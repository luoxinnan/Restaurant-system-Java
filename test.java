import java.util.HashMap;

public class test {
    public static void main(String[] args) {
        Menu menu = new Menu("menu.csv");
        // System.out.println(menu);
        HashMap<String, Double> foods = new HashMap<>();
        foods.put("Chicken wings", 179.0);
        Order order = new Order(foods, "Take away");

        HashMap<String, Double> foods2 = new HashMap<>();
        foods.put("Chicken legs", 189.0);
        Order order2 = new Order(foods2, "Dine in");

        OrderSystem orderSystem = new OrderSystem();
        orderSystem.addOrder(order);
        orderSystem.addOrder(order2);
    }
}
