package com.example.api.controller;

import com.example.Services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Executor;

@RestController
@RequestMapping("/image")
public class ImageController {

    private ImageService imageService;
    private final Executor asyncExecutor;

    @Autowired
    public ImageController(ImageService imageService, @Qualifier("applicationTaskExecutor") Executor asyncExecutor) {
        this.imageService = imageService;
        this.asyncExecutor = asyncExecutor;
    }


    @PostMapping("/userupload")
    public String userupload(
            @RequestParam("userId") int userId,
            @RequestParam("filename") String filename,
            @RequestParam("file") MultipartFile file,  // MultipartFile for file upload
            @RequestParam("contentype") String contentype) throws IOException
    {
        InputStream inputStream = file.getInputStream();
        return imageService.UserUploadImage(userId, filename, inputStream , contentype);
    }

    @GetMapping("/userimages/{userId}/{filename}")
    public byte[] userimages(@PathVariable("userId") int userId, @PathVariable("filename") String filename) throws IOException {
        return imageService.UserGetImage(userId, filename);
    }

    @PostMapping("/usergetimage")
    public String usergetimage(
            @RequestParam("userId") int userId,
            @RequestParam("filename") String filename,
            @RequestParam("file") MultipartFile file,  // MultipartFile for file upload
            @RequestParam("contentype") String contentype) throws IOException
    {
        System.out.println("Hello");
        InputStream inputStream = file.getInputStream();
        return imageService.UserUploadImage(userId, filename, inputStream , contentype);
    }

    //Verander dit naar een model waar de byte (image) en String (key) in zit en list deze dan
    @GetMapping("/getalluserimages/{userid}")
    public ResponseEntity<List<byte[]>> getAllUserImages(
            @PathVariable String userid) {
        try {
            // Call the service to get images as byte arrays for the user
            List<byte[]> images = imageService.getAllUserImages(userid);

            // Return the list of byte arrays representing the images
            return ResponseEntity.ok(images);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);  // Handle any exceptions gracefully
        }
    }

}
