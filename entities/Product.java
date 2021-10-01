package com.stocked.entities;

public class Product {

    private final int id;
    private int position;
    private final int value;

    Product(int id, int position, int value){
        this.position = position;
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public int getPosition() {
        return position;
    }

    public int getValue() {
        return value;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
