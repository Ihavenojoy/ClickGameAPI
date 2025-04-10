package com.example.Configuration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
public class DragonflyConfiguration {
    @Bean
    public LettuceConnectionFactory DragonFlyConnectionFactory() {
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName("host.docker.internal");
        config.setPort(6379);

        return new LettuceConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer()); // Ensure values are stored as plain strings (JSON)
        return template;
    }

    @Bean
    public CommandLineRunner pingDragonflyOnStartup(LettuceConnectionFactory redisConnectionFactory) {
        return args -> {
            RedisConnection connection = null;
            try {
                // Make sure Redis is available and ping it
                connection = redisConnectionFactory.getConnection();
                String pingResponse = connection.ping();  // Ping Redis
                System.out.println("✅ Redis Ping Response: " + pingResponse); // Should output "PONG"

                // Add a check for a couple of seconds if Redis is not ready
                int retries = 0;
                while (!"PONG".equals(pingResponse) && retries < 5) {
                    System.out.println("⚠️ Waiting for Redis to be available...");
                    Thread.sleep(2000);  // Wait for 2 seconds before retrying
                    pingResponse = connection.ping();
                    retries++;
                }

                if ("PONG".equals(pingResponse)) {
                    System.out.println("✅ Redis is ready and responding!");
                } else {
                    System.out.println("⚠️ Redis is still not responding after multiple retries.");
                }
            } catch (Exception e) {
                System.err.println("⚠️ Error pinging Redis: " + e.getMessage());
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        };
    }

}
