package com.exam.service.impl;

import com.exam.Service.impl.UserDetailsServiceImpl;
import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void testLoadUserByUsername_Positive() {
        // Prepare User and Roles
        User user = new User();
        user.setUsername("veeresh");

        Role role = new Role();
        role.setRoleId(1L);
        role.setRoleName("ADMIN");

        UserRole userRole = new UserRole();
        userRole.setRole(role);
        userRole.setUser(user);

        Set<UserRole> roles = new HashSet<>();
        roles.add(userRole);
        user.setUserRoles(roles);

        // Mock repository
        when(userRepository.findByUsername("veeresh")).thenReturn(user);

        // Act
        UserDetails result = userDetailsService.loadUserByUsername("veeresh");

        // Assert
        assertNotNull(result);
        assertEquals("veeresh", result.getUsername());
        verify(userRepository).findByUsername("veeresh");
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Mock repository: return null
        when(userRepository.findByUsername("unknown")).thenReturn(null);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () ->
                userDetailsService.loadUserByUsername("unknown"));

        verify(userRepository).findByUsername("unknown");
    }
}
