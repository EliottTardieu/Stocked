package com.stocked.entities.stock;

import com.stocked.App;
import com.stocked.entities.observers.Consumer;
import com.stocked.entities.observers.Producer;
import com.stocked.entities.Product;
import com.stocked.entities.alarm.Alarm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class StockController implements Observable{

    private int maxSize = 25;
    private StockStatus status = StockStatus.STATUS_EMPTY;

    private final Stack<Product> content = new Stack<>();
    private Stack<Product> history = new Stack<>();
    private StockReader view;
    private Alarm alarm;

    private final ArrayList<Object> observers = new ArrayList<>();
    private final LinkedList<Consumer> consumerQueue = new LinkedList<>();
    private final LinkedList<Producer> producerQueue = new LinkedList<>();


    public void init(){
        this.view = App.getInstance().getStockReader();
        this.alarm = new Alarm();
    }

    @Override
    public void update(StockStatus status) {
        this.status = status;
        this.dispatch();
    }

    @Override
    public void dispatch() {
        //Checks for Meth-1
        for (Object observer : observers) {
            if(observer.getClass() == Consumer.class) {
                if(((Consumer) observer).getKnownStatus() != this.status) ((Consumer)observer).update();
            } else if(observer.getClass() == Producer.class){
                if(((Producer) observer).getKnownStatus() != this.status) ((Producer)observer).update();
            }
        }
    }

    private void pushFromQueue(){
        producerQueue.getFirst().getPendingProduct().setPush();
        content.push(producerQueue.getFirst().getPendingProduct());
        history.push(producerQueue.getFirst().getPendingProduct());
        view.push(StockStatus.STATUS_QUEUED, producerQueue.getFirst().getPendingProduct());
        producerQueue.removeFirst();
        alarm.checkStatus();
    }

    private void popFromQueue(){
        Product prod = content.pop();
        prod.setPop();
        view.pop(StockStatus.STATUS_QUEUED);
        consumerQueue.removeFirst();
        alarm.checkStatus();
    }

    //Push au stock depuis un producteur
    public Boolean push(Product product){
        Boolean toReturn = false;
        if(content.size() >= maxSize){
            view.push(StockStatus.STATUS_DENIED, product);
        } else {
            product.setPush();
            content.push(product);
            history.push(product);
            view.push(StockStatus.STATUS_ALLOWED, product);
            alarm.checkStatus();
            toReturn = true;
            if(!consumerQueue.isEmpty()) {
                this.popFromQueue();
            }
        }
        return toReturn;
    }

    //Push au stock à la main (sans producteur donc bypass de la producer queue)
    public Boolean push(int value){
        Boolean toReturn = false;
        Product TempProduct = new Product(value);
        if(content.size() >= maxSize){
            view.push(StockStatus.STATUS_DENIED, TempProduct);
        } else {
            TempProduct.setPush();
            content.push(TempProduct);
            history.push(TempProduct);
            view.push(StockStatus.STATUS_ALLOWED, TempProduct);
            alarm.checkStatus();
            toReturn = true;
            if(!consumerQueue.isEmpty()) {
                this.popFromQueue();
            }
        }
        return toReturn;
    }

    //Pop du stock depuis un consumer ou à la main
    public Boolean pop(){
        Boolean toReturn = false;
        if(content.size() <= 0){
            view.pop(StockStatus.STATUS_DENIED);
        } else {
            Product prod = content.pop();
            prod.setPop();
            view.pop(StockStatus.STATUS_ALLOWED);
            alarm.checkStatus();
            toReturn = true;
            if(!producerQueue.isEmpty()) {
                this.pushFromQueue();
            }
        }
        return toReturn;
    }

    //Clear du stock ainsi que des queues
    public Boolean clear(){
        if(content.isEmpty()){
            view.clear(StockStatus.STATUS_DENIED);
            return false;
        } else {
            for (Product prod : content) prod.setPop();
            content.clear();
            view.clear(StockStatus.STATUS_ALLOWED);
            this.consumerQueue.clear();
            this.producerQueue.clear();
            alarm.checkStatus();
            return true;
        }
    }

    @Override
    public void removeObserver(Object o){
        if(o.getClass() == Producer.class){
            observers.remove(o);
        } else if(o.getClass() == Consumer.class){
            observers.remove(o);
        }
    }

    @Override
    public void addToObservers(Object o) {
        observers.add(o);
    }

    public void addToConsumerQueue(Consumer consumer) {
        if(observers.contains(consumer))
        consumerQueue.add(consumer);
    }

    public void addToProducerQueue(Producer producer) {
        if(observers.contains(producer))
        producerQueue.add(producer);
    }

    public StockStatus getStatus(){
        return this.status;
    }

    public LinkedList<Consumer> getConsumerQueue() {
        return consumerQueue;
    }

    public LinkedList<Producer> getProducerQueue() {
        return producerQueue;
    }

    public int getMaxSize(){
        return maxSize;
    }

    public Stack<Product> getContent() {
        return content;
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public Stack<Product> getHistory() {
        return history;
    }

    public ArrayList<Consumer> getConsumers(){
        ArrayList<Consumer> toReturn = new ArrayList<>();
        for(Object o : this.observers){
            if(o.getClass() == Consumer.class) toReturn.add((Consumer) o);
        }
        return toReturn;
    }

    public ArrayList<Producer> getProducers(){
        ArrayList<Producer> toReturn = new ArrayList<>();
        for(Object o : this.observers){
            if(o.getClass() == Producer.class) toReturn.add((Producer) o);
        }
        return toReturn;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }


}
