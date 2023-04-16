package com.example.yoloq.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table
@RequiredArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private LocalDateTime timeStamp;

    @Column(nullable = false)
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private Set<Reaction> reactions = new HashSet<>();

    @ManyToOne
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment")
    private Set<Comment> replies = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "id")
    private Set<Report> reports = new HashSet<>();

    public void addReport(Report report) {
        reports.add(report);
        report.setReportedComment(this);
    }

    public void removeReport(Report report) {
        reports.remove(report);
        report.setReportedComment(null);
    }
    public void addReply(Comment reply) {
        replies.add(reply);
        reply.setParentComment(this);
    }

    public void removeReply(Comment reply) {
        replies.remove(reply);
        reply.setParentComment(null);
    }

}

