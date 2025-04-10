package com.example.Interfaces;

import java.io.IOException;
import java.io.InputStream;

public interface IMinIO {
    String uploadFile(String filename, InputStream inputStream, String contentType, String Bucket) throws IOException;
    byte[] getFile(String filename, String Bucket);
}
