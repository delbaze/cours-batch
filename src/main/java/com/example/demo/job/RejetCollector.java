package com.example.demo.job;

import com.example.demo.model.ProduitCsv;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RejetCollector {

    private final List<ProduitCsv> rejets = new ArrayList<>();

    public void ajouter(ProduitCsv item) {
        rejets.add(item);
    }

    public List<ProduitCsv> getRejets() {
        return rejets;
    }

    public void reset() {
        rejets.clear();
    }
}
