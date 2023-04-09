import java.io.PrintWriter;
import java.util.ArrayList;

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
        PrintWriter f = null;
        try{
            f = new PrintWriter("orderLog");
        }catch(Exception e){
            System.err.println("Failed adding order to log");
            System.exit(1);
        }

        String foods = "";
        for(String foodName: order.getFoods().keySet()){
            foods = foods + foodName + " ";
        }
        String line = order.getNumber() + "," + order.getType() + "," + order.getTotalPrice() + "," + foods;
        f.write(line);
    }
}
