package com.example.yoloq.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @ElementCollection
    @CollectionTable(name="PostImages", joinColumns = @JoinColumn(name="image_url"))
    @Column(name="imagePaths")
    private Set<String> imagePaths;

    @Column(nullable = false)
    private boolean isDeleted;
}
