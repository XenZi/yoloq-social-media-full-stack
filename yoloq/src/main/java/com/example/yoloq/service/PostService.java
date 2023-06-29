package com.example.yoloq.service;

import com.example.yoloq.models.Post;
import com.example.yoloq.models.dto.PostDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface PostService {
    PostDTO save(PostDTO newPost, MultipartFile[] images);
    List<PostDTO> getAll();
    PostDTO update(PostDTO updateDTO, MultipartFile[] images);
    PostDTO findOne(int id);
    Post findOnePost(int id);
    PostDTO delete(int id);
    Post mapPostDTOToEntity(PostDTO postDTO);
    PostDTO mapEntityToPostDTO(Post post);
    Set<PostDTO> findAllByGroupID(int id);

}
