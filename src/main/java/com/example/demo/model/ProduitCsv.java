package com.example.demo.model;

// représente une ligne du fichier CSV, avant toute transformation.
// les noms des champs doivent correspondre EXACTEMENT aux colonnes du CSV
public class ProduitCsv {
    private String reference;
    private String designation;
    private double prix;
    private int stock;

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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

}
