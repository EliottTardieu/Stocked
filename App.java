package com.stocked;

import com.stocked.entities.Stock;
import com.stocked.entities.StockReader;

public class App {

    private static App instance;
    private final Stock stock;
    private final StockReader stockReader;

    public App(){
        this.stock = new Stock();
        instance = this;
        this.stockReader = new StockReader();
    }

    public void init(int maxSize){
        stock.setMaxSize(maxSize);
    }

    public static App getInstance() {
        return instance;
    }

    public StockReader getStockReader() {
        return stockReader;
    }

    public Stock getStock() {
        return stock;
    }
}
