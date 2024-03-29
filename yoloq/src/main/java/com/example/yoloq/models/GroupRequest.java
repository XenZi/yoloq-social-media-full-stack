package com.example.yoloq.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@RequiredArgsConstructor
public class GroupRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Boolean approved;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime at;

    @ManyToOne
    private User requestFrom;

    @ManyToOne
    private Group forGroup;
}
