package com.stocked.entities.stock;

import java.util.ArrayList;

public interface Observable {

    public final ArrayList<Object> observers = new ArrayList<>();

    public void update(StockStatus status);

    public void dispatch();

    public void addToObservers(Object o);

    public void removeObserver(Object o);

}
