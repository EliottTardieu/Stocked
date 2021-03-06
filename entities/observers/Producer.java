package com.stocked.entities.observers;

import com.stocked.App;
import com.stocked.entities.Product;
import com.stocked.entities.stock.StockController;
import com.stocked.entities.stock.StockStatus;
import com.stocked.utils.Logger;

import java.util.UUID;

public class Producer implements Observer{

    private final UUID id;
    private final String name;
    private Product pendingProduct = null;
    private final StockController observedObject;
    private StockStatus knownStatus;


    public Producer(String name){
        this.id = UUID.randomUUID();
        this.name = name;
        this.observedObject = App.getInstance().getStock();
        observedObject.addToObservers(this);
    }

    @Override
    public void update() {
        this.knownStatus = observedObject.getStatus();
        Logger.fine(this.name+" updated successfully after stock changes.");
        System.out.println("Status: "+knownStatus.toString());
    }

    public Boolean produce(Product product){
        if(observedObject.push(product)) {
            Logger.fine("Producer: "+name + " added " + product.getId() + " to stock.");
            return true;
        } else {
            Logger.severe("Producer: "+name+" failed to add to stock: Stock is full. Producer added to the queue.");
            this.pendingProduct = product;
            observedObject.addToProducerQueue(this);
            return false;
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Product getPendingProduct() {
        return pendingProduct;
    }

    public StockStatus getKnownStatus(){ return  knownStatus; }

}
