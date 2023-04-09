import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Menu {
    HashMap<String, HashMap<String, Double>> menu = new HashMap<>(); // in a format of: {category: {dish name: price}}

    public Menu(String fileName){
        readFile(fileName);
    }

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
        while(scan.hasNextLine()){
            if(line.contains("#")){
                category = line.substring(1);
                HashMap<String, Double> map = new HashMap<>(); // dish name, price
                while(scan.hasNextLine()){
                    line = scan.nextLine();
                    if(line.contains("#")) break;
                    String[] bits = line.split(",");
                    String dish = bits[0];
                    Double price = Double.parseDouble(bits[1]);
                    map.put(dish, price);
                }
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
