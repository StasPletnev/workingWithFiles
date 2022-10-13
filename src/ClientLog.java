import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientLog {
    public static void log(int productNum, int amount) {
        String[] choice = new String[]{String.valueOf(productNum), String.valueOf(amount)};
        try(CSVWriter writer = new CSVWriter(new FileWriter("log.csv", true))) {
            writer.writeNext(choice);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void exportAsCSV(File txtFile) {

    }
}
