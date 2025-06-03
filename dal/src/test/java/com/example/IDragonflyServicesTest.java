package com.example;


import com.example.Services.IDragonflyServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IDragonflyServicesTest {

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOps;

    private IDragonflyServices dragonflyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        dragonflyService = new IDragonflyServices(redisTemplate);
    }

    @Test
    void testToMemory() throws Exception {
        String key = "3";
        String saveType = "ClickUserID";
        String json = "{\"score\": 100}";

        CompletableFuture<Boolean> result = dragonflyService.ToMemory(key, saveType, json);

        // Wait for async operation to complete
        assertTrue(result.get());
        verify(valueOps).set("ClickUserID: 3", json);
    }

    @Test
    void testFromMemory() throws Exception {
        String key = "3";
        String saveType = "ClickUserID";
        String expectedJson = "{\"score\": 100}";

        when(valueOps.get("ClickUserID: 3")).thenReturn(expectedJson);
        when(redisTemplate.opsForValue()).thenReturn(valueOps);

        CompletableFuture<String> result = dragonflyService.FromMemory(key, saveType);

        assertEquals(expectedJson, result.get());
    }
}
