package com.example.yoloq.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
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

    // ovo je upitno
    @ElementCollection
    @CollectionTable(name="PostImages", joinColumns = @JoinColumn(name="post_id", referencedColumnName = "id"))
    @Column
    private Set<String> imagePaths = new HashSet<>();

    @Column(nullable = false)
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User postedBy;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id")
    private Set<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id")
    private Set<Reaction> reactions;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "id")
    private Set<Report> reports = new HashSet<>();

    @ManyToOne
    private Group postedInGroup;
    public void addReport(Report report) {
        reports.add(report);
        report.setReportedPost(this);
    }

    public void removeReport(Report report) {
        reports.remove(report);
        report.setReportedComment(null);
    }
    public void addReaction(Reaction reaction) {
        reactions.add(reaction);
        reaction.setPostReactedTo(this);
    }

    public void removeReaction(Reaction reaction) {
        reactions.remove(reaction);
        reaction.setPostReactedTo(null);
    }
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setPost(null);
    }
}