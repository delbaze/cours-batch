package com.example.demo.job;

import com.example.demo.model.ProduitCsv;
import org.springframework.batch.core.listener.ItemReadListener;
import org.springframework.stereotype.Component;

@Component
public class ProduitItemReadListener  implements ItemReadListener<ProduitCsv> {

    @Override
    public void onReadError(Exception ex) {
        System.out.println("ERreur de lecture : " + ex.getMessage());
    }
}
