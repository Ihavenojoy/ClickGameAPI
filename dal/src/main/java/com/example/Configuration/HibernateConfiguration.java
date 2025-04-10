package com.example.Configuration;


import com.example.DTO.UserRegistrationDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
@org.springframework.context.annotation.Configuration

public class HibernateConfiguration {
    private static SessionFactory factory;

    static {
        try {
            // Create a SessionFactory using the Hibernate configuration file
            factory = new Configuration().configure("hibernate.cfg.xml")
                    .addAnnotatedClass(UserRegistrationDTO.class)  // Add the actual entity class
                    .buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("SessionFactory initialization failed");
        }
    }

    // Method to get the current session (using openSession for non-Spring setups)
    public static Session getSession() {
        return factory.openSession();  // Open a new session
    }

    // Method to close the factory (to release resources)
    public static void close() {
        factory.close();
    }
}