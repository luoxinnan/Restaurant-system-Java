import java.io.*;
import java.util.*;

class Order{
    private int number;
    private String status; // order taken/cooked/packed
    private HashMap<String, Double> foods = new HashMap<>(); // foods the order contains
    private String type; // takeaway/dine in
    private boolean paid = false; 
    private double totalPrice;


    public Order(HashMap<String, Double> foods, String type){
        this.foods = foods;
        this.type = type;
        setOrderNumber();
        setTotalPrice(foods);
    }

    public void setOrderNumber(){

        Scanner scan = null;
        try{
            scan = new Scanner(new File("orderNumber.txt"));
        } catch(FileNotFoundException e){
            System.err.println(e);
            System.exit(1);
        }

        int currentNumber = scan.nextInt();
        number = currentNumber;


        PrintWriter f = null;
        try{
            f = new PrintWriter("orderNumber.txt");
        }catch(FileNotFoundException e){
            System.err.println(e);
            System.exit(1);
        }

        f.print(currentNumber+1);
        f.close();

    }

    public void setTotalPrice(HashMap<String, Double> foods){
        for(Double price: foods.values()){
            totalPrice += price;
        }
    }


    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HashMap<String,Double> getFoods() {
        return this.foods;
    }

    public void setFoods(HashMap<String,Double> foods) {
        this.foods = foods;
    }

    public boolean paid() {
        return this.paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }

    public int getNumber() {
        return this.number;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isPaid() {
        return this.paid;
    }



}