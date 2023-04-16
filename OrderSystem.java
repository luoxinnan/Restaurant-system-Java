import java.io.*;
import java.util.*;
import java.nio.file.*;

public class OrderSystem {
    private ArrayList<Order> orders = new ArrayList<>();

    public OrderSystem(){
    }

    // add order to the list and append to order log
    public void addOrder(Order order){
        orders.add(order);
        addToOrderLog(order);
    }

    // return the list of orders
    public ArrayList<Order> getOrders(){
        return orders;
    }

    public void addToOrderLog(Order order){
        // write a line of information
        String foods = "";
        for(String foodName: order.getFoods().keySet()){
            foods = foods + foodName + " ";
        }
        String line = order.getNumber() + "," + order.getType() + "," + order.getTotalPrice() + "," + foods + "\n";

        // append to orderLog
        try {
            Files.write(Paths.get("doc/orderLog.csv"), line.getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            System.err.println(e);
        }

    }
}
