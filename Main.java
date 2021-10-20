package com.stocked;

import com.stocked.entities.Product;
import com.stocked.entities.observers.Producer;
import com.stocked.entities.stock.StockController;
import com.stocked.entities.stock.StockReader;
import com.stocked.utils.Logger;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        App app = new App();
        StockController stock = app.getStock();
        boolean stop = false;
        StockReader stockReader = App.getInstance().getStockReader();
        System.out.println("Enter a maximum stock size : ");
        Scanner reader = new Scanner(System.in);
        app.init(reader.nextInt());


        Producer producer = new Producer(1, "Pat");
        stock.addToObservers(producer);
        Product prod = new Product(25, producer);
        producer.produce(prod);


        while (!stop) {
            String input = reader.nextLine();
            try {
                System.out.println("Type your commands here. \"help\" to get all available commands.");
                switch (input) {
                    case "stop":
                        stop = true;
                        break;
                    case "clear":
                        if (stock.clear()) {
                            Logger.fine("Stock cleared by admin.");
                        } else {
                            Logger.fine("Admin is not strong enough to clear empty stock.");
                        }
                        break;
                    case "pop":
                        if (stock.pop()) {
                            Logger.fine("Product popped by admin.");
                        } else {
                            Logger.severe("Admin is not strong enough to pop from empty stock.");
                        }
                        break;
                    case "push":
                        System.out.println("Enter a value: ");
                        int value = reader.nextInt();
                        if (stock.push(value)) {
                            Logger.fine("Product successfully added to stock by admin.");
                        } else {
                            Logger.severe("Admin is not strong enough to bypass maximum size of stock.");
                        }
                        break;
                    case "display":
                        stockReader.display();
                        break;
                    case "add":

                        break;
                    case "settings":

                        break;
                    case "help":

                        break;
                    default:
                        System.out.println("Please use \"help\" to get all available commands.");
                        break;
                }
            } catch (NullPointerException exception){
                Logger.severe("COMMAND ERROR: String is null. End of simulation.");
                System.out.println("Error in input, check logs for more details.");
                stop = true;
            }
            if (input.equals("stop")) {
                stop = true;
            } else if (input.equals("clear")) {

            } else if (input.equals("push")) {

            } else if (input.equals("pop")) {

            } else if(input.equals("display")) {
            } else {
            }
        }
    }

}
