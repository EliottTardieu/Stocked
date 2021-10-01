package com.stocked.entities;

import java.util.ArrayList;
import java.util.Stack;

public class Stock {

    private static Stock instance;
    private static int maxSize;
    private static Stack<Product> content = new Stack<>();
    private static Boolean fullAlert = false;
    private static Boolean emptyAlert = true;
    private static ArrayList<Consumer> consumerQueue = new ArrayList<>();
    private static ArrayList<Producer> producerQueue = new ArrayList<>();

    public Stock(){
        instance = this;
    }

    //TODO Logs + checks
    public void push(Product product){
        content.push(product);

    }

    public void push(int id, int position, int value){
        Product product = new Product(id, position, value);
        content.push(product);
    }

    public void pop(){
        content.pop();
    }

    private void clear(){
        content.clear();
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
