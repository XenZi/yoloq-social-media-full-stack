package com.example.yoloq.service.impl;

import com.example.yoloq.models.Comment;
import com.example.yoloq.models.Post;
import com.example.yoloq.models.Report;
import com.example.yoloq.models.dto.CommentDTO;
import com.example.yoloq.models.dto.PostDTO;
import com.example.yoloq.models.dto.ReportDTO;
import com.example.yoloq.models.dto.requests.UpdateReportDTO;
import com.example.yoloq.repository.ReportRepository;
import com.example.yoloq.service.CommentService;
import com.example.yoloq.service.PostService;
import com.example.yoloq.service.ReportService;
import com.example.yoloq.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository repository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final CommentService commentService;
    private final PostService postService;

    @Autowired
    public ReportServiceImpl(ReportRepository repository, ModelMapper modelMapper, UserService userService, CommentService commentService, PostService postService) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.commentService = commentService;
        this.postService = postService;
    }

    @Override
    public ReportDTO createReport(ReportDTO reportDTO) {
        Report report = modelMapper.map(reportDTO, Report.class);
        report.setByUser(this.userService.findLoggedUser());
        report.setReportReason(report.getReportReason());
        report.setDeleted(false);
        if (reportDTO.getPostID() != null) {
            report.setReportedPost(this.postService.findOnePost(reportDTO.getPostID()));
        } else {
            report.setReportedComment(this.commentService.findOneEntity(reportDTO.getCommentID()));
        }
        report.setTimestamp(LocalDateTime.now());
        report = repository.save(report);
        return mapToDto(report);
    }

    @Override
    public Set<ReportDTO> getAllReports() {
        return this.repository.findAll().stream().map(this::mapToDto).collect(Collectors.toSet());
    }

    @Override
    public Set<ReportDTO> getAllReportsForGroup(int groupID) {
        return this.repository.findAll().stream()
                .filter(report -> {
                    if (report.getReportedComment() != null) {
                        Comment topLevelComment = this.commentService.findTopLevelParent(report.getReportedComment().getId());
                        Post post = topLevelComment.getPost();
                        return post.getPostedInGroup() != null && post.getPostedInGroup().getId() == groupID;
                    } else {
                        return report.getReportedPost().getPostedInGroup() != null && report.getReportedPost().getId() == groupID;
                    }
                })
                .map(this::mapToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public ReportDTO updateReport(UpdateReportDTO updateReportDTO) {
        Report report = this.findOneEntityById(updateReportDTO.getReportID());
        if (!updateReportDTO.isDecision()) {
            report.setDeleted(true);
            report = repository.save(report);
            return mapToDto(report);
        }
        if (report.getReportedComment() != null) {
            this.commentService.delete(report.getReportedComment().getId());
        } else {
            this.postService.delete(report.getReportedPost().getId());
        }
        report.setDeleted(true);
        report = repository.save(report);
        return mapToDto(report);
    }

    @Override
    public Report findOneEntityById(int id) {
        return this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    }

    @Override
    public Set<ReportDTO> getAllReportsForAdmin() {
        return this.repository.findAll().stream()
                .filter(report -> {
                    if (report.getReportedComment() != null) {
                        Comment topLevelComment = this.commentService.findTopLevelParent(report.getReportedComment().getId());
                        Post post = topLevelComment.getPost();
                        return post.getPostedInGroup() == null;
                    } else {
                        return report.getReportedPost().getPostedInGroup() == null;
                    }
                })
                .map(this::mapToDto)
                .collect(Collectors.toSet());
    }

    private ReportDTO mapToDto(Report report) {
        ReportDTO reportDTO = modelMapper.map(report, ReportDTO.class);
        if (report.getReportedComment() != null) {
            reportDTO.setReportedComment(modelMapper.map(report.getReportedComment(), CommentDTO.class));
        } else {
            reportDTO.setReportedPost(modelMapper.map(report.getReportedPost(), PostDTO.class));
        }
        return reportDTO;
    }
}
