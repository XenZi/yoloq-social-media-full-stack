package com.example.yoloq.service;

import com.example.yoloq.models.Post;
import com.example.yoloq.models.dto.PostDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    PostDTO save(PostDTO newPost, MultipartFile[] images);
    List<PostDTO> getAll();
    PostDTO update(PostDTO updateDTO);
    PostDTO findOne(int id);
    PostDTO delete(int id);
}
