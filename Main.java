package com.stocked;

import com.stocked.entities.Stock;

public class Main {

    private static Stock stock;

    public static void main(String[] args) {
        init();
        Boolean stop = false;

        while (!stop){
            //TODO boucle (lecture clavier)
        }
    }

    public static void init(){
        stock = new Stock();
    }
}
