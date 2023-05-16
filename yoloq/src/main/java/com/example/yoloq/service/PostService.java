package com.example.yoloq.service;

import com.example.yoloq.models.Post;
import com.example.yoloq.models.dto.PostDTO;

import java.util.List;

public interface PostService {
    PostDTO save(PostDTO newPost);
    List<PostDTO> getAll();
    PostDTO update(PostDTO updateDTO);
    PostDTO findOne(int id);
    PostDTO delete(int id);
}
