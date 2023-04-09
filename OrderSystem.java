import java.io.*;
import java.util.*;
import java.nio.file.*;

public class OrderSystem {
    private ArrayList<Order> orders = new ArrayList<>();

    public OrderSystem(){
    }

    public void addOrder(Order order){
        orders.add(order);
        addToOrderLog(order);
    }

    public ArrayList<Order> getOrders(){
        return orders;
    }

    public void addToOrderLog(Order order){
        String foods = "";
        for(String foodName: order.getFoods().keySet()){
            foods = foods + foodName + " ";
        }
        String line = order.getNumber() + "," + order.getType() + "," + order.getTotalPrice() + "," + foods + "\n";
        System.out.println(line);

        try {
            Files.write(Paths.get("orderLog.csv"), line.getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            System.err.println(e);
        }


    }
}
