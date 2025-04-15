package com.example.api.Controller;

import com.example.Models.Click;
import com.example.Services.ClickService;
import com.example.Services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@CrossOrigin(origins = {"http://localhost:3000", "https://localhost:3000"})
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
    public String uploadImage(
            @RequestParam("userId") int userId,
            @RequestParam("filename") String filename,
            @RequestParam("file") MultipartFile file,  // MultipartFile for file upload
            @RequestParam("contentype") String contentype) throws IOException
    {
        System.out.println("Hello");
        InputStream inputStream = file.getInputStream();
        return imageService.UserUploadImage(userId, filename, inputStream , contentype);
    }
}
