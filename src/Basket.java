import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;

public class Basket {
    protected String[] products;
    protected long[] prices;
    protected long[] basket;
    protected boolean[] isFilled;

    public Basket(String[] products, long[] prices, long[] basket, boolean[] isFilled) {
        this.products = products;
        this.prices = prices;
        this.basket = basket;
        this.isFilled = isFilled;
    }

    public void addToCart(int productNum, int amount) {
        basket[productNum - 1] += amount;
        isFilled[productNum - 1] = true;
    }

    public void printCart() {
        System.out.println("Вы положили в корзину: ");
        int sum = 0;
        for (int i = 0; i < products.length; i++) {
            if (isFilled[i]) {
                double price = prices[i] * basket[i];
                System.out.println(products[i] + ", цена: " + prices[i] + " за штуку. " + basket[i] + "шт. " + "На сумму: " + price);
                sum += price;
            }
        }
        System.out.println("Вышло на сумму: " + sum);
    }

    public void saveTxt(File textFile) throws IOException {
        try (
                PrintWriter out = new PrintWriter(textFile)
        ) {
            for (String p : products) {
                out.print(p + " ");
            }
            out.println();
            for (long pr : prices) {
                out.print(pr + " ");
            }
            out.println();
            for (long b : basket) {
                out.print(b + " ");
            }
            out.println();
            for (boolean fill : isFilled) {
                out.print(fill + " ");
            }
        }
    }

    public void saveJSON(File forSave) throws IOException {
        Gson gson = new Gson();
        try (FileWriter file = new FileWriter(String.valueOf(forSave))) {
            file.write(gson.toJson(this));
        }
    }

    public static Basket loadJSON(String nameFile) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(nameFile));
        return gson.fromJson(reader, Basket.class);
    }

    public static Basket loadFromTxtFile(File textFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {
            String[] products = reader.readLine().split(" ");
            String[] pricesStr = reader.readLine().trim().split(" ");
            long[] prices = new long[pricesStr.length];
            for (int i = 0; i < prices.length; i++) {
                prices[i] = Integer.parseInt(pricesStr[i]);
            }
            String[] basketStr = reader.readLine().trim().split(" ");
            long[] basket = new long[basketStr.length];
            for (int i = 0; i < basket.length; i++) {
                basket[i] = Integer.parseInt(basketStr[i]);
            }
            String[] isFilledStr = reader.readLine().split(" ");
            boolean[] isFilled = new boolean[isFilledStr.length];
            for (int i = 0; i < isFilled.length; i++) {
                isFilled[i] = Boolean.parseBoolean(isFilledStr[i]);
            }
            return new Basket(products, prices, basket, isFilled);
        }
    }

    public void getProducts() {
        System.out.println("Список продуктов: ");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i]);
        }
        System.out.println();
    }
}
