package com.stocked.entities.observers;

import com.stocked.App;
import com.stocked.entities.stock.StockController;
import com.stocked.entities.stock.StockStatus;
import com.stocked.utils.Logger;

public class Consumer implements Observer{

    private final int id;
    private final String name;
    private final StockController observedObject;

    private StockStatus knownStatus;

    public Consumer(int id, String name){
        this.id = id;
        this.name = name;
        this.observedObject = App.getInstance().getStock();
        observedObject.addToObservers(this);
    }

    @Override
    public void update() {
        this.knownStatus = observedObject.getStatus();
        Logger.fine(this.name+" updated successfully after stock changes.");
    }

    public void consume(){
        if(observedObject.pop()) {
            Logger.fine("Consumer: "+name+" consumed from stock.");
        } else {
            Logger.severe("Consumer: "+name+" failed to consume from stock: Stock is empty. Consumer added to the queue.");
            observedObject.addToConsumerQueue(this);
        }

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public StockStatus getKnownStatus(){ return  knownStatus; }

}
