import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ClientLog {
    public static void log(int productNum, int amount, File fileName) {
        String[] choice = new String[]{String.valueOf(productNum), String.valueOf(amount)};
        try(CSVWriter writer = new CSVWriter(new FileWriter(String.valueOf(fileName), true))) {
            writer.writeNext(choice);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
