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
        JSONObject object = new JSONObject();
        JSONArray listProd = new JSONArray();
        for (String product :
                products) {
            listProd.add(product);
        }
        object.put("products", listProd);

        JSONArray listPrices = new JSONArray();
        for (long price :
                prices) {
            listPrices.add(price);
        }
        object.put("prices", listPrices);

        JSONArray listBasket = new JSONArray();
        for (long bask :
                basket) {
            listBasket.add(bask);
        }
        object.put("basket", listBasket);

        JSONArray listIsFilled = new JSONArray();
        for (boolean fill :
                isFilled) {
            listIsFilled.add(fill);
        }
        object.put("trueOrFalse", listIsFilled);

        try (FileWriter file = new FileWriter(String.valueOf(forSave))) {
            file.write(object.toJSONString());
        }
    }

    public static Basket loadJSON(String nameFile) throws ParseException, IOException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(nameFile));
        JSONObject jsonObject = (JSONObject) obj;

        JSONArray products = (JSONArray) jsonObject.get("products");
        ArrayList<String> productsArr = (ArrayList<String>) products;
        String[] productsStr = new String[productsArr.toArray().length];
        for (int i = 0; i < productsStr.length; i++) {
            productsStr[i] = productsArr.get(i);
        }

        JSONArray prices = (JSONArray) jsonObject.get("prices");
        ArrayList<Long> pricesArr = (ArrayList<Long>) prices;
        long[] pricesInt = new long[pricesArr.toArray().length];
        for (int i = 0; i < pricesInt.length; i++) {
            pricesInt[i] = pricesArr.get(i);
        }

        JSONArray basket = (JSONArray) jsonObject.get("basket");
        ArrayList<Long> basketArr = (ArrayList<Long>) basket;
        long[] basketInt = new long[basketArr.toArray().length];
        for (int i = 0; i < basketInt.length; i++) {
            basketInt[i] = basketArr.get(i);
        }

        JSONArray isFilled = (JSONArray) jsonObject.get("trueOrFalse");
        ArrayList<Boolean> isFilledArr = (ArrayList<Boolean>) isFilled;
        boolean[] isFilledBool = new boolean[isFilledArr.toArray().length];
        for (int i = 0; i < isFilledBool.length; i++) {
            isFilledBool[i] = isFilledArr.get(i);
        }
        return new Basket(productsStr, pricesInt, basketInt, isFilledBool);

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
