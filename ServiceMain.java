import java.util.*;

public class ServiceMain {
    static boolean exit = false;
    static Scanner scan = new Scanner(System.in);
    static OrderSystem orderSystem = new OrderSystem();
    static RestaurantMonitor monitor = new RestaurantMonitor(orderSystem);
    static Menu menu = new Menu("menu.csv");
    public static void main(String[] args) {
        

        System.out.println("\nWelcome to 175C Korean Fried Chicken!");

        while(true){
            System.out.println("\nOrder food press 1, pick up food press 2, administration press 3, exit press 4");
            String prompt = scan.nextLine();
            switch (prompt){
                case "1": startOrdering(); break;
                case "2": System.out.println("pick up"); break;
                case "3": System.out.println("Administration"); break;
                case "4": System.exit(0);
                default: ;
            }
        }
       

        





 




    
    
    
    
    
    
    
    
    }

    public static void startOrdering(){
        ArrayList<Order> orders = new ArrayList<>();

        while(true){
            orders.add(takeOrder());
            System.out.println("Do you want to take one more order? y for yes");
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
        Order order = new Order(foods, orderType);
        order.setPaid(pay());
        return order;

    }

    public static boolean pay(){
        System.out.println("Are you paying now? y for yes, other input for no");
        if(scan.nextLine().toLowerCase().equals("y")) return true;
        return false;

    }

    public static HashMap<String, Double> chooseFood(){

        System.out.println("\nHere is the menu: ");
        System.out.println(menu);
        System.out.println("Which dishes du you want to order? Enter c when you are finished");
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
        while(true){
            System.out.println("\n Here is food you have orderd.");
            System.out.println(foods.toString());
            System.out.println("\nPress y to confirm, press n to restart, press e to exit system");
            String confirm = scan.nextLine().toLowerCase();
            if(confirm.equals("y")) return foods;
            if(confirm.equals("n")) chooseFood();
            else if(confirm.equals("e")) System.exit(0);
            else ;
        }
    }
}
