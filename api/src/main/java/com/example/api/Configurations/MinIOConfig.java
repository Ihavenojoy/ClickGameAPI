package com.example.api.Configurations;

import com.example.Configuration.MinIOProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.net.URI;

@Configuration
@EnableConfigurationProperties(MinIOProperties.class) // Enable properties binding
public class MinIOConfig {

    private final MinIOProperties minIOProperties; // Inject MinIOProperties

    public MinIOConfig(MinIOProperties minIOProperties) {
        this.minIOProperties = minIOProperties;
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .endpointOverride(URI.create(minIOProperties.getEndpoint())) // Use injected properties
                .region(Region.of(minIOProperties.getRegion()))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(minIOProperties.getAccessKey(), minIOProperties.getSecretKey())
                        )
                )
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true) // Important for MinIO
                        .build())
                .httpClientBuilder(UrlConnectionHttpClient.builder())
                .build();
    }
}
