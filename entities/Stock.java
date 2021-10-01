package com.stocked.entities;

import com.stocked.utils.Logger;

import java.util.ArrayList;
import java.util.Stack;

public class Stock {

    private static Stock instance;
    public static int maxSize = 25;
    private static final Stack<Product> content = new Stack<>();
    private static Boolean fullAlert = false;
    private static Boolean emptyAlert = true;
    private static final ArrayList<Consumer> consumerQueue = new ArrayList<>();
    private static final ArrayList<Producer> producerQueue = new ArrayList<>();

    public Stock(int maxSize){
        this.maxSize = maxSize;
        instance = this;
    }

    //Push au stock depuis un producteur
    public Boolean push(Product product){
        if(content.size() >= maxSize){
            Logger.severe("Unable to push "+product.getValue()+" (Id: "+product.getId()+")");
            return false;
        } else if(!producerQueue.isEmpty()) {
            Logger.fine("Stock was successfully pushed from queue.");
            content.push(producerQueue.get(producerQueue.size()-1).getPendingProduct());
            return false;
        } else {
            Logger.fine("Stock was successfully pushed.");
            content.push(product);
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
            System.out.println(content.size());
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
            consumerQueue.remove(consumerQueue.size()-1);
            return false;
        } else {
            Logger.fine("Stock was successfully poped.");
            content.pop();
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
            return true;
        }
    }

    public String getStatus(){
        if(fullAlert) return "Full or nearly full";
        if(emptyAlert) return "Empty or nearly empty";
        return "Everything is fine";
    }

    public void addToConsumerQueue(Consumer consumer) {
        consumerQueue.add(consumer);
    }

    public void addToProducerQueue(Producer producer) {
        producerQueue.add(producer);
    }

    public static ArrayList<Consumer> getConsumerQueue() {
        return consumerQueue;
    }

    public static ArrayList<Producer> getProducerQueue() {
        return producerQueue;
    }

    public static Stock getInstance() {
        return instance;
    }

    public Product getLastContent(){
        return content.lastElement();
    }
}
