package com.example.yoloq.controller;

import com.example.yoloq.models.dto.ReportDTO;
import com.example.yoloq.models.dto.requests.UpdateReportDTO;
import com.example.yoloq.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/reports")
public class ReportController {

    private final ReportService service;

    @Autowired
    public ReportController(ReportService service) {
        this.service = service;
    }

    @PostMapping
    private ResponseEntity<ReportDTO> create(@RequestBody ReportDTO reportDTO) {
        return new ResponseEntity<>(this.service.createReport(reportDTO), HttpStatus.OK);
    }

    @GetMapping
    private ResponseEntity<Set<ReportDTO>> getAllReports() {
        return new ResponseEntity<>(this.service.getAllReports(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    private ResponseEntity<ReportDTO> update(@RequestBody UpdateReportDTO updateReportDTO) {
        return new ResponseEntity<>(this.service.updateReport(updateReportDTO), HttpStatus.OK);
    }

    @GetMapping("/group/{id}")
    private ResponseEntity<Set<ReportDTO>> getAllByGroupID(@PathVariable Integer id) {
        return new ResponseEntity<>(this.service.getAllReportsForGroup(id), HttpStatus.OK);
    }

    @GetMapping("/admin")
    private ResponseEntity<Set<ReportDTO>> getAllForAdmin() {
        return new ResponseEntity<>(this.service.getAllReportsForAdmin(), HttpStatus.OK);
    }
}
