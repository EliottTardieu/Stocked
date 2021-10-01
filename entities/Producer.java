package com.stocked.entities;

import com.stocked.utils.Logger;

public class Producer {

    private int id;
    private String name;
    private final Stock stock = Stock.getInstance();

    private void produce(Product product){
        stock.push(product);
        Logger.fine(name+" added "+product.getValue()+" to stock.");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
