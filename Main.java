package com.stocked;

import com.stocked.entities.Product;
import com.stocked.entities.alarm.AlarmS1;
import com.stocked.entities.alarm.AlarmS2;
import com.stocked.entities.observers.Consumer;
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
        StockReader stockReader = App.getInstance().getStockReader();

        System.out.println("Enter a maximum stock size: \n");
        Scanner reader = new Scanner(System.in);
        app.init(reader.nextInt());

        Boolean stop = false;

        //To always add producer/consumer/products at the beginning, might not be useful.
        Producer producer1 = new Producer( "Pat");
        Product prod = new Product(25, producer1);
        producer1.produce(prod);


        while (!stop) {
            String input = reader.nextLine();
            String parameter;
            try {
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
                        Boolean popped = false;
                        String nameC;

                        if(stock.getConsumers().isEmpty()){
                            popped = true;
                            System.out.println("No consumers, only admin can pop.");
                            if (stock.pop()) {
                                System.out.println("Stock popped successfully.");
                                Logger.fine("Product popped by admin.");
                            } else {
                                System.out.println("Stock pop failed.");
                                Logger.severe("Admin is not strong enough to pop from empty stock.");
                            }
                        } else {
                            for (Consumer consumer : stock.getConsumers()) {
                                System.out.println("Available consumer to pop: "+ consumer.getName());
                            }
                            System.out.println("Enter a consumer name or admin: ");
                            nameC = reader.nextLine();
                            if(nameC.equals("admin")){
                                popped = true;
                                if (stock.pop()) {
                                    System.out.println("Stock popped successfully.");
                                    Logger.fine("Product popped by admin.");
                                } else {
                                    System.out.println("Stock pop failed.");
                                    Logger.severe("Admin is not strong enough to pop from empty stock.");
                                }
                            } else {
                                for (Consumer consumer : stock.getConsumers()) {
                                    if(consumer.getName().equals(nameC)){
                                        popped = true;
                                        if (consumer.consume()) {
                                            System.out.println("Stock popped successfully.");
                                            Logger.fine("Product popped by "+consumer.getName()+".");
                                        } else {
                                            System.out.println("Stock pop failed.");
                                            Logger.severe(consumer.getName()+" was unable to pop from stock.");
                                        }
                                    } else {
                                        System.out.println("Invalid name selected.");
                                    }
                                }
                            }
                        }
                        if(!popped){
                            System.out.println("Error in push, please try again.");
                        }
                        break;
                    case "push":
                        System.out.println("Enter a value for your product: ");
                        int value = reader.nextInt();
                        reader.nextLine();  //Else the next nextLine will be skipped
                        Product produced = new Product(value);
                        Boolean pushed = false;
                        String nameP;

                        if(stock.getProducers().isEmpty()){
                            pushed = true;
                            System.out.println("No producers, only admin can push.");
                            if (stock.push(produced)) {
                                System.out.println("Stock pushed successfully.");
                                Logger.fine("Product successfully added to stock by admin.");
                            } else {
                                System.out.println("Stock push failed.");
                                Logger.severe("Admin is not strong enough to bypass maximum size of stock.");
                            }
                        } else {
                            for (Producer producer : stock.getProducers()) {
                                System.out.println("Available producer to push: "+ producer.getName());
                            }
                            System.out.println("Enter a producer name or admin: ");
                            nameP = reader.nextLine();
                            System.out.println("Name : "+nameP);
                            if(nameP.equals("admin")){
                                pushed = true;
                                if (stock.push(produced)) {
                                    System.out.println("Stock pushed successfully.");
                                    Logger.fine("Product successfully added to stock by admin.");
                                } else {
                                    System.out.println("Stock push failed.");
                                    Logger.severe("Admin is not strong enough to bypass maximum size of stock.");
                                }
                            } else {
                                for (Producer producer : stock.getProducers()) {
                                    if(producer.getName().equals(nameP)){
                                        pushed = true;
                                        if (producer.produce(produced)) {
                                            System.out.println("Stock pushed successfully.");
                                            Logger.fine("Product produced by "+producer.getName()+".");
                                        } else {
                                            System.out.println("Stock push failed.");
                                            Logger.severe(producer.getName()+" was unable to produce.");
                                        }
                                    }
                                }
                            }
                        }
                        if(!pushed){
                            System.out.println("Error in push, please try again.");
                        }
                        break;
                    case "display":
                        System.out.println("Stock content: \n");
                        stockReader.display();
                        System.out.println("\n Producers: ");
                        if(stock.getProducers().isEmpty()){
                            System.out.println("No producers.");
                        } else {
                            for (Producer producer : stock.getProducers()) {
                                System.out.println("Available producer: " + producer.getName());
                            }
                        }
                        System.out.println("\n Consumers: ");
                        if(stock.getConsumers().isEmpty()){
                            System.out.println("No consumers.");
                        } else {
                            for (Consumer consumer : stock.getConsumers()) {
                                System.out.println("Available consumer: " + consumer.getName());
                            }
                        }
                        break;
                    case "add":
                        System.out.println("Select between producer or consumer, any other command to cancel: ");
                        parameter = reader.nextLine();
                        switch (parameter){
                            case "producer":
                                System.out.println("Enter producer name: ");
                                parameter = reader.nextLine();
                                Producer prodAdd = new Producer(parameter);
                                Logger.fine("Producer: "+prodAdd.getId()+" added to producers with name: "+prodAdd.getName());
                                System.out.println("Producer added.");
                                break;
                            case "consumer":
                                System.out.println("Enter consumer name: ");
                                parameter = reader.nextLine();
                                Consumer consAdd = new Consumer(parameter);
                                Logger.fine("Consumer: "+consAdd.getId()+" added to consumers with name: "+consAdd.getName());
                                System.out.println("Consumer added.");
                                break;
                            default:
                                System.out.println("Error in input or command canceled.\n");
                                break;
                        }
                        break;
                    case "remove":
                        System.out.println("Select between producer or consumer, any other command to cancel: ");
                        parameter = reader.nextLine();
                        boolean contained = false;
                        switch (parameter) {
                            case "producer":
                                System.out.println("Enter the producer name: ");
                                parameter = reader.nextLine();
                                for(Producer producer : stock.getProducers()){
                                    if(producer.getName().equals(parameter)) {
                                        stock.removeObserver(producer);
                                        Logger.fine("Producer successfully removed from producers.");
                                        System.out.println("Producer removed.");
                                        contained = true;
                                    }
                                }
                                if(!contained){
                                    Logger.warning("Such producer not found in stock producers.");
                                }
                                break;
                            case "consumer":
                                System.out.println("Enter the consumer name: ");
                                parameter = reader.nextLine();
                                for(Consumer consumer : stock.getConsumers()){
                                    if(consumer.getName().equals(parameter)) {
                                        stock.removeObserver(consumer);
                                        Logger.fine("Consumer successfully removed from consumers.");
                                        System.out.println("Consumer removed.");
                                        contained = true;
                                    }
                                }
                                if(!contained){
                                    Logger.warning("Such consumer not found in stock consumers.");
                                }
                                break;
                            default:
                                System.out.println("Error in input or command canceled.\n");
                                break;
                        }
                        break;
                    case "settings":
                        System.out.println("Select between S1 or S2 (Case sensitive !) to switch alarm mode, help to display alarm modes, modify to modify their parameters: ");
                        parameter = reader.nextLine();
                        switch (parameter){
                            case "S1":
                                stock.getAlarm().setStrategy(new AlarmS1());
                                System.out.println("Alarm strategy set to S1.");
                                break;
                            case "S2":
                                stock.getAlarm().setStrategy(new AlarmS2());
                                System.out.println("Alarm strategy set to S2.");
                                break;
                            case "modify":
                                if(stock.getAlarm().getCurrentStrategy().getClass() == AlarmS2.class){
                                    System.out.println("Enter the threshold for max amount of products in X minutes (you will select X after): ");
                                    int threshold = reader.nextInt();
                                    System.out.println("Enter X the amount of minutes you want to work on: ");
                                    int timer = reader.nextInt();
                                    ((AlarmS2)stock.getAlarm().getCurrentStrategy()).modify(threshold, timer);
                                    System.out.println("Alarm strategy S2 updated.");
                                } else if(stock.getAlarm().getCurrentStrategy().getClass() == AlarmS1.class){
                                    System.out.println("Enter the threshold for MAX ALARM (Example: 5-> on a 100 slots-stock, ALARM_FULL will go on at 80+; ALARM_EMPTY will go on at 20- (80/4): ");
                                    int threshold = reader.nextInt();
                                    ((AlarmS1)stock.getAlarm().getCurrentStrategy()).modify(threshold);
                                    System.out.println("Alarm strategy S1 updated.");
                                } else {
                                    Logger.severe("COMMAND ERROR: Unknown alarm mode selected.");
                                }
                                break;
                            case "help":
                                System.out.println( "S1 mode is a threshold alarm mode with variable threshold.\n"+
                                                    "S2 mode is a filling/time calculator that will calculate the number of products (threshold) produced in the last X minutes (timer).");
                                break;
                            default:
                                System.out.println("Error in input or command canceled.\n");
                                break;
                        }
                        break;
                    case "help":
                        System.out.println( "stop - Will stop the current loop. \n"+
                                            "push - You will have to select a producer or admin, and to enter a value to push product. Will add such product to stock. \n"+
                                            "pop - You will have to select a consumer or admin. Will pop a product from stock. \n"+
                                            "clear - Will clear the stock content.\n"+
                                            "display - Will display the current stock content.\n"+
                                            "add - You can add a producer by typing producer, or add a consumer by typing consumer, any other command to cancel.\n"+
                                            "remove - You can remove a producer by typing producer, or remove a consumer by typing consumer, any other command to cancel.\n"+
                                            "settings - You can select the alarm mode, type S1 to select alarm 1, S2 to select alarm 2, help to explain alarm modes, and modify to modify the current alarm mode parameters.\n");
                        break;
                    default:
                        System.out.println("Please use \"help\" to get all available commands.\n");
                        break;
                }
            } catch (NullPointerException exception){
                Logger.severe("COMMAND ERROR: String is null. End of simulation.");
                System.out.println("Error in input, check logs for more details.\n");
                stop = true;
            }
        }
    }

}
