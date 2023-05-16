package com.example.yoloq.service.impl;


import com.example.yoloq.exception.ResourceNotFoundException;
import com.example.yoloq.models.Post;
import com.example.yoloq.models.User;
import com.example.yoloq.models.dto.PostDTO;
import com.example.yoloq.models.dto.UserDTO;
import com.example.yoloq.repository.PostRepository;
import com.example.yoloq.service.FileService;
import com.example.yoloq.service.PostService;
import com.example.yoloq.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final FileService fileService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           ModelMapper modelMapper,
                           UserService userService,
                           FileService fileService) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.fileService = fileService;
    }

    public PostDTO save(PostDTO newPost) {
        Post post = modelMapper.map(newPost, Post.class);
        User user = userService.findLoggedUser();
        if (newPost.getImages() != null) {
            for (MultipartFile file:
                    newPost.getImages()) {
                post.getImagePaths().add(fileService.uploadImage(file));
            }
        }
        post.setPostedBy(user);
        post.setCreationDate(LocalDateTime.now());
        post = postRepository.save(post);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        PostDTO postDTO = modelMapper.map(post, PostDTO.class);
        postDTO.setPostedBy(userDTO);
        return postDTO;
    }

    @Override
    public List<PostDTO> getAll() {
        List<Post> posts = this.postRepository.findAllWithComments();
        List<PostDTO> postDTOs = new ArrayList<>();
        for (Post post:
             posts) {
            PostDTO postDTO = modelMapper.map(post, PostDTO.class);
            postDTO.setPostedBy(modelMapper.map(post.getPostedBy(), UserDTO.class));
            postDTOs.add(postDTO);
        }
        return postDTOs;
    }

    @Override
    public PostDTO update(PostDTO updateDTO) {
        Post post = this.findOneById(updateDTO.getId());
        post.setContent(updateDTO.getContent());
        System.out.println(updateDTO.getContent());
        this.postRepository.save(post);
        return modelMapper.map(post, PostDTO.class);
    }

    private Post findOneById(int id) {
        Optional<Post> foundPost = postRepository.findFirstByIdWithCollections(id);
        if (foundPost.isEmpty()) {
            throw new ResourceNotFoundException("Entity not found");
        }
        return foundPost.get();
    }

    public PostDTO findOne(int id) {
        Optional<Post> foundPost = postRepository.findFirstByIdWithCollections(id);
        if (foundPost.isEmpty()) {
            throw new ResourceNotFoundException("Entity not found");
        }
        PostDTO postDTO = modelMapper.map(foundPost.get(), PostDTO.class);
        postDTO.setComments(postDTO.getComments());
        return modelMapper.map(foundPost.get(), PostDTO.class);
    }

    @Override
    public PostDTO delete(int id) {
        Post post = this.findOneById(id);
        post.setDeleted(true);
        postRepository.save(post);
        return modelMapper.map(post, PostDTO.class);
    }
}
