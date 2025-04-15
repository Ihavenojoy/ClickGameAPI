package com.example.api;

import com.example.Configuration.MinIOProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")  // Activate the test profile
public class ApiApplicationTests {

    @Autowired
    private MinIOProperties minIOProperties;

    @Test
    void contextLoads() {
        assertNotNull(minIOProperties);  // Ensure MinIOProperties is injected
    }
}
