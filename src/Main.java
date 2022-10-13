import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Main {
    protected static Scanner scanner = new Scanner(System.in);
    protected static String[] products = new String[]{"Молоко", "Хлеб", "Хлопья", "Вода", "Чипсы"};
    protected static long[] prices = new long[]{80, 21, 120, 30, 200};
    protected static long[] basketNum = new long[products.length];
    protected static boolean[] trueOrFalse = new boolean[products.length];

    public static void main(String[] args) throws IOException, ParseException {
        File textFile = new File("basket.json");
        Basket basket = new Basket(products, prices, basketNum, trueOrFalse);
        if (textFile.exists()) {
            basket = Basket.loadJSON();
            System.out.println("Была восстановлена старая корзина");
            basket.getProducts();
            menu(basket);
        } else {
            basket.getProducts();
            menu(basket);
        }
    }

    public static void menu(Basket basket) throws IOException {
        while (true) {
            System.out.println("Введите basket для просмотра корзины или end для выхода из программы.");
            System.out.println("Введите номер товара и его количество:");
            String input = scanner.nextLine();
            if (input.equals("end")) {
                break;
            } else if (input.equals("basket")) {
                basket.printCart();
            } else {
                String[] parts = input.split(" ");
                int product = Integer.parseInt(parts[0]);
                int sum = Integer.parseInt(parts[1]);
                basket.addToCart(product, sum);

                basket.saveJSON();
                ClientLog.log(product, sum);
            }
        }
    }


}
