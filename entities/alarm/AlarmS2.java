package com.stocked.entities.alarm;

import com.stocked.App;
import com.stocked.entities.Product;
import com.stocked.entities.stock.StockController;
import com.stocked.entities.stock.StockStatus;

import java.time.LocalTime;
import java.util.ArrayList;

public class AlarmS2 implements AlarmStrategy{

    private final StockController stock;

    private int ALERT_MOVING;
    private int TIMER;

    public AlarmS2(){
        this.stock = App.getInstance().getStock();
        this.ALERT_MOVING = 5;
        this.TIMER = 2;
    }

    @Override
    public StockStatus checkStatus() {
        StockStatus toReturn = StockStatus.STATUS_STANDARD;

        //This will count the amount of products pushed or popped in the "TIMER" last minutes.
        int nbProductsPush = 0;
        int nbProductsPop = 0;
        for (Product product : stock.getHistory()) {
            ArrayList<Integer> timer = product.getTimer();
            if(timer.get(0) == LocalTime.now().getHour()){
                if((timer.get(1) + this.TIMER)%60 >= LocalTime.now().getMinute()) nbProductsPush++;
            } else if(timer.get(2) == LocalTime.now().getHour()){
                if((timer.get(3) + this.TIMER)%60 >= LocalTime.now().getMinute()) nbProductsPop++;
            }
        }

        if(nbProductsPush >= ALERT_MOVING){
            toReturn = StockStatus.STATUS_FASTFULL;
        } else if(nbProductsPop >= ALERT_MOVING){
            toReturn = StockStatus.STATUS_FASTEMPTY;
        }

        return toReturn;
    }

    @Override
    public void modify(int threshold, int... time) {
        if(threshold > 1) this.ALERT_MOVING = threshold;
        if(time[0] >= 1) this.TIMER = time[0];
    }

}
