package com.example;

import com.example.DTO.UserRegistrationDTO;
import com.example.Services.UserServices;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServicesTest {

    private EntityManager entityManager;
    private UserServices userServices;

    @BeforeEach
    public void setUp() {
        entityManager = mock(EntityManager.class);
        userServices = new UserServices();
        // Inject mocked entityManager manually since @PersistenceContext is not processed here
        userServices.entityManager = entityManager;
    }

    @Test
    public void testRegisterUser_Success() {
        UserRegistrationDTO userDTO = new UserRegistrationDTO();

        doNothing().when(entityManager).persist(userDTO);

        boolean result = userServices.RegisterUser(userDTO);

        assertTrue(result);
        verify(entityManager).persist(userDTO);
    }

    @Test
    public void testRegisterUser_Exception() {
        UserRegistrationDTO userDTO = new UserRegistrationDTO();

        doThrow(new RuntimeException("DB error")).when(entityManager).persist(userDTO);

        boolean result = userServices.RegisterUser(userDTO);

        assertFalse(result);
        verify(entityManager).persist(userDTO);
    }
}
