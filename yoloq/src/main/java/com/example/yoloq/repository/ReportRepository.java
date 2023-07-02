package com.example.yoloq.repository;

import com.example.yoloq.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Integer> {

    @Query("SELECT DISTINCT r FROM Report r WHERE r.isDeleted = false")
    List<Report> findAll();

}
