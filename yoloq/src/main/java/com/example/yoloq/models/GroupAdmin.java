package com.example.yoloq.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Setter
@Getter
@RequiredArgsConstructor
public class GroupAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Group adminAt;

    @OneToMany
    private Set<Banned> bans = new HashSet<>();
}
