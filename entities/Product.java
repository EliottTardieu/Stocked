package com.stocked.entities;

import com.stocked.entities.observers.Producer;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.UUID;

public class Product {

    private final UUID id;
    private final int value;
    public Producer[] producers;
    private ArrayList<Integer> timer = new ArrayList<>();  //Index 0 et 1: Heure et minute du Push; Index 2 et 3: Heure et minute du pop

    public Product(int value, Producer... producers){
        this.id = UUID.randomUUID();
        this.value = value;
        this.producers = producers;
    }

    public UUID getId() {
        return id;
    }

    public int getValue() {
        return value;
    }

    public ArrayList<Integer> getTimer() {
        return timer;
    }

    public void setPush() {
        this.timer.add(0, LocalTime.now().getHour());
        this.timer.add(1, LocalTime.now().getMinute());
    }

    public void setPop() {
        this.timer.add(2, LocalTime.now().getHour());
        this.timer.add(3, LocalTime.now().getMinute());
    }

}
