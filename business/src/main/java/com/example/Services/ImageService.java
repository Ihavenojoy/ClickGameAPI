package com.example.Services;

import com.example.Interfaces.IDragonfly;
import com.example.Interfaces.IMinIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    @Autowired
    private IMinIO minioService;

}
