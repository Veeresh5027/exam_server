package com.exam.controller;

import com.exam.Service.UserService;
import com.exam.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ---------- createUser ----------
    @Test
    void createUser_Positive() {
        User input = new User();
        input.setUsername("john");
        input.setPassword("123");

        User saved = new User();
        saved.setUsername("john");
        saved.setPassword("encoded");

        when(bCryptPasswordEncoder.encode("123")).thenReturn("encoded");
        when(userService.createUser(any(User.class), anySet())).thenReturn(saved);

        User result = userController.createUser(input);

        assertNotNull(result);
        assertEquals("john", result.getUsername());
        assertEquals("encoded", result.getPassword());
        assertEquals("default.png", input.getProfile());

        verify(userService).createUser(any(User.class), anySet());
        verify(bCryptPasswordEncoder).encode("123");
    }

    @Test
    void createUser_NullInput_ShouldThrowException() {
        assertThrows(NullPointerException.class, () -> userController.createUser(null));
    }

    // ---------- getUser ----------
    @Test
    void getUser_Positive() {
        User mockUser = new User();
        mockUser.setUsername("john");
        when(userService.getUser("john")).thenReturn(mockUser);

        User result = userController.getUser("john");

        assertNotNull(result);
        assertEquals("john", result.getUsername());
        verify(userService).getUser("john");
    }

    @Test
    void getUser_NotFound_ShouldThrow() {
        when(userService.getUser("missing")).thenThrow(new UsernameNotFoundException("User not found"));
        assertThrows(UsernameNotFoundException.class, () -> userController.getUser("missing"));
    }

    // ---------- deleteUser ----------
    @Test
    void deleteUser_Positive() {
        doNothing().when(userService).deleteUser(1L);
        userController.deleteUser(1L);
        verify(userService).deleteUser(1L);
    }

    @Test
    void deleteUser_NotFound_ShouldThrow() {
        doThrow(new RuntimeException("Not found")).when(userService).deleteUser(2L);
        assertThrows(RuntimeException.class, () -> userController.deleteUser(2L));
    }

    // ---------- updateUser ----------
    @Test
    void updateUser_Positive() {
        User user = new User();
        user.setUsername("john");
        when(userService.updateUser(user)).thenReturn(user);

        User result = userController.updateUser(user);

        assertNotNull(result);
        assertEquals("john", result.getUsername());
        verify(userService).updateUser(user);
    }

    @Test
    void updateUser_Null_ShouldThrow() {
        when(userService.updateUser(null)).thenThrow(new NullPointerException("User is null"));
        assertThrows(NullPointerException.class, () -> userController.updateUser(null));
    }
}
