package com.stocked.entities;

import com.stocked.App;

public class StockReader {

    private final Stock stock;

    public StockReader(){
        this.stock = App.getInstance().getStock();
    }

    public void display(){
        if(stock.content.isEmpty()){
            System.out.println("Empty");
        } else {
            for (Product product : stock.getContent()) {
                System.out.println("Product ID: "+product.getId()+" with value: "+product.getValue());
            }
        }
    }

}
