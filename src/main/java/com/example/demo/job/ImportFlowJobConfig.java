package com.example.demo.job;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ImportFlowJobConfig {
    @Bean
    public Job importPorduitsJob(JobRepository jobRepository, Step loggerStep) {
        return new JobBuilder("importProduitsJob", jobRepository).start(loggerStep).build();
    }

    @Bean // step de type tasklet (exécute une action unique)
    public Step loggerStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
            return new StepBuilder("loggerStep", jobRepository).tasklet((contribution, chunkContext) -> {
                System.out.println("ImportFlow démarré");
//                JobParameters params = chunkContext.getStepContext().getStepExecution().getJobParameters();
//
//                String fichier = params.getString("fichier");
                return RepeatStatus.FINISHED;
        }, transactionManager).build();

    }
}
