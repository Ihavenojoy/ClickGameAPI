package com.example.Services;

import com.example.Configuration.MinIOProperties;
import com.example.Interfaces.IMinIO;
import io.minio.MinioClient;
import io.minio.Result;
import jakarta.validation.groups.Default;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import java.util.ArrayList;
import io.minio.messages.Item;
import io.minio.ListObjectsArgs;

@Service
public class MinioServices implements IMinIO {
    private final S3Client s3Client;
    private final MinIOProperties minioProperties;

    @Autowired
    public MinioServices(S3Client s3Client, MinIOProperties minioProperties) {
        this.s3Client = s3Client;
        this.minioProperties = minioProperties;
        createBucketIfNotExists();
    }

    public String uploadFile(String filename, InputStream inputStream, String contentType, String Bucket) throws IOException {

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(Bucket)
                .key(filename)
                .contentType(contentType)
                .build();

        // Upload the file
        s3Client.putObject(request, RequestBody.fromInputStream(inputStream, inputStream.available()));

        // You might want to return a success message or URL for the uploaded file
        return "File uploaded successfully: " + filename;
    }

    public byte[] getFile(String key, String Bucket) {

        System.out.println("Looking for object key: [" + key + "]");
        // Get the file as bytes
        ResponseBytes<GetObjectResponse> bytes = s3Client.getObjectAsBytes(GetObjectRequest.builder()
                .bucket(Bucket)
                .key(key)
                .build());

        return bytes.asByteArray();
    }

    public List<String> getImageNames(String bucketName, String folderPrefix) throws Exception {
        List<String> fileNames = new ArrayList<>();

        // Ensure prefix ends with a slash (to only get files "under" that folder)
        if (!folderPrefix.endsWith("/")) {
            folderPrefix += "/";
        }

        ListObjectsV2Request listObjects = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .prefix(folderPrefix)
                .build();

        ListObjectsV2Response response = s3Client.listObjectsV2(listObjects);

        for (S3Object s3Object : response.contents()) {
            String key = s3Object.key();

            // Only strip the folder prefix if the key starts with it
            if (key.startsWith(folderPrefix)) {
                String fileName = key.substring(folderPrefix.length());

                // Skip if it's a "folder" entry or empty name
                if (!fileName.isEmpty() && !fileName.endsWith("/")) {
                    fileNames.add(fileName);
                }
            }
        }

        return fileNames;
    }




    private void createBucketIfNotExists() {
        if (!s3Client.listBuckets().buckets().stream()
                .anyMatch(b -> b.name().equals(minioProperties.getBucket()))) {
            s3Client.createBucket(CreateBucketRequest.builder().bucket(minioProperties.getBucket()).build());
        }
    }
}
