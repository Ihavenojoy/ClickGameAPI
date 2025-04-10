package com.example.Services;

import com.example.Configuration.MinIOProperties;
import com.example.Interfaces.IMinIO;
import jakarta.validation.groups.Default;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

@Service
public class MinioServices implements IMinIO {
    private final S3Client s3Client;
    private final MinIOProperties minioProperties;
    private String bucketName;

    // Constructor injection of MinIOProperties
    public MinioServices(MinIOProperties minioProperties) {
        this.minioProperties = minioProperties;

        // Validate access key and secret key
        if (minioProperties.getAccessKey() == null || minioProperties.getAccessKey().isEmpty()) {
            throw new IllegalArgumentException("Access key cannot be blank.");
        }
        if (minioProperties.getSecretKey() == null || minioProperties.getSecretKey().isEmpty()) {
            throw new IllegalArgumentException("Secret key cannot be blank.");
        }

        // Initialize MinIO client
        this.s3Client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(minioProperties.getAccessKey(), minioProperties.getSecretKey())))
                .endpointOverride(URI.create(minioProperties.getEndpoint()))
                .region(Region.of(minioProperties.getRegion()))
                .build();

        createBucketIfNotExists();
    }

    public String uploadFile(String filename, InputStream inputStream, String contentType, String Bucket) throws IOException {
        bucketName = Bucket;
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(filename)
                .contentType(contentType)
                .build();

        s3Client.putObject(request, RequestBody.fromInputStream(inputStream, inputStream.available()));
        return s3Client.utilities()
                .getUrl(GetUrlRequest.builder().bucket(bucketName).key(filename).build())
                .toExternalForm();
    }

    public byte[] getFile(String filename, String Bucket) {
        bucketName = Bucket;
        ResponseBytes<GetObjectResponse> bytes = s3Client.getObjectAsBytes(GetObjectRequest.builder()
                .bucket(bucketName)
                .key(filename)
                .build());
        return bytes.asByteArray();
    }

    private void createBucketIfNotExists() {
        if (!s3Client.listBuckets().buckets().stream()
                .anyMatch(b -> b.name().equals(minioProperties.getBucket()))) {
            s3Client.createBucket(CreateBucketRequest.builder().bucket(minioProperties.getBucket()).build());
        }
    }
}
