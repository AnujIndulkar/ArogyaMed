package com.arogyamed.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

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
  // VALIDATION ERRORS (ready for Phase 3 - Jakarta Validation)
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
  // FILE UPLOAD ERRORS (relevant to Document module)
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
  // Your current services throw plain RuntimeException("X not found")
  // everywhere. This handler bridges that pattern to proper HTTP
  // status codes WITHOUT requiring you to touch any service file today.
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

    return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, message, request, null);
  }

  // ==========================================================
  // CATCH-ALL FALLBACK
  // ==========================================================

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(
          Exception ex, HttpServletRequest request) {

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
