import java.io.*;
import java.util.*;

public class ServiceMain {
    static final String menuFile = "doc/menu.csv";
    static final String orderLog = "doc/orderLog.csv";

    static Scanner scan = new Scanner(System.in);
    static OrderSystem orderSystem = new OrderSystem();
    static RestaurantMonitor monitor = new RestaurantMonitor(orderSystem);
    static Menu menu = new Menu(menuFile);
    public static void main(String[] args) {
        ArrayList<Order> orders = new ArrayList<>();

        System.out.println("\nWelcome to 175C Korean Fried Chicken!");

        while(true){
            System.out.println("\nOrder food press 1, pick up food press 2, administration press 3, exit press 4");
            String prompt = scan.nextLine();
            switch (prompt){
                case "1": startOrdering(orders); break;
                case "2": pickUp(orders); break;
                case "3": checkInfo(); break;
                case "4": System.exit(0);
                default: ;
            }
        }
    
    }

    // Print out all the information in the entire order log
    public static void checkInfo(){
        System.out.println("Informations of orders: ");

        Scanner scanner = null;
        try{
            scanner = new Scanner(new File(orderLog));
        } catch(FileNotFoundException e){
            System.out.println(e);
            System.exit(1);
        }

        while(scanner.hasNextLine()){
            System.out.println(scanner.nextLine());
        }
        scanner.close();
    }

    // pick up order by order number
    public static void pickUp(ArrayList<Order> orders){
        System.out.println("\nPlease enter you order number: ");
        String orderNumber = scan.nextLine();
        for(Order order: orders){
            if(order.getStatus().equals("order packed") && order.getNumber()== Integer.parseInt(orderNumber)){
                order.setStatus("delivered");
                System.out.println("\nHere is your food of order " + orderNumber + ". Enjoy!");
                return;
            }

        }
        System.out.println("\nThere is no matched number");
        return;
    }


    // Take orders, add orders into a list, then send inn all these orders through SendInOrder threads
    // Packing orders by deliverOrder thread
    public static void startOrdering(ArrayList<Order> orders){

        while(true){
            Order order = takeOrder();
            if(order == null) return;
            orders.add(order);
            System.out.println("Do you want to take one more order? y for yes, other keys for no: ");
            if(!scan.nextLine().toLowerCase().equals("y")) break;
        }
    
        System.out.println("We are preparing your food, please be pasient...");
        DeliverOrder deliver = new DeliverOrder(monitor);
        Thread deliverThread = new Thread(deliver);
        deliverThread.start();
        Thread[] sengInnthreads = new Thread[orders.size()];
        for(int i=0; i < orders.size(); i++){
            SendInOrder sendIn = new SendInOrder( orders.get(i), monitor);
            Thread thread = new Thread(sendIn);
            sengInnthreads[i] = thread;
            thread.start();
        }

        try{
            for(int i=0; i<sengInnthreads.length; i++){
                sengInnthreads[i].join();
            }
            deliverThread.join();
        }catch(Exception e){
            System.err.println(e);
            System.exit(1);
        }

        System.out.println("All orders finished paking");


    }

    // Make order, print out the order number and return the order object
    public static Order takeOrder(){
        System.out.println("\nTo dine in press 1, for takeout press 2: ");
        String orderType = "";
        String answer = scan.nextLine();
        if(answer.equals("1")) orderType = "Dine in";
        else if(answer.equals("2")) orderType = "Takeout";
        else takeOrder();
        HashMap<String, Double> foods = chooseFood();
        if(foods == null) return null;
        Order order = new Order(foods, orderType);
        System.out.println("\n--------This order number is " + order.getNumber() + "--------");
        order.setPaid(true);
        return order;

    }


    // Select the food from the menu ny entering the corresponding number. Then return the hash map of the selected food(in the format of food name: price).
    public static HashMap<String, Double> chooseFood(){

        System.out.println("\nHere is the menu: ");
        System.out.println(menu);
        System.out.println("\n(Type numbers with space then type ' c' and hit enter) Which dishes du you want to order?");
        ArrayList<String> dishNumbers = new ArrayList<>();
        while(scan.hasNextInt()){
            String num = Integer.toString(scan.nextInt());
            dishNumbers.add(num);
        }
        scan.nextLine();

        HashMap<String, Double> foods = new LinkedHashMap<>();
        for(String dishNumber: dishNumbers){
            for(HashMap<String, Double> dishes: menu.menu.values()){
                for(String dishName: dishes.keySet()){
                    if(dishName.split(" ")[0].equals(dishNumber)){
                        foods.put(dishName, dishes.get(dishName));
                    }
                }
            }
        }

        System.out.println("\nHere is food you have orderd.");
        System.out.println(foods.toString());
        System.out.println("\nTo confirm press y, press restart press other keys");
        String confirm = scan.nextLine().toLowerCase();
        if(confirm.equals("y")) return foods;
        else return null;

    }
}
