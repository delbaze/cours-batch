package com.example.demo.job;

import javax.sql.DataSource;

import com.example.demo.model.Produit;
import com.example.demo.model.ProduitCsv;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.batch.infrastructure.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.FlatFileItemWriter;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
public class ImportFlowJobConfig {
    @Bean
    public Job importProduitsJob(JobRepository jobRepository, Step importProduitsStep, Step ecrireRejetsStep) {
        return new JobBuilder("importProduitsJob", jobRepository).start(importProduitsStep).next(ecrireRejetsStep).build();
    }

    @Bean // step de type tasklet (exécute une action unique)
    public Step loggerStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("loggerStep", jobRepository).tasklet((contribution, chunkContext) -> {
            System.out.println("ImportFlow démarré");
//                JobParameters params = chunkContext.getStepContext().getStepExecution().getJobParameters();
//
//                String fichier = params.getString("fichier");
            return RepeatStatus.FINISHED;
        }, transactionManager).build();
    }

    @Bean
    public FlatFileItemReader<ProduitCsv> produitItemReader() {
        return new FlatFileItemReaderBuilder<ProduitCsv>()
                .name("produitItemReader")
                .resource(new ClassPathResource("produits.csv"))
                .delimited()
                .delimiter(",")
                .names("reference", "designation", "prix", "stock")
                .targetType(ProduitCsv.class)
                .linesToSkip(1)
                .build();
    }

    // le JdbcBatchItemWriter reçoit un Chunk<Produit>

    @Bean
    public JdbcBatchItemWriter<Produit> produitItemWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Produit>().dataSource(dataSource)
                .sql("""
                        MERGE INTO produit (reference, designation, prix, stock, date_import)
                        KEY(reference)
                        VALUES (:reference, :designation, :prix, :stock, :dateImport)
                        """)
                .beanMapped()
                .build();

    }


    @Bean
    public FlatFileItemWriter<ProduitCsv> rejetItemWriter() {
        return new FlatFileItemWriterBuilder<ProduitCsv>()
                .name("rejetItemWriter")
                .resource(new FileSystemResource("rejets.csv"))
                .delimited()
                .delimiter(",")
                .names("reference", "designation", "prix", "stock")
                .headerCallback(writer -> writer.write("reference,designation,prix,stock"))
                .build();
    }

    @Bean
    public Step ecrireRejetsStep(JobRepository jobRepository,
                                 PlatformTransactionManager transactionManager,
                                 FlatFileItemWriter<ProduitCsv> rejetItemWriter,
                                 RejetCollector rejetCollector
                                 ){
        return new StepBuilder("ecrireRejetsStep", jobRepository).tasklet((contribution, chunkContext) -> {
            List<ProduitCsv> rejets = rejetCollector.getRejets();
            if (!rejets.isEmpty()) {
                rejetItemWriter.open(chunkContext.getStepContext().getStepExecution().getExecutionContext());
                rejetItemWriter.write(new Chunk<>(rejets));
                rejetItemWriter.close();
                System.out.println(rejets.size() + " rejets écrits dans rejets.csv");
                ;
            }
            rejetCollector.reset();
            return RepeatStatus.FINISHED;
        }, transactionManager).build();
    }



    @Bean
    public Step importProduitsStep(JobRepository jobRepository,
                                   PlatformTransactionManager transactionManager,
                                   FlatFileItemReader<ProduitCsv> produitItemReader,
                                   ProduitItemProcessor produitItemProcessor,
                                   JdbcBatchItemWriter<Produit> produitItemWriter) {
        return new StepBuilder("importProduitsStep", jobRepository)
                .<ProduitCsv, Produit>chunk(10)
                .transactionManager(transactionManager)
                .reader(produitItemReader)
                .processor(produitItemProcessor)
                .writer(produitItemWriter)
                .build();
    }


}
