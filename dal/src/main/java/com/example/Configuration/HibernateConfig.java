package com.example.Configuration;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.DTO.UserRegistrationDTO;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    @Bean
    public SessionFactory sessionFactory() {
        // Create registry builder and configure settings from hibernate.cfg.xml
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml") // loads settings from resource
                .build();

        try {
            // Create MetadataSources using the registry
            MetadataSources sources = new MetadataSources(registry);
            // Add your annotated classes explicitly
            sources.addAnnotatedClass(UserRegistrationDTO.class);

            // Build Metadata
            Metadata metadata = sources.getMetadataBuilder().build();

            // Build the SessionFactory
            return metadata.getSessionFactoryBuilder().build();

        } catch (Exception e) {
            // If something goes wrong, destroy registry and throw exception
            StandardServiceRegistryBuilder.destroy(registry);
            throw new RuntimeException("SessionFactory build failed", e);
        }
    }
}
