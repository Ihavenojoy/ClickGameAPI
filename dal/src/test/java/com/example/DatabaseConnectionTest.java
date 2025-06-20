package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import redis.clients.jedis.Jedis;

import io.minio.MinioClient;
import io.minio.errors.MinioException;

public class DatabaseConnectionTest {

    @Test
    public void testMysqlPing() {
        String url = "jdbc:mysql://localhost:3306/Click_Store";

        try (Connection conn = DriverManager.getConnection(url)) {
            assertTrue(conn != null && !conn.isClosed(), "Connection should be established");
            System.out.println("✅ Ping successful: Connected to MySQL as anonymous user.");
        } catch (SQLException e) {
            if (e.getSQLState().startsWith("28")) { // SQLSTATE 28000 = Access denied
                System.out.println("✅ Ping successful: MySQL responded with access denied.");
                assertTrue(true); // Test passes — server is alive
            } else {
                System.err.println("❌ Ping failed: " + e.getMessage());
                assertTrue(false, "Ping failed due to unexpected error: " + e.getMessage());
            }
        }
    }

    @Test
    void testDragonflyPing() {
        int port = Integer.parseInt(System.getenv().getOrDefault("DRAGONFLY_PORT", "6379"));

        try (Jedis jedis = new Jedis("localhost", port)) {
            String response = jedis.ping();
            assertEquals("PONG", response);
        } catch (Exception e) {
            fail("Could not connect to Dragonfly: " + e.getMessage());
        }
    }

    @Test
    void testMinioConnection() {
        String endpoint = "http://localhost:9000";
        String accessKey = System.getenv().getOrDefault("MINIO_ACCESS_KEY", "minioadmin");
        String secretKey = System.getenv().getOrDefault("MINIO_SECRET_KEY", "minioadmin");

        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(accessKey, secretKey)
                    .build();

            minioClient.listBuckets();
        } catch (MinioException e) {
            fail("Could not connect to MinIO: " + e.getMessage());
        } catch (Exception e) {
            fail("Unexpected error connecting to MinIO: " + e.getMessage());
        }
    }
}