package com.stocked.entities.alarm;

import com.stocked.App;
import com.stocked.entities.stock.StockController;
import com.stocked.entities.stock.StockStatus;
import com.stocked.utils.Logger;

public class Alarm {

    private StockController stock;
    private Object strategy = new AlarmS1();    //Default strategy set to S1

    public Alarm(){
        this.stock = App.getInstance().getStock();
    }

    public void checkStatus(){
        StockStatus status = StockStatus.STATUS_STANDARD;
        if(strategy.getClass() == AlarmS1.class){
            status = ((AlarmS1)strategy).checkStatus();
        } else if(strategy.getClass() == AlarmS2.class){
            status = ((AlarmS2)strategy).checkStatus();
        } else {
            Logger.severe("Invalid strategy.");
        }
        stock.update(status);
        this.displayStatus();
    }

    public void displayStatus(){
        switch (stock.getStatus()){
            case STATUS_STANDARD:
                Logger.fine("Stock is fine, not full.");
                break;
            case STATUS_FULL:
                Logger.warning("Stock is full or nearly full.");
                break;
            case STATUS_EMPTY:
                Logger.warning("Stock is empty or nearly empty.");
                break;
            case STATUS_FASTFULL:
                Logger.warning("Stock getting filled very fast.");
                break;
            case STATUS_FASTEMPTY:
                Logger.warning("Stock is getting popped very fast.");
                break;
            default:
                Logger.severe("Unknown status code.");
                break;

        }

    }

    public void setStrategy(Object strategy) {
        if(strategy.getClass() == AlarmS1.class || strategy.getClass() == AlarmS2.class) {
            this.strategy = strategy;
            Logger.fine("Alarm strategy successfully updated");
        } else {
            Logger.severe("Unable to update alarm strategy: Unknown strategy.");
        }
    }

    public Object getCurrentStrategy() {
        return strategy;
    }
}
