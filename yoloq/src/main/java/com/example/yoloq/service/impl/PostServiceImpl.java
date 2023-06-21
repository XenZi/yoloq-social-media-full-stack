package com.example.yoloq.service.impl;


import com.example.yoloq.exception.IncompleteRequestException;
import com.example.yoloq.exception.ResourceNotFoundException;
import com.example.yoloq.exception.UnauthorizedAccessException;
import com.example.yoloq.models.Image;
import com.example.yoloq.models.Post;
import com.example.yoloq.models.User;
import com.example.yoloq.models.dto.PostDTO;
import com.example.yoloq.models.dto.UserDTO;
import com.example.yoloq.repository.PostRepository;
import com.example.yoloq.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final FileService fileService;
    private final ImageService imageService;
    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           ModelMapper modelMapper,
                           UserService userService,
                           FileService fileService,
                           ImageService imageService) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.fileService = fileService;
        this.imageService = imageService;
    }

    @Override
    public PostDTO save(PostDTO newPost, MultipartFile[] images) {
        Post post =  mapPostDTOToEntity(newPost);
        User user = userService.findLoggedUser();
        if (images != null) {
            for (MultipartFile file:
                    images) {
                post.getImages().add(fileService.uploadImage(file));
            }
        }
        post.setPostedBy(user);
        post.setCreationDate(LocalDateTime.now());
        post = postRepository.save(post);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        PostDTO postDTO = modelMapper.map(post, PostDTO.class);

        for (Image image :
                post.getImages()) {
            image.setPostedIn(post);
            postDTO.getImagePaths().add(image.getName());
            imageService.update(image);
        }
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
            postDTO.setImagePaths(imageService.findAllPathsForPost(post));
            postDTO.setPostedBy(modelMapper.map(post.getPostedBy(), UserDTO.class));
            postDTOs.add(postDTO);
        }
        return postDTOs;
    }

    @Override
    public PostDTO update(PostDTO updateDTO, MultipartFile[] images) {
        Post post = this.findOneById(updateDTO.getId());

        if (!isTheSameUserLoggedIn(post.getPostedBy())) {
            throw new UnauthorizedAccessException("You are not authorized");
        }

        if (updateDTO.getContent().equals("") && updateDTO.getContent().length() < 5) {
            throw new IncompleteRequestException("Content must be more than 5 characters and can't be empty!");
        }

        post.setImages(new HashSet<>());
        if (images != null) {
            for (MultipartFile file:
                    images) {
                Image image = fileService.uploadImage(file);
                post.getImages().add(image);
                image.setPostedIn(post);
            }
        }
        Post finalPost = post;
        updateDTO.getImagePaths().forEach(path -> {
            Image image = imageService.findByName(path);
            image.setPostedIn(finalPost);
            finalPost.getImages().add(image);
        });
        post.setContent(updateDTO.getContent());
        post = this.postRepository.save(post);
        return mapEntityToPostDTO(post);
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
        postDTO.setImagePaths(imageService.findAllPathsForPost(foundPost.get()));
        return postDTO;
    }

    @Override
    public Post findOnePost(int id) {
        return postRepository.findFirstByIdWithCollections(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));
    }

    @Override
    public PostDTO delete(int id) {
        Post post = this.findOneById(id);

        if (!isTheSameUserLoggedIn(post.getPostedBy())) {
            throw new UnauthorizedAccessException("You are not authorized");
        }

        post.setDeleted(true);
        postRepository.save(post);
        return mapEntityToPostDTO(post);
    }

    @Override
    public Post mapPostDTOToEntity(PostDTO postDTO) {
        return modelMapper.map(postDTO, Post.class);
    }

    @Override
    public PostDTO mapEntityToPostDTO(Post post) {
        return modelMapper.map(post, PostDTO.class);
    }

    private boolean isTheSameUserLoggedIn(User userFromUpdate) {
        User user = userService.findLoggedUser();
        return user.getId() == userFromUpdate.getId();
    }
}
