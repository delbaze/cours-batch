package com.example.demo.job;

import com.example.demo.model.Produit;
import com.example.demo.model.ProduitCsv;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ProduitItemProcessor implements ItemProcessor<ProduitCsv, Produit> {
    private final RejetCollector rejetCollector;
    private String fournisseur;

    public ProduitItemProcessor(RejetCollector rejetCollector) {
        this.rejetCollector = rejetCollector;
    }

    // new ProduitItemProcessor(rejetCollector)

    @Override
    public Produit process(ProduitCsv item) throws Exception {

        // ici je peux travailler à partir du ProduitCsv et obtenir en sortie
        // un Produit
        // un prix négatif signale un enreigstrement invalide, on retourne
        // null (ça exclut l'item du chunk en cours
        if (item.getPrix() < 0 || item.getStock() < 0) {
            rejetCollector.ajouter(item);
            return null;
        }
        String reference = item.getReference().trim().toUpperCase();

        if (fournisseur != null && !fournisseur.isBlank()){
            reference = fournisseur.toUpperCase() + "-" + reference;
        }
         // on procède à la transformation : on doit créer un Produit à partir de ProduitCsv

        Produit produit = new Produit();
        produit.setReference(reference);
        produit.setDesignation(item.getDesignation().trim());
        produit.setPrix(item.getPrix());
        produit.setStock(item.getStock());
        produit.setDateImport(LocalDate.now());

        return produit;
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.fournisseur = stepExecution.getJobParameters().getString("fournisseur");
    }
}
