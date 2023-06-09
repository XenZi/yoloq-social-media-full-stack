package com.example.yoloq.service.impl;

import com.example.yoloq.models.Image;
import com.example.yoloq.models.Post;
import com.example.yoloq.repository.ImageRepository;
import com.example.yoloq.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Image update(Image image) {
        return this.imageRepository.save(image);
    }

    @Override
    public Set<String> findAllPathsForPost(Post post) {
        return post.getImages().stream().map(Image::getName).collect(Collectors.toSet());
    }
}
