package com.exam.service.impl;


import com.exam.Service.impl.UserServiceImpl;
import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.repo.RoleRepository;
import com.exam.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testCreateUser_Positive() {

        // Prepare input user
        User user = new User();
        user.setUsername("veeresh");

        // Prepare roles
        Role role = new Role();
        role.setRoleId(1L);
        role.setRoleName("ADMIN");

        UserRole userRole = new UserRole();
        userRole.setRole(role);
        userRole.setUser(user);

        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(userRole);

        // Mock: user doesn't exist
        when(userRepository.findByUsername("veeresh")).thenReturn(null);

        // Mock: save role and user
        when(roleRepository.save(role)).thenReturn(role);
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User result = userService.createUser(user, userRoles);

        // Assert
        assertNotNull(result);
        assertEquals("veeresh", result.getUsername());

        // Verify
        verify(userRepository).findByUsername("veeresh");
        verify(roleRepository).save(role);
        verify(userRepository).save(user);
    }

    @Test
    void testCreateUser_UserAlreadyExists() {
        // Prepare input user
        User user = new User();
        user.setUsername("veeresh");

        // Mock: user already exists
        when(userRepository.findByUsername("veeresh")).thenReturn(user);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.createUser(user, new HashSet<>());
        });

        assertEquals("User is already there !!", exception.getMessage());

        // Verify that save was never called
        verify(userRepository).findByUsername("veeresh");
        verify(roleRepository, never()).save(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testGetUser_UserExists() {
        // Arrange
        String username = "veeresh";
        User mockUser = new User();
        mockUser.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(mockUser);

        // Act
        User result = userService.getUser(username);

        // Assert
        assertNotNull(result);
        assertEquals("veeresh", result.getUsername());

        verify(userRepository).findByUsername(username);
    }


    @Test
    void testGetUser_UserNotFound() {
        // Arrange
        String username = "unknown";
        when(userRepository.findByUsername(username)).thenReturn(null);

        // Act
        User result = userService.getUser(username);

        // Assert
        assertNull(result);

        verify(userRepository).findByUsername(username);
    }

    @Test
    void testDeleteUser_UserExists() {
        // Arrange
        Long userId = 1L;
        doNothing().when(userRepository).deleteById(userId);

        // Act
        userService.deleteUser(userId);

        // Assert + Verify
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testUpdateUser_Success() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setUsername("veeresh");

        when(userRepository.save(user)).thenReturn(user);

        // Act
        User result = userService.updateUser(user);

        // Assert
        assertNotNull(result);
        assertEquals("veeresh", result.getUsername());
        verify(userRepository, times(1)).save(user);
    }




}
