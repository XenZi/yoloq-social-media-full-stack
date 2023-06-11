package com.example.yoloq.service;

import com.example.yoloq.models.Image;
import com.example.yoloq.models.Post;

import java.util.Set;

public interface ImageService {
    Image update(Image image);
    Set<String> findAllPathsForPost(Post post);
    Image findByName(String name);
}
