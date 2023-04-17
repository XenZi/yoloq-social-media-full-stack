package com.example.yoloq.models;


import com.example.yoloq.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column
    private Role role;
    
    @Column
    private LocalDateTime lastLogin;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String profileImagePath;

    @Column(nullable = false)
    private boolean isDeleted;

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<Comment>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User friendWith;

    @OneToMany
    private Set<User> friendsList = new HashSet<>();

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Post> posts = new HashSet<Post>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id")
    private Set<Reaction> reactions = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id")
    private Set<FriendRequest> friendRequestsFrom = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id")
    private Set<FriendRequest> friendRequestsTo = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id")
    private Set<GroupRequest> groupRequests = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id")
    private Set<GroupAdmin> groupAdmins = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id")
    private Set<Banned> bans = new HashSet<>();

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setUser(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setUser(null);
    }

    public void addPost(Post post) {
        posts.add(post);
        post.setPostedBy(this);
    }

    public void removePost(Post post) {
        posts.remove(post);
        post.setPostedBy(null);
    }

    public void addReaction(Reaction reaction) {
        reactions.add(reaction);
        reaction.setReactedBy(this);
    }

    public void removeReaction(Reaction reaction) {
        reactions.remove(reaction);
        reaction.setReactedBy(null);
    }


}