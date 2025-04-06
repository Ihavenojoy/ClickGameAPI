package com.example.Services;

import com.example.Configuration.HibernateConfiguration;
import com.example.DTO.UserRegistrationDTO;
import com.example.Interfaces.IUser;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class UserServices implements IUser {

    @Async
    public boolean RegisterUser(UserRegistrationDTO userRegistrationDTO) {
        Session session = HibernateConfiguration.getSession();

        try {
            // Begin a transaction
            Transaction transaction = session.beginTransaction();

            // Set entity properties here
            session.save(userRegistrationDTO);

            // Commit the transaction
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            session.close();  // Always close the session
            return true;
        }
    }
}
