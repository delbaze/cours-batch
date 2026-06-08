package com.example.demo.web;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/batch")
public class BatchController {
    private final JobOperator jobOperator;
    private final Job importProduitsJob;

    public BatchController(JobOperator jobOperator, Job importProduitsJob) {
        this.jobOperator = jobOperator;
        this.importProduitsJob = importProduitsJob;
    }

    @PostMapping("/import")
    public ResponseEntity<String> lancerImport(@RequestParam String fichier) throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addString("fichier", fichier)
                .addLocalDate("date", LocalDate.now())
                .addLong("timestamp", System.currentTimeMillis()).toJobParameters();

        jobOperator.start(importProduitsJob, params);
        return ResponseEntity.ok("Job lancé avec le fichier : " + fichier);
    }
}
