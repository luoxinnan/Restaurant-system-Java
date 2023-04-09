import java.util.ArrayList;

class Order{
    private static int number = 0; // order number
    private String status; // order taken/made/packed/delivered
    private ArrayList<String> foods = new ArrayList<>(); // foods the order contains
    private String type; // takeaway/dine in
    private double bill;
    private boolean paid = false; 

    public Order(ArrayList<String> foods, String type){
        this.foods = foods;
        this.type = type;
    }

    public void addToBill(ArrayList<String> foods){
        
    }

    public void pay() {
        //Todo: add bill amount to total income
        // Todo: set paid to true
    }



    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<String> getFoods() {
        return this.foods;
    }

    public void setFoods(ArrayList<String> foods) {
        this.foods = foods;
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