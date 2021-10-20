package com.stocked;

import com.stocked.entities.stock.StockController;
import com.stocked.entities.stock.StockReader;

public class App {

    private static App instance = null;
    private final StockController stock;
    private final StockReader stockReader;

    public App(){
        this.stockReader = new StockReader();
        this.stock = new StockController();
        instance = this;
    }

    public void init(int maxSize){
        this.stockReader.init();
        this.stock.init();
        stock.setMaxSize(maxSize);
    }

    public static App getInstance() {
        return instance;
    }

    public StockReader getStockReader() {
        return stockReader;
    }

    public StockController getStock() {
        return stock;
    }
}
