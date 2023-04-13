import java.util.*;

public class ServiceMain {
    static boolean exit = false;
    static Scanner scan = new Scanner(System.in);
    static OrderSystem orderSystem = new OrderSystem();
    static RestaurantMonitor monitor = new RestaurantMonitor(orderSystem);
    static Menu menu = new Menu("menu.csv");
    public static void main(String[] args) {
        ArrayList<Order> orders = new ArrayList<>();

        System.out.println("\nWelcome to 175C Korean Fried Chicken!");

        while(true){
            System.out.println("\nOrder food press 1, pick up food press 2, administration press 3, exit press 4");
            String prompt = scan.nextLine();
            switch (prompt){
                case "1": startOrdering(orders); break;
                case "2": pickUp(orders); break;
                case "3": System.out.println("Administration"); break;
                case "4": System.exit(0);
                default: ;
            }
        }
    
    }

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
        System.out.println("\nThere is no mached number");
        return;
    }

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

    public static Order takeOrder(){
        System.out.println("\nAre you intending to dine in or opt for takeout?");
        String orderType = scan.nextLine();
        HashMap<String, Double> foods = chooseFood();
        if(foods == null) return null;
        Order order = new Order(foods, orderType);
        System.out.println("\n--------This order number is " + order.getNumber() + "--------");
        order.setPaid(true);
        return order;

    }

    // public static boolean pay(){
    //     System.out.println("Are you paying now? y for yes, other input for no");
    //     if(scan.nextLine().toLowerCase().equals("y")) return true;
    //     return false;
    // }

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
        System.out.println("\nPress y to confirm, press other key to restart");
        String confirm = scan.nextLine().toLowerCase();
        if(confirm.equals("y")) return foods;
        else return null;

    }
}
