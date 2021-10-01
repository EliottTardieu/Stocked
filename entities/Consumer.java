package com.stocked.entities;

import com.stocked.utils.Logger;

public class Consumer {

    private int id;
    private String name;
    private final Stock stock = Stock.getInstance();

    private void pop(){
        stock.pop();
        Logger.fine(name+" consumed from stock, last element is now "+stock.getLastContent().getValue());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
