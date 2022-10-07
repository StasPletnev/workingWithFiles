import java.io.*;
import java.util.Scanner;


public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        File binFile = new File("basket.bin");
        String[] products = new String[]{"Молоко", "Хлеб", "Хлопья", "Вода", "Чипсы"};
        Basket basket = new Basket(products, new int[]{80, 21, 120, 30, 200}, new int[products.length], new boolean[products.length]);
        if (binFile.exists()) {
            basket = Basket.loadFromBinFile(binFile);
            System.out.println("Была восстановлена старая корзина");
            basket.getProducts();
            menu(basket, binFile);
        } else {
            basket.getProducts();
            menu(basket, binFile);
        }
    }

    public static void menu(Basket basket, File textFile) throws IOException {
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
                basket.saveBin(textFile);
            }
        }
    }
}
