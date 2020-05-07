package frontend;

import javafx.util.Pair;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TextExporterTest {

    private static File dir;

    @BeforeClass
    public static void setUp() {
        String currentWorkingDir = System.getProperty("user.dir");
        dir = new File(currentWorkingDir);
    }

    @Test
    public void saveToCsvTest() {
        List<Pair<Double, Double>> points = Arrays.asList(new Pair<>(0.0D, 0.0D), new Pair<>(1.0D, 1.0D),
                new Pair<>(2.0D, 2.0D));
        String expectedOutput = "x,f(x)\n" +
                "0.0,0.0\n" +
                "1.0,1.0\n" +
                "2.0,2.0\n";

        try {
            assertEquals(expectedOutput, getActualOutput(points, dir));
        } catch (IOException e) {
            fail(e.getMessage());
        }

        //try again to check if the file will be overwritten correctly
        points.set(2, new Pair<>(3.0D, 3.0D));
        expectedOutput = "x,f(x)\n" +
                "0.0,0.0\n" +
                "1.0,1.0\n" +
                "3.0,3.0\n";
        try {
            assertEquals(expectedOutput, getActualOutput(points, dir));
        } catch (IOException e) {
            fail(e.getMessage());
        }

    }

    @Test(expected = IOException.class)
    public void saveToCsvWithEmptyList() throws IOException {
        getActualOutput(new ArrayList<>(), dir);
    }

    @Test(expected = IOException.class)
    public void saveToCsvWithNullList() throws IOException {
        getActualOutput(null, dir);
    }

    private String getActualOutput(List<Pair<Double, Double>> points, File dir) throws IOException {
        new TextExporter().saveToCsv(points, dir);
        return Files.readAllLines(Paths.get(dir.getAbsolutePath(), TextExporter.FILENAME)).stream()
                .reduce("", (str1, str2) -> str1 + str2 + "\n");
    }

    @AfterClass
    public static void cleanUp() {
        File fileToDelete = new File(dir.getAbsolutePath(), TextExporter.FILENAME);
        if (fileToDelete.delete()) {
            System.out.println("Cleanup successful");
        } else {
            System.out.println("Cleanup failed");
        }
    }
}
