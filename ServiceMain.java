import java.util.*;

public class ServiceMain {
    static boolean exit = false;
    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        
        Menu menu = new Menu("menu.csv");
        System.out.println("\nWelcome to 175C Korean Fried Chicken!");
        System.out.println("\nAre you intending to dine in or opt for takeout?");

        String orderType = scan.nextLine();
        HashMap<String, Double> foods = takeOrder(menu);
        Order order = new Order(foods, orderType);
        order.setPaid(pay());
        System.out.println(order);


    
    
    
    
    
    
    
    
    }

    public static boolean pay(){
        System.out.println("Are you paying now? y for yes, other input for no");
        if(scan.nextLine().toLowerCase().equals("y")) return true;
        return false;

    }

    public static HashMap<String, Double> takeOrder(Menu menu){

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
            if(confirm.equals("n")) ;
            else if(confirm.equals("e")) System.exit(0);
            else ;
        }
    }
}
