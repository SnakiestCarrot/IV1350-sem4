package se.kth.iv1350.amazingpos.integration;

import java.util.ArrayList;

import se.kth.iv1350.amazingpos.model.FinalSaleDTO;

public class CompositeDiscounter implements Discounter{
    private ArrayList<Discounter> discounterList = new ArrayList<Discounter>();

    @Override
    public void discountSale (FinalSaleDTO saleDTO, int customerIdentification) {

    }

    void addDiscounter (Discounter discounter) {
        discounterList.add(discounter);
    }

}
