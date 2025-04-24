package com.example.Interfaces;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface IMinIO {
    String uploadFile(String filename, InputStream inputStream, String contentType, String Bucket) throws IOException;
    byte[] getFile(String filename, String Bucket);
    List<String> getImageNames(String bucketName, String folderPrefix) throws Exception;
}
