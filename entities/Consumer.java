package com.stocked.entities;

import com.stocked.App;
import com.stocked.utils.Logger;

public class Consumer {

    private final int id;
    private final String name;
    private final Stock stock;

    Consumer(int id, String name){
        this.id = id;
        this.name = name;
        this.stock = App.getInstance().getStock();
    }

    private void pop(){
        if(stock.pop()) {
            Logger.fine("Consumer: "+name+" consumed from stock, last element is now "+stock.getLastContent().getValue());
        } else {
            Logger.severe("Consumer: "+name+" failed to consume from stock: Stock is empty or queue is not empty. Consumer added to the queue.");
            stock.addToConsumerQueue(this);
        }

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
