package com.example.yoloq.service.impl;


import com.example.yoloq.models.Image;
import com.example.yoloq.models.Post;
import com.example.yoloq.models.User;
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
            throw new RuntimeException("Error while creating the root directory of the file system");
        }
    }

    @Override
    public Image uploadImage(MultipartFile file) {
        return uploadImageInternal(file);
    }

    private Image uploadImageInternal(MultipartFile file) {
        final String randomGeneratedName = UUID.randomUUID().toString();
        Image image = new Image();
        image.setName(randomGeneratedName);
        try {
            Path saveTo = Paths.get("uploads/" + randomGeneratedName);
            image = imageRepository.save(image);
            Files.copy(file.getInputStream(), saveTo);
        } catch (IOException e) {
            throw new RuntimeException("Error while saving an image");
        }
        return image;
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
