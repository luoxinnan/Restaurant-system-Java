public class test {
    public static void main(String[] args) {
        Menu menu = new Menu("menu.csv");
        System.out.println(menu);
        System.out.println(menu.chickens.toString());
    }
}
