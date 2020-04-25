package graph;

import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.util.Pair;

import java.util.List;

public class GraphController {

    private static final Double SCALING = 50.;
    private static WritableImage image;
    @FXML
    GridPane gridPane;

    MyPane pane = new MyPane();

    @FXML
    void initialize() {
        gridPane.getChildren().addAll(pane);

        Line horizontalAxis = new Line(-Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 0);
        Line verticalAxis = new Line(0, -Integer.MAX_VALUE, 0, Integer.MAX_VALUE);

        pane.getChildren().add(horizontalAxis);
        pane.getChildren().add(verticalAxis);


    }

    public void drawGraph(List<Pair<Double, Double>> points) {
        Polyline graph = new Polyline();

        double minYvalue = Integer.MAX_VALUE;
        double maxYvalue = Integer.MIN_VALUE;
        double minXvalue = Integer.MAX_VALUE;
        double maxXvalue = Integer.MIN_VALUE;

        for (Pair<Double, Double> pair :
                points) {
            if (pair.getKey() < minXvalue)
                minXvalue = pair.getKey();
            if (pair.getKey() > maxXvalue)
                maxXvalue = pair.getKey();
            if (pair.getValue() < minYvalue)
                minYvalue = pair.getValue();
            if (pair.getValue() > maxYvalue)
                maxYvalue = pair.getValue();
            graph.getPoints().addAll(pair.getKey() * SCALING, pair.getValue() * (-SCALING));
        }

        drawYvalues(minYvalue, maxYvalue, 1);
        drawXvalues(minXvalue, maxXvalue, 1);

        pane.getChildren().add(graph);

        pane.setTranslateX(pane.getWidth() / 2);
        pane.setTranslateY(pane.getHeight() / 2);

        image = pane.snapshot(new SnapshotParameters(), new WritableImage((int)pane.getWidth(), (int)pane.getHeight()));
    }

    private void drawXvalues(double left, double right, double step) {
        for (double i = left; i < right; i += step) {
            double labelValue = i * SCALING;
            Label xLabel = new Label(String.valueOf(i));
            xLabel.setTranslateX(labelValue);
            xLabel.setTranslateY(0);
            pane.getChildren().add(xLabel);
        }
    }

    private void drawYvalues(double minValue, double maxValue, double someStep) {
        for (double i = minValue; i < maxValue; i += someStep) {
            double labelValue = i * SCALING;
            Label yLabel = new Label(String.valueOf(i));
            yLabel.setTranslateY((-1) * (labelValue));
            yLabel.setTranslateX(0);
            pane.getChildren().add(yLabel);
        }
    }

    public WritableImage getImage() {
        return image;
    }
}
