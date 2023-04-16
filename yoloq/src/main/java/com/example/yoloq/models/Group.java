package com.example.yoloq.models;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@RequiredArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private String creationDate;

    @Column(nullable = false)
    private boolean isSuspended;

    @Column
    private boolean suspendedReason;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id")
    private Set<GroupRequest> requests = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id")
    private Set<Post> containsPosts = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id")
    private Set<GroupAdmin> admins = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id")
    private Set<Banned> bans = new HashSet<>();
}
