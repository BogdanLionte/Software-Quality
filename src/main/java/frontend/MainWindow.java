package frontend;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends Stage {

    public final String TITLE = "Software Quality - Project";
    public final double HEIGHT = 750D;
    public final double WIDTH = 1000D;

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

    public MainWindow() {
        StackPane root = new StackPane();
        VBox content = new VBox(10);
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
        content.getChildren().add(new Label("Please insert function in prefix form: "));
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
        String function = functionInput.getText();
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

        //draw the graph here and get the points

        if (integral.isSelected()) {
            System.out.println("Selected");
            //get integral approximations here and add them to the window
        }
        exportImage.setDisable(false);
        exportText.setDisable(false);
    }

    private void imageExportListener() {
        File dir = getDirectoryFromUser();
        if (dir == null) {
            return;
        }
    }

    private void textExportListener() {
        File dir = getDirectoryFromUser();
        if (dir == null) {
            return;
        }
        points.add(new Pair<>(10D, 10D));
        points.add(new Pair<>(20D, 20D));
        try {
            TextExporter.saveToCsv(points, dir);
        } catch (IOException e) {
            windowManager.openAlert("Error writing to selected file");
        }
    }

    //this function returns null if the user closes the directory chooser without selecting anything
    private File getDirectoryFromUser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        return directoryChooser.showDialog(this);
    }
}