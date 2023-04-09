import java.util.HashMap;

public class test {
    public static void main(String[] args) {
        Menu menu = new Menu("menu.csv");
        // System.out.println(menu);
        HashMap<String, Double> foods = new HashMap<>();
        foods.put("Chicken wings", 179.0);
        Order order = new Order(foods, "Take away");

        HashMap<String, Double> foods2 = new HashMap<>();
        foods2.put("Legs", 179.0);
        Order order2 = new Order(foods2, "Take away");

        OrderSystem orderSystem = new OrderSystem();
        orderSystem.addOrder(order2);
        orderSystem.addOrder(order);

    }
}
