package frontend;

import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class TextExporter {

    public static String FILENAME = "textExport.csv";

    public static void saveToCsv(List<Pair<Double, Double>> points, File dir) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("x,f(x)\n");
        for (Pair<Double, Double> point : points) {
            sb.append(point.getKey().toString());
            sb.append(",");
            sb.append(point.getValue().toString());
            sb.append("\n");
        }
        Files.write(Paths.get(dir.getAbsolutePath(), FILENAME), sb.toString().getBytes(),
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
