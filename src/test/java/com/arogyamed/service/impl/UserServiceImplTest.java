package com.arogyamed.service.impl;

import com.arogyamed.dto.UserRequestDTO;
import com.arogyamed.dto.UserResponseDTO;
import com.arogyamed.model.Role;
import com.arogyamed.model.User;
import com.arogyamed.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User sampleUser;
    private UserRequestDTO sampleRequest;

    @BeforeEach
    void setUp() {
        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setFullName("Anuj Indulkar");
        sampleUser.setEmail("anuj@example.com");
        sampleUser.setPhoneNumber("9876543210");
        sampleUser.setAddress("Pune, India");
        sampleUser.setRole(Role.PATIENT);
        sampleUser.setVerified(false);

        sampleRequest = new UserRequestDTO();
        sampleRequest.setFullName("Anuj Indulkar");
        sampleRequest.setEmail("anuj@example.com");
        sampleRequest.setPhoneNumber("9876543210");
        sampleRequest.setPassword("password123");
        sampleRequest.setAddress("Pune, India");
        sampleRequest.setRole(Role.PATIENT);
    }

    @Test
    void registerUser_shouldSaveAndReturnUser_whenEmailDoesNotExist() {
        when(userRepository.existsByEmail(sampleRequest.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(sampleUser);

        UserResponseDTO result = userService.registerUser(sampleRequest);

        assertNotNull(result);
        assertEquals(sampleUser.getEmail(), result.getEmail());
        assertEquals(sampleUser.getFullName(), result.getFullName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_shouldThrowException_whenEmailAlreadyExists() {
        when(userRepository.existsByEmail(sampleRequest.getEmail())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.registerUser(sampleRequest));

        assertEquals("Email already exists", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void getUserById_shouldReturnUser_whenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));

        UserResponseDTO result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(sampleUser.getEmail(), result.getEmail());
    }

    @Test
    void getUserById_shouldThrowException_whenUserDoesNotExist() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.getUserById(99L));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() {
        User secondUser = new User();
        secondUser.setId(2L);
        secondUser.setFullName("Second User");
        secondUser.setEmail("second@example.com");
        secondUser.setRole(Role.DOCTOR);

        when(userRepository.findAll()).thenReturn(Arrays.asList(sampleUser, secondUser));

        List<UserResponseDTO> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void deleteUser_shouldDeleteUser_whenUserExists() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        assertDoesNotThrow(() -> userService.deleteUser(1L));

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteUser_shouldThrowException_whenUserDoesNotExist() {
        when(userRepository.existsById(99L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.deleteUser(99L));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    void searchByRole_shouldReturnMatchingUsers() {
        when(userRepository.findByRole(Role.PATIENT)).thenReturn(List.of(sampleUser));

        List<UserResponseDTO> result = userService.searchByRole(Role.PATIENT);

        assertEquals(1, result.size());
        assertEquals(Role.PATIENT, result.get(0).getRole());
    }
}