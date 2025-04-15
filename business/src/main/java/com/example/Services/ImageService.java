package com.example.Services;

import com.example.Interfaces.IDragonfly;
import com.example.Interfaces.IMinIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {

    private IMinIO minioService;
    @Autowired
    public ImageService(IMinIO imageService) {
        minioService = imageService;
    }

    public String UploadImage(String filename, InputStream inputStream, String contentType, String bucket) throws IOException {
        return minioService.uploadFile(filename,inputStream,contentType,bucket);
    }

    public String UserUploadImage(int userId, String filename, InputStream inputStream, String contentType) throws IOException {
        String fixedBucketName = "user-images";
        String objectKey = userId + "/" + filename;

        return minioService.uploadFile(objectKey, inputStream, contentType, fixedBucketName);
    }


}
