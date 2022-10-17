import com.google.gson.Gson;
import org.json.simple.parser.ParseException;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.Scanner;

public class Main {
    protected static Scanner scanner = new Scanner(System.in);
    protected static String[] products = new String[]{"Молоко", "Хлеб", "Хлопья", "Вода", "Чипсы"};
    protected static long[] prices = new long[]{80, 21, 120, 30, 200};
    protected static long[] basketNum = new long[products.length];
    protected static boolean[] trueOrFalse = new boolean[products.length];
    protected static Basket basket = new Basket(products, prices, basketNum, trueOrFalse);
    protected static int[] saveOrNo = new int[2];
    protected static File forSave;
    protected static int logOrNo;
    protected static File forLog;

    public static void main(String[] args) throws IOException {
        read();
        basket.getProducts();
        menu(basket);
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
                if (saveOrNo[0] == 1 && saveOrNo[1] == 0) {
                    basket.saveJSON(forSave);
                } else if (saveOrNo[0] == 1 && saveOrNo[1] == 1) {
                    basket.saveTxt(forSave);
                }
                if (logOrNo == 1) {
                    ClientLog.log(product, sum, forLog);
                }
            }
        }
    }

    private static void read() {
        try {
            File fXmlFile = new File("shop.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList loadList = doc.getElementsByTagName("load");
            NodeList saveList = doc.getElementsByTagName("save");
            NodeList logList = doc.getElementsByTagName("log");
            Node loadNode = loadList.item(0);
            Node saveNode = saveList.item(0);
            Node logNode = logList.item(0);
            Element loadElement = (Element) loadNode;
            Element saveElement = (Element) saveNode;
            Element logElement = (Element) logNode;
            if (loadElement.getElementsByTagName("enabled").item(0).getTextContent().equals("false")) {
                System.out.println("Загрузка данных по прошлой сессии отключена в настройках.");
            } else if (loadElement.getElementsByTagName("enabled").item(0).getTextContent().equals("true")) {
                System.out.println("Была восстановлена старая корзина");
                if (loadElement.getElementsByTagName("format").item(0).getTextContent().equals("json")) {
                    File jsonFile = new File(loadElement.getElementsByTagName("fileName").item(0).getTextContent());
                    basket = Basket.loadJSON(String.valueOf(jsonFile));
                } else if (loadElement.getElementsByTagName("format").item(0).getTextContent().equals("text")) {
                    File textFile = new File(loadElement.getElementsByTagName("fileName").item(0).getTextContent());
                    basket = Basket.loadFromTxtFile(textFile);
                }
            }

            if (saveElement.getElementsByTagName("enabled").item(0).getTextContent().equals("false")) {
                System.out.println("Сохранение корзины выключено");
                saveOrNo[0] = 0;
            } else if (saveElement.getElementsByTagName("enabled").item(0).getTextContent().equals("true")) {
                System.out.println("Сохранение корзины включено");
                saveOrNo[0] = 1;
                if (saveElement.getElementsByTagName("format").item(0).getTextContent().equals("json")) {
                    saveOrNo[1] = 0;
                    forSave = new File(saveElement.getElementsByTagName("fileName").item(0).getTextContent());
                } else if (saveElement.getElementsByTagName("format").item(0).getTextContent().equals("text")) {
                    saveOrNo[1] = 1;
                    forSave = new File(saveElement.getElementsByTagName("fileName").item(0).getTextContent());
                }
            }

            if (logElement.getElementsByTagName("enabled").item(0).getTextContent().equals("false")) {
                System.out.println("Сохранение логов выключено");
            } else if (logElement.getElementsByTagName("enabled").item(0).getTextContent().equals("true")) {
                System.out.println("Сохранение логов включено");
                logOrNo = 1;
                forLog = new File(logElement.getElementsByTagName("fileName").item(0).getTextContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
