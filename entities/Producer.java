package com.stocked.entities;

import com.stocked.utils.Logger;

public class Producer {

    private int id;
    private String name;
    private static final Stock stock = Stock.getInstance();
    private Product pendingProduct;

    Producer(int id, String name){
        this.id = id;
        this.name = name;
        this.pendingProduct = null;
    }

    private void produce(Product product){
        if(stock.push(product)) {
            Logger.fine("Producer: "+name + " added " + product.getValue() + " to stock.");
        } else {
            Logger.severe("Producer: "+name+" failed to add to stock: Stock is full or queue is not empty. Producer added to the queue.");
            this.pendingProduct = product;
            stock.addToProducerQueue(this);
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Product getPendingProduct() {
        return pendingProduct;
    }
}
