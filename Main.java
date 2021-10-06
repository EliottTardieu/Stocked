package com.stocked;

import com.stocked.entities.Stock;
import com.stocked.entities.StockReader;
import com.stocked.utils.Logger;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        App app = new App();
        Stock stock = app.getStock();
        Boolean stop = false;
        StockReader stockReader = App.getInstance().getStockReader();



        System.out.println("Enter a maximum stock size : ");
        Scanner reader = new Scanner(System.in);
        app.init(reader.nextInt());

        while (!stop) {
            String input = reader.nextLine();
            if (input.equals("stop")) {
                stop = true;
            } else if (input.equals("clear")) {
                if (stock.clear()) {
                    Logger.fine("Stock cleared by admin.");
                } else {
                    Logger.fine("Admin is not strong enough to clear empty stock.");
                }
            } else if (input.equals("push")) {
                System.out.println("Entrez un ID: ");
                int id = reader.nextInt();
                System.out.println("Entrez une valeur: ");
                int value = reader.nextInt();
                if (stock.push(id, value)) {
                    Logger.fine("Product successfully added to stock by admin.");
                } else {
                    Logger.severe("Admin is not strong enough to bypass maximum size of stock.");
                }
            } else if (input.equals("pop")) {
                if (stock.pop()) {
                    Logger.fine("Product popped by admin.");
                } else {
                    Logger.severe("Admin is not strong enough to pop from empty stock.");
                }
            } else if(input.equals("display")) {
                stockReader.display();
            } else {
                System.out.println("Please use \"stop\" to stop; \"push\" to push; \"pop\" to pop; \"clear\" to clear the stock.");
            }
        }
    }

}
