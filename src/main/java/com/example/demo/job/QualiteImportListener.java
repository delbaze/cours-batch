package com.example.demo.job;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.stereotype.Component;

@Component
public class QualiteImportListener implements StepExecutionListener {
    @Override
    public ExitStatus afterStep(StepExecution stepExecution){
        long lu = stepExecution.getReadCount();
        long filtre = stepExecution.getFilterCount();

        if (lu > 0 && (double) filtre / lu > 0.1) {
            return new ExitStatus("QUALITE_INSUFFISANTE");

        }
        return stepExecution.getExitStatus();
    }
}
