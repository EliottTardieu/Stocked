package com.stocked.entities;

import com.stocked.utils.Alarm;
import com.stocked.utils.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class Stock {

    private int maxSize = 25;
    private int status = 0;
    protected final Stack<Product> content = new Stack<>();
    private final LinkedList<Consumer> consumerQueue = new LinkedList<>();
    private final LinkedList<Producer> producerQueue = new LinkedList<>();

    //Push au stock depuis un producteur
    public Boolean push(Product product){
        if(content.size() >= maxSize){
            Logger.severe("Unable to push "+product.getValue()+" (Id: "+product.getId()+")");
            return false;
        } else if(!producerQueue.isEmpty()) {
            Logger.fine("Stock was successfully pushed from queue.");
            content.push(producerQueue.get(producerQueue.size()-1).getPendingProduct());
            producerQueue.removeFirst();
            this.displayLast();
            Alarm.checkStatus();
            return false;
        } else {
            Logger.fine("Stock was successfully pushed.");
            content.push(product);
            this.displayLast();
            Alarm.checkStatus();
            return true;
        }
    }

    //Push au stock à la main (sans producteur donc bypass de la producer queue
    public Boolean push(int id, int value){
        if(content.size() >= maxSize){
            Logger.severe("Unable to push "+value+" (Id: "+id+"): the stock is full.");
            return false;
        } else {
            Logger.fine("Stock was successfully pushed.");
            Product product = new Product(id, content.size(), value);
            content.push(product);
            this.displayLast();
            Alarm.checkStatus();
            return true;
        }
    }

    //Pop du stock depuis un consumer ou à la main
    public Boolean pop(){
        if(content.size() <= 0){
            Logger.severe("Unable to pop: the stock is empty.");
            return false;
        } else if(!consumerQueue.isEmpty()) {
            Logger.fine("Stock was successfully poped from queue.");
            content.pop();
            this.displayLast();
            consumerQueue.removeFirst();
            Alarm.checkStatus();
            return false;
        } else {
            Logger.fine("Stock was successfully poped.");
            content.pop();
            this.displayLast();
            Alarm.checkStatus();
            return true;
        }
    }

    //Clear du stock à la main
    public Boolean clear(){
        if(content.isEmpty()){
            Logger.severe("Unable to clear: the stock is already empty.");
            return false;
        } else {
            Logger.fine("Stock was successfully cleared.");
            content.clear();
            Alarm.checkStatus();
            return true;
        }
    }

    private void displayLast(){
        if(this.content.size() > 0) {
            System.out.println("Product ID: "+this.content.get(this.content.size()-1).getId()+" with value: "+this.content.get(this.content.size()-1).getValue());
        } else {
            System.out.println("No product in Stock.");
        }
    }

    public void addToConsumerQueue(Consumer consumer) {
        consumerQueue.add(consumer);
    }

    public void addToProducerQueue(Producer producer) {
        producerQueue.add(producer);
    }

    public int getStatus(){
        return this.status;
    }

    public LinkedList<Consumer> getConsumerQueue() {
        return consumerQueue;
    }

    public LinkedList<Producer> getProducerQueue() {
        return producerQueue;
    }

    public Product getLastContent(){
        return content.lastElement();
    }

    public int getMaxSize(){
        return maxSize;
    }

    public Stack<Product> getContent() {
        return content;
    }

    public void setStatus(int status){
        this.status = status;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
}
