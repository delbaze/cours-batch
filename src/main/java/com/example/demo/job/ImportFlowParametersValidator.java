package com.example.demo.job;

import org.springframework.batch.core.job.parameters.InvalidJobParametersException;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersValidator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class ImportFlowParametersValidator implements JobParametersValidator {


    @Override
    public void validate(JobParameters parameters) throws InvalidJobParametersException {
        String fichier = parameters.getString("fichier");
        if (fichier == null || fichier.isBlank()) {
            throw new IllegalArgumentException("Le paramètre fichier est obligatoire");
        }

        ClassPathResource resource = new ClassPathResource(fichier);
        if (!resource.exists()){
            throw new IllegalArgumentException("Le fichier est introuvable dans le classpath : " + fichier);
        }
    }
}
