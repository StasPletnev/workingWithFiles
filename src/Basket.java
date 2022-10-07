import java.io.*;

public class Basket {
    protected String[] products;
    protected int[] prices;
    protected int[] basket;
    protected boolean[] isFilled;

    public Basket(String[] products, int[] prices, int[] basket, boolean[] isFilled) {
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
            for (int pr : prices) {
                out.print(pr + " ");
            }
            out.println();
            for (int b : basket) {
                out.print(b + " ");
            }
            out.println();
            for (boolean fill : isFilled) {
                out.print(fill + " ");
            }
        }
    }

    public static Basket loadFromTxtFile(File textFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {
            String[] products = reader.readLine().split(" ");
            String[] pricesStr = reader.readLine().trim().split(" ");
            int[] prices = new int[pricesStr.length];
            for (int i = 0; i < prices.length; i++) {
                prices[i] = Integer.parseInt(pricesStr[i]);
            }
            String[] basketStr = reader.readLine().trim().split(" ");
            int[] basket = new int[basketStr.length];
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
