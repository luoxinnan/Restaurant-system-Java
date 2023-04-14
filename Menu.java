import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Menu {
    HashMap<String, HashMap<String, Double>> menu = new LinkedHashMap<>(); // in a format of: {category: {dish name: price}}
    HashMap<String, Double> chickens = new LinkedHashMap<>();
    HashMap<String, Double> snacks = new LinkedHashMap<>();
    HashMap<String, Double> sauces = new LinkedHashMap<>();
    HashMap<String, Double> softDrinks = new LinkedHashMap<>();

    public Menu(String fileName){
        readFile(fileName);
    }

    // read file and generate menu hashmap in instansvariable
    public void readFile(String fileName){
        Scanner scan = null;
        try{
            scan = new Scanner(new File(fileName));
        } catch(FileNotFoundException e){
            System.out.println(e);
            System.exit(1);
        }


        String category = "";
        String line = scan.nextLine();
        int number = 0;
        while(scan.hasNextLine()){
            if(line.contains("#")){
                category = line.substring(1);
                HashMap<String, Double> map = new LinkedHashMap<>(); // dish name, price
                while(scan.hasNextLine()){
                    line = scan.nextLine();
                    if(line.contains("#")) break;
                    String[] bits = line.split(",");
                    String dish = number +  " " + bits[0];
                    number ++;
                    Double price = Double.parseDouble(bits[1]);
                    map.put(dish, price);
                }
                if(category.equals("Chicken")) chickens= map;
                else if(category.equals("Snacks")) snacks = map;
                else if(category.equals("Sauce")) sauces = map;
                else if(category.equals("Soft drink")) softDrinks = map;
                menu.put(category, map);
            } 
        }
    }

    @Override
    public String toString(){
        String str = "\n--------------------------Menu-----------------------";
        for(HashMap.Entry<String, HashMap<String, Double>> entry: menu.entrySet()){
            String category = entry.getKey();
            str = str + "\n" + category + ": ";
            HashMap<String, Double> childMap = entry.getValue();
            for(HashMap.Entry<String, Double> entry2: childMap.entrySet()){
                str = str +  "\n" + entry2.getKey() + ": " + entry2.getValue() + "kr";
            }
            str += "\n";
        }
        return str;
    }
}
