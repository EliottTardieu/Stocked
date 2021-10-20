package com.stocked.entities.alarm;

import com.stocked.App;
import com.stocked.entities.stock.StockController;
import com.stocked.entities.stock.StockStatus;
import com.stocked.utils.Logger;

public class AlarmS1 implements AlarmStrategy{

    private final StockController stock;

    private int ALERT_FULL;
    private int ALERT_EMPTY;

    public AlarmS1(){
        this.stock = App.getInstance().getStock();
        this.ALERT_FULL = Math.max(stock.getMaxSize()/5, 1);    //Ensure ALERT_FULL != 0
        this.ALERT_EMPTY = ALERT_FULL*4;
    }

    @Override
    public StockStatus checkStatus() {
        StockStatus toReturn = StockStatus.STATUS_STANDARD;
        if(stock.getMaxSize() - stock.getContent().size() <= ALERT_FULL){
            toReturn = StockStatus.STATUS_FULL;
        } else if(stock.getMaxSize() - stock.getContent().size() >= ALERT_EMPTY){
            toReturn = StockStatus.STATUS_EMPTY;
        }
        return toReturn;
    }

    @Override
    public void modify(int threshold, int... time) {
        if(threshold <= 4){
            Logger.warning("Threshold for alarmStrategy wasn't changed because \"<= 4\".");
        } else {
            this.ALERT_FULL = stock.getMaxSize()/threshold;
            this.ALERT_EMPTY = ALERT_FULL*4;
        }
    }
}
