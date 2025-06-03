package com.example.Services;

import com.example.DTO.UserRegistrationDTO;
import com.example.Interfaces.IUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServices implements IUser {

    @PersistenceContext
    public EntityManager entityManager;

    @Async
    @Transactional
    public boolean RegisterUser(UserRegistrationDTO userRegistrationDTO) {
        try {
            entityManager.persist(userRegistrationDTO);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
