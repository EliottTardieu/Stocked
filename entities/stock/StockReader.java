package com.stocked.entities.stock;

import com.stocked.App;
import com.stocked.entities.Product;
import com.stocked.utils.Logger;

public class StockReader {

    private StockController stock;

    public StockReader(){}

    public void init(){
        this.stock = App.getInstance().getStock();
    }

    public void push(StockStatus status, Product product){
        if(status == StockStatus.STATUS_DENIED){
            Logger.severe("Unable to push "+product.getValue()+" (Id: "+product.getId()+")");
        }else if(status == StockStatus.STATUS_QUEUED){
            Logger.fine("Stock was successfully pushed from queue "+product.getValue()+" (Id: "+product.getId()+")");
        }else if(status == StockStatus.STATUS_ALLOWED){
            Logger.fine("Stock was successfully pushed "+product.getValue()+" (Id: "+product.getId()+")");
        }
        this.displayLast();
    }

    public void pop(StockStatus status){
        if(status == StockStatus.STATUS_DENIED){
            Logger.severe("Unable to pop: the stock is empty.");
        }else if(status == StockStatus.STATUS_QUEUED){
            Logger.fine("Stock was successfully poped from queue.");
        }else if(status == StockStatus.STATUS_ALLOWED){
            Logger.fine("Stock was successfully poped.");
        }
        this.displayLast();
    }

    public void clear(StockStatus status){
        if(status == StockStatus.STATUS_DENIED){
            Logger.severe("Unable to clear: the stock is already empty.");
        }else if(status == StockStatus.STATUS_ALLOWED){
            Logger.fine("Stock was successfully cleared.");
        }
    }

    private void displayLast(){
        if(stock.getContent().size()+1 > 0) {
            System.out.println("Product ID: "+stock.getContent().lastElement().getId()+" with value: "+stock.getContent().lastElement().getValue());
        } else {
            System.out.println("No product in Stock.");
        }
    }

    public void display(){
        if(stock.getContent().isEmpty()){
            System.out.println("Empty");
        } else {
            for (Product product : stock.getContent()) {
                System.out.println("Product ID: "+product.getId()+" with value: "+product.getValue());
            }
        }
    }

}
