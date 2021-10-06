package com.stocked.entities;

import com.stocked.App;
import com.stocked.utils.Logger;

public class Producer {

    private final int id;
    private final String name;
    private Product pendingProduct = null;
    private final Stock stock;

    Producer(int id, String name){
        this.id = id;
        this.name = name;
        this.stock = App.getInstance().getStock();
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
