package com.stocked.entities.observers;

import com.stocked.App;
import com.stocked.entities.stock.StockController;
import com.stocked.entities.stock.StockStatus;
import com.stocked.utils.Logger;

import java.util.UUID;

public class Consumer implements Observer{

    private final UUID id;
    private final String name;
    private final StockController observedObject;

    private StockStatus knownStatus;

    public Consumer(String name){
        this.id = UUID.randomUUID();
        this.name = name;
        this.observedObject = App.getInstance().getStock();
        observedObject.addToObservers(this);
    }

    @Override
    public void update() {
        this.knownStatus = observedObject.getStatus();
        Logger.fine(this.name+" updated successfully after stock changes.");
    }

    public Boolean consume(){
        if(observedObject.pop()) {
            Logger.fine("Consumer: "+name+" consumed from stock.");
            return true;
        } else {
            Logger.severe("Consumer: "+name+" failed to consume from stock: Stock is empty. Consumer added to the queue.");
            observedObject.addToConsumerQueue(this);
            return false;
        }

    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public StockStatus getKnownStatus(){ return  knownStatus; }

}
