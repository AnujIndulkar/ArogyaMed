package com.arogyamed.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  // ==========================================================
  // CUSTOM EXCEPTIONS (use these in new code going forward)
  // ==========================================================

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFound(
          ResourceNotFoundException ex, HttpServletRequest request) {

    return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request, null);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> handleBadRequest(
          BadRequestException ex, HttpServletRequest request) {

    return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request, null);
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ErrorResponse> handleUnauthorized(
          UnauthorizedException ex, HttpServletRequest request) {

    return buildResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), request, null);
  }

  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<ErrorResponse> handleConflict(
          ConflictException ex, HttpServletRequest request) {

    return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), request, null);
  }

  // ==========================================================
  // ResponseStatusException (used by the newer services):
  // keep the status the code chose (400, 403, 404, 409 ...)
  // and send only the clean reason as the message
  // ==========================================================

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ErrorResponse> handleResponseStatus(
          ResponseStatusException ex, HttpServletRequest request) {

    HttpStatusCode code = ex.getStatusCode();
    HttpStatus status = HttpStatus.resolve(code.value());
    if (status == null) {
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    String message = ex.getReason() != null ? ex.getReason() : status.getReasonPhrase();
    return buildResponse(status, message, request, null);
  }

  // ==========================================================
  // LOGIN FAILURES (wrong password, unknown email, disabled user)
  // ==========================================================

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorResponse> handleAuthentication(
          AuthenticationException ex, HttpServletRequest request) {

    return buildResponse(HttpStatus.UNAUTHORIZED, "Invalid email or password", request, null);
  }

  // ==========================================================
  // VALIDATION ERRORS
  // ==========================================================

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationErrors(
          MethodArgumentNotValidException ex, HttpServletRequest request) {

    List<String> details = ex.getBindingResult().getFieldErrors()
            .stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.toList());

    return buildResponse(HttpStatus.BAD_REQUEST, "Validation failed", request, details);
  }

  // ==========================================================
  // WRONG URL / WRONG HTTP METHOD
  // ==========================================================

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<ErrorResponse> handleNoResource(
          NoResourceFoundException ex, HttpServletRequest request) {

    log.warn("No endpoint for {} {}", request.getMethod(), request.getRequestURI());
    return buildResponse(HttpStatus.NOT_FOUND,
            "No such endpoint: " + request.getMethod() + " " + request.getRequestURI(), request, null);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleMethodNotSupported(
          HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {

    log.warn("Method {} not allowed for {}", request.getMethod(), request.getRequestURI());
    return buildResponse(HttpStatus.METHOD_NOT_ALLOWED,
            request.getMethod() + " is not allowed on " + request.getRequestURI(), request, null);
  }

  // ==========================================================
  // FILE UPLOAD ERRORS
  // ==========================================================

  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public ResponseEntity<ErrorResponse> handleMaxUploadSize(
          MaxUploadSizeExceededException ex, HttpServletRequest request) {

    return buildResponse(HttpStatus.PAYLOAD_TOO_LARGE, "Uploaded file is too large.", request, null);
  }

  @ExceptionHandler(MultipartException.class)
  public ResponseEntity<ErrorResponse> handleMultipart(
          MultipartException ex, HttpServletRequest request) {

    return buildResponse(HttpStatus.BAD_REQUEST, "Invalid file upload request.", request, null);
  }

  // ==========================================================
  // EXISTING CODE COMPATIBILITY
  // Older services throw plain RuntimeException("X not found").
  // ==========================================================

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorResponse> handleRuntimeException(
          RuntimeException ex, HttpServletRequest request) {

    String message = ex.getMessage() != null ? ex.getMessage() : "Something went wrong.";

    String lowerMessage = message.toLowerCase();

    if (lowerMessage.contains("not found")) {
      return buildResponse(HttpStatus.NOT_FOUND, message, request, null);
    }

    if (lowerMessage.contains("already exists")
            || lowerMessage.contains("already registered")
            || lowerMessage.contains("already booked")) {
      return buildResponse(HttpStatus.CONFLICT, message, request, null);
    }

    if (lowerMessage.contains("invalid")
            || lowerMessage.contains("required")
            || lowerMessage.contains("must be")) {
      return buildResponse(HttpStatus.BAD_REQUEST, message, request, null);
    }

    // a real server error: always write it to the log with the full stack trace
    log.error("Unhandled runtime error on {} {}", request.getMethod(), request.getRequestURI(), ex);
    return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, message, request, null);
  }

  // ==========================================================
  // CATCH-ALL FALLBACK
  // ==========================================================

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(
          Exception ex, HttpServletRequest request) {

    // this used to swallow the error silently: log it so it shows in the console
    log.error("Unexpected error on {} {}", request.getMethod(), request.getRequestURI(), ex);

    return buildResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "An unexpected error occurred. Please try again later.",
            request,
            null
    );
  }

  // ==========================================================
  // HELPER
  // ==========================================================

  private ResponseEntity<ErrorResponse> buildResponse(
          HttpStatus status, String message, HttpServletRequest request, List<String> details) {

    ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(status.value())
            .error(status.getReasonPhrase())
            .message(message)
            .path(request.getRequestURI())
            .details(details)
            .build();

    return new ResponseEntity<>(errorResponse, status);
  }
}