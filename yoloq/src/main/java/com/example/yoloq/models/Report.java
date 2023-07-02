package com.example.yoloq.models;


import com.example.yoloq.enums.ReportReason;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private ReportReason reportReason;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private boolean isDeleted;

    @ManyToOne
    private Comment reportedComment;

    @ManyToOne
    private Post reportedPost;

    @ManyToOne
    private User byUser;

}
