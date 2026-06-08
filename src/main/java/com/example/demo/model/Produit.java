package com.example.demo.model;
// représente un produit tel qu'il sera inséré en base
// ce modèle est enrichi par le processor : la référence est normalisé,
// la date d'import est ajoutée

import java.time.LocalDate;

public class Produit {
    private String reference;
    private String designation;
    private double prix;
    private int stock;
    private LocalDate dateImport;


    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public LocalDate getDateImport() {
        return dateImport;
    }

    public void setDateImport(LocalDate dateImport) {
        this.dateImport = dateImport;
    }
}
