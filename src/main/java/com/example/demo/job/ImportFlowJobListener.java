package com.example.demo.job;

import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class ImportFlowJobListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        // ici avcant le job
        System.out.println("Démarrage du job");
    }
    @Override
    public void afterJob(JobExecution jobExecution) {
        // ici après le job
        System.out.println("Fin du job");
    }
}
