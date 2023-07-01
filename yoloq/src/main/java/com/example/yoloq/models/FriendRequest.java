package com.example.yoloq.models;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Setter
@Getter
@Table
@Entity
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Boolean approved;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime at;

    @Column(nullable = false)
    private boolean isDeleted;

    @ManyToOne
    private User userFrom;

    @ManyToOne
    private User userTo;
}
