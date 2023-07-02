package com.example.yoloq.service;

import com.example.yoloq.models.Report;
import com.example.yoloq.models.dto.ReportDTO;
import com.example.yoloq.models.dto.requests.UpdateReportDTO;

import java.util.Set;

public interface ReportService {

    ReportDTO createReport(ReportDTO reportDTO);
    Set<ReportDTO> getAllReports();
    Set<ReportDTO> getAllReportsForGroup(int groupID);
    ReportDTO updateReport(UpdateReportDTO updateReportDTO);
    Report findOneEntityById(int id);
    Set<ReportDTO>getAllReportsForAdmin();
}
