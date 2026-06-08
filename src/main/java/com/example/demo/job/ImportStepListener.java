package com.example.demo.job;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.stereotype.Component;

@Component
public class ImportStepListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution){
        System.out.println("Démarrage du step : " + stepExecution.getStepName());
    }
    @Override
    public ExitStatus afterStep(StepExecution stepExecution){
        System.out.printf("Step terminé : lu=%d, écrit=%d, filtré=%d%n",
                stepExecution.getReadCount(),
                stepExecution.getWriteCount(),
                stepExecution.getFilterCount());


        return stepExecution.getExitStatus();
    }

}
