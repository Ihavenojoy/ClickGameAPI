package com.example;

import com.example.Configuration.MinIOProperties;
import com.example.Services.MinioServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MinioServicesTest {

    private S3Client s3Client;
    private MinIOProperties minIOProperties;
    private MinioServices minioServices;

    @BeforeEach
    void setUp() {
        s3Client = mock(S3Client.class);
        minIOProperties = mock(MinIOProperties.class);

        // Simulate no existing buckets during setup
        ListBucketsResponse emptyBucketsResponse = ListBucketsResponse.builder()
                .buckets(List.of())
                .build();
        when(s3Client.listBuckets()).thenReturn(emptyBucketsResponse);
        when(minIOProperties.getBucket()).thenReturn("test-bucket");

        minioServices = new MinioServices(s3Client, minIOProperties);
    }

    @Test
    void testUploadFile() throws Exception {
        InputStream inputStream = new ByteArrayInputStream("file-content".getBytes());
        String bucket = "test-bucket";
        String filename = "test.txt";
        String contentType = "text/plain";

        String result = minioServices.uploadFile(filename, inputStream, contentType, bucket);

        assertTrue(result.contains("File uploaded successfully"));
        verify(s3Client, times(1)).putObject(any(PutObjectRequest.class), any(RequestBody.class));
    }

    @Test
    void testGetFile() {
        String bucket = "test-bucket";
        String key = "image.jpg";
        byte[] fakeData = "image-data".getBytes();

        GetObjectResponse getObjectResponse = GetObjectResponse.builder().build();
        ResponseBytes<GetObjectResponse> mockBytes = ResponseBytes.fromByteArray(getObjectResponse, fakeData);

        when(s3Client.getObjectAsBytes(any(GetObjectRequest.class))).thenReturn(mockBytes);

        byte[] result = minioServices.getFile(key, bucket);

        assertArrayEquals(fakeData, result);
        verify(s3Client).getObjectAsBytes(any(GetObjectRequest.class));
    }

    @Test
    void testGetImageNames() throws Exception {
        String bucket = "test-bucket";
        String prefix = "images/";

        List<S3Object> objects = List.of(
                S3Object.builder().key("images/pic1.jpg").build(),
                S3Object.builder().key("images/pic2.jpg").build()
        );

        ListObjectsV2Response mockResponse = ListObjectsV2Response.builder()
                .contents(objects)
                .build();

        when(s3Client.listObjectsV2(any(ListObjectsV2Request.class))).thenReturn(mockResponse);

        List<String> result = minioServices.getImageNames(bucket, prefix);

        assertEquals(List.of("pic1.jpg", "pic2.jpg"), result);
    }
}
