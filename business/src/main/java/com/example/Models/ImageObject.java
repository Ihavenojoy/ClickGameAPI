package com.example.Models;

import java.util.Base64;

public class ImageObject {

    private String name;
    private byte[] imageData;

    public ImageObject() {}

    public ImageObject(byte[] imageData, String name) {
        this.imageData = imageData;
        this.name = name;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
