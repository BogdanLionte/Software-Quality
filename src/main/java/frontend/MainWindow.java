package frontend;

import graph.GraphController;
import integral.PrefixEv;
import integral.PrefixIntegral;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import lombok.Getter;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Getter
public class MainWindow extends Stage {

    public final String TITLE = "Software Quality - Project";
    public final double HEIGHT = 750D;
    public final double WIDTH = 1000D;

    private VBox content = new VBox(10);
    private List<Pair<Double, Double>> points = new ArrayList<>();
    private Region veil = new Region();
    private WindowManager windowManager = new WindowManager(veil);
    private TextField functionInput = new TextField();
    private TextField lowInterval = new TextField();
    private TextField highInterval = new TextField();
    private TextField step = new TextField();
    private ToggleButton integral = new ToggleButton("yes/no");
    private Button draw = new Button("draw");
    private Button exportImage = new Button("export image");
    private Button exportText = new Button("export text");
    private boolean eraseApproximations;
    private static WritableImage image;
    private DirectoryChooser directoryChooser = new DirectoryChooser();
    private Stage drawWindow;

    public MainWindow() {
        StackPane root = new StackPane();
        content.setAlignment(Pos.TOP_CENTER);
        veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
        veil.setVisible(false);
        root.getChildren().addAll(content, veil);
        this.setScene(new Scene(root));
        this.setTitle(TITLE);
        this.setHeight(HEIGHT);
        this.setWidth(WIDTH);

        Text titleText = new Text();
        titleText.setText(TITLE);
        titleText.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.ITALIC, 30));
        content.getChildren().add(titleText);

        functionInput.setMaxWidth(600D);
        functionInput.setAlignment(Pos.BASELINE_LEFT);
        content.getChildren().add(new Label("Please insert function  "));
        content.getChildren().add(functionInput);

        HBox numbers = new HBox(10);
        numbers.setAlignment(Pos.CENTER);
        numbers.getChildren().add(new Label("Insert low-end : "));
        numbers.getChildren().add(lowInterval);
        numbers.getChildren().add(new Label("Insert high-end : "));
        numbers.getChildren().add(highInterval);
        numbers.getChildren().add(new Label("Insert step : "));
        numbers.getChildren().add(step);
        content.getChildren().add(numbers);

        HBox integralBox = new HBox(10);
        integralBox.setAlignment(Pos.CENTER);
        integralBox.getChildren().add(new Label("Show definite integral approximations : "));
        integralBox.getChildren().add(integral);
        content.getChildren().add(integralBox);

        HBox bottomButtons = new HBox(10);
        bottomButtons.setAlignment(Pos.CENTER);
        exportText.setDisable(true);
        exportImage.setDisable(true);
        bottomButtons.getChildren().add(draw);
        bottomButtons.getChildren().add(exportImage);
        bottomButtons.getChildren().add(exportText);
        content.getChildren().add(bottomButtons);

        draw.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                drawListener();
            }
        });

        exportImage.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                imageExportListener();
            }
        });

        exportText.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                textExportListener();
            }
        });
    }

    private void drawListener() {
        double lowIntervalNumber;
        double highIntervalNumber;
        double stepNumber;

        try {
            lowIntervalNumber = Double.parseDouble(lowInterval.getText());
        } catch (NumberFormatException e) {
            windowManager.openAlert("Low interval number is not valid!");
            return;
        }

        try {
            highIntervalNumber = Double.parseDouble(highInterval.getText());
        } catch (NumberFormatException e) {
            windowManager.openAlert("High interval number is not valid! ");
            return;
        }

        try {
            stepNumber = Double.parseDouble(step.getText());
        } catch (NumberFormatException e) {
            windowManager.openAlert("Step number is not valid!");
            return;
        }

        if(lowIntervalNumber > highIntervalNumber) {
            windowManager.openAlert("Low bigger than high !!");
            return;
        }

        if(stepNumber > highIntervalNumber - lowIntervalNumber) {
            windowManager.openAlert("Step bigger than interval!");
            return;
        }

        try {
            points = PrefixEv.evaluate(functionInput.getText(), lowIntervalNumber, highIntervalNumber, stepNumber);
        } catch (IOException e) {
            windowManager.openAlert("NaN");
            return;
        }

        drawGraph(points);

        if (integral.isSelected()) {
            List<Double> approximations;
            try {
                approximations = PrefixIntegral.integral(PrefixEv.infixToPrefix(functionInput.getText()), lowIntervalNumber,
                        highIntervalNumber, stepNumber);
            } catch (IOException e) {
                windowManager.openAlert("NaN");
                return;
            }

            HBox numbers = new HBox(10);
            numbers.setAlignment(Pos.CENTER);

            for (Double approx : approximations) {
                numbers.getChildren().add(new Label(approx.toString()));
            }

            if(eraseApproximations) {
                content.getChildren().remove(content.getChildren().size() - 1);
            }
            eraseApproximations = true;
            content.getChildren().add(numbers);
        }
        exportImage.setDisable(false);
        exportText.setDisable(false);
    }

    private void drawGraph(List<Pair<Double, Double>> points) {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/graph/graph.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 450, 250);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GraphController graphController = fxmlLoader.getController();

        Stage stage = new Stage();
        stage.setTitle("Input");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        graphController.drawGraph(points);

        drawWindow = stage;
        image = stage.getScene().snapshot(null);
    }

    private void imageExportListener() {
        File dir = getDirectoryFromUser();
        if (dir == null) {
            return;
        }

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", baos);
            baos.flush();
            Files.write(Paths.get(dir.getAbsolutePath(), "imgExport.png"), baos.toByteArray(),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            baos.close();
        } catch (IOException e) {
            windowManager.openAlert("Error writing to selected file");
        }
    }

    private void textExportListener() {
        File dir = getDirectoryFromUser();
        if (dir == null) {
            return;
        }

        try {
            new TextExporter().saveToCsv(points, dir);
        } catch (IOException e) {
            windowManager.openAlert("Error writing to selected file");
        }
    }

    //this function returns null if the user closes the directory chooser without selecting anything
    private File getDirectoryFromUser() {
        return directoryChooser.showDialog(this);
    }
}
