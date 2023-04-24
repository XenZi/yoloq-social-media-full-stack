package com.example.yoloq.service.impl;


import com.example.yoloq.models.Image;
import com.example.yoloq.repository.ImageRepository;
import com.example.yoloq.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private final Path root = Paths.get("uploads");
    private final ImageRepository imageRepository;


    @Autowired
    public FileServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @PostConstruct
    private void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            return;
        }
    }

    @Override
    public String uploadImage(MultipartFile file) {
        final String originalFileName = file.getOriginalFilename();
        final String randomGeneratedName = UUID.randomUUID().toString();
        try {
            Path saveTo = Paths.get("uploads/" + randomGeneratedName);
            Image image = new Image();
            image.setName(randomGeneratedName);
            imageRepository.save(image);     
            Files.copy(file.getInputStream(), saveTo);
        } catch (IOException e) {
            throw new RuntimeException("Error while saving an image");
        }
        return randomGeneratedName;
    }

    @Override
    public Resource getImage(String fileName)  {
        try {
            Path file = root.resolve(fileName);
            return new UrlResource(file.toUri());
        } catch (IOException exception) {
            throw new RuntimeException("Error while reading resource from the server");
        }
    }
}
