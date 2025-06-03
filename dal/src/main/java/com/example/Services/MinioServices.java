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

        // Use the AWS SDK's listObjectsV2 without the delimiter for recursive listing
        ListObjectsV2Request listObjects = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .prefix(folderPrefix)
                .build();

        ListObjectsV2Response response = s3Client.listObjectsV2(listObjects);

        // Iterate over the objects in the response
        for (S3Object s3Object : response.contents()) {
            String fileName = s3Object.key();

            // Remove the prefix (subfolder) from the file name and also remove any leading slash
            fileName = fileName.replaceFirst("^" + folderPrefix, "");
            fileName = fileName.replaceFirst("^/", ""); // Remove the leading slash if it exists

            fileNames.add(fileName);
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
