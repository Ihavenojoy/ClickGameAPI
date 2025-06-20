package com.example.api.controller;

import com.example.Models.ImageObject;
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
@CrossOrigin(origins = "http://localhost:3000")
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
            @RequestParam("contentype") String contentype) throws IOException {
        InputStream inputStream = file.getInputStream();
        return imageService.UserUploadImage(userId, filename, inputStream, contentype);
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
            @RequestParam("contentype") String contentype) throws IOException {
        System.out.println("Hello");
        InputStream inputStream = file.getInputStream();
        return imageService.UserUploadImage(userId, filename, inputStream, contentype);
    }

    @GetMapping("/getalluserimages/{userid}")
    public List<ImageObject> getAllUserImages(@PathVariable String userid) throws Exception {
        return imageService.getAllUserImages(userid);
    }
}

