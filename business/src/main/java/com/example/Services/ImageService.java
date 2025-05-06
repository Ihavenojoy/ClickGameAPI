package com.example.Services;

import com.example.Interfaces.IMinIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    private IMinIO minioService;
    private final String UserBucketName = "user-images";
    @Autowired
    public ImageService(IMinIO imageService) {
        minioService = imageService;
    }

    public String UploadImage(String filename, InputStream inputStream, String contentType, String bucket) throws IOException {
        return minioService.uploadFile(filename,inputStream,contentType,bucket);
    }

    public String UserUploadImage(int userId, String filename, InputStream inputStream, String contentType) throws IOException {
        String objectKey = userId + "/" + filename;

        return minioService.uploadFile(objectKey, inputStream, contentType, UserBucketName);
    }


    public byte[] UserGetImage(int userId, String filename) throws IOException {
        String objectKey = userId + "/" + filename.trim();
        return minioService.getFile(objectKey, UserBucketName);
    }

    public List<byte[]> getAllUserImages(String folderPrefix) throws Exception {
        List<String> imageNames = minioService.getImageNames(UserBucketName, folderPrefix);

        List<byte[]> imageBytesList = new ArrayList<>();

        for (String imageName : imageNames) {
            String objectKey = folderPrefix + "/" + imageName;
            byte[] imageBytes = minioService.getFile(objectKey, UserBucketName);
            imageBytesList.add(imageBytes);
        }

        return imageBytesList;
    }
}
