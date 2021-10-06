package com.stocked.utils;

import com.stocked.App;
import com.stocked.entities.Stock;

public class Alarm {

    private static final Stock stock = App.getInstance().getStock();

    public static final int STATUS_FULL = 1;
    public static final int STATUS_EMPTY = 2;
    public static final int STATUS_STANDARD = 0;

    private static int ALERT_FULL = stock.getMaxSize()/5;
    private static int ALERT_EMPTY = ALERT_FULL*4;

    public static void checkStatus(){
        if(stock.getMaxSize() - stock.getContent().size() <= ALERT_FULL){
            stock.setStatus(STATUS_FULL);
        } else if(stock.getMaxSize() - stock.getContent().size() >= ALERT_EMPTY){
            stock.setStatus(STATUS_EMPTY);
        }else {
            stock.setStatus(STATUS_STANDARD);
        }
        Alarm.displayStatus();
    }

    public static void displayStatus(){
        switch (stock.getStatus()){
            case 0:
                Logger.fine("Stock is fine, not full.");
                break;
            case 1:
                Logger.warning("Stock is full or nearly full.");
                break;
            case 2:
                Logger.warning("Stock is empty or nearly empty.");
                break;
            default:
                Logger.severe("Unknown status code.");
                break;

        }

    }

}
