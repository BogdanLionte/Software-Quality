package graph;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    GridPane gridPane;

    MyPane pane = new MyPane();

    double SCALING = 1000;

    @FXML
    void initialize() {
        gridPane.getChildren().addAll(pane);

        Line horizontalAxis = new Line(-Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 0);
        Line verticalAxis = new Line(0, -Integer.MAX_VALUE, 0, Integer.MAX_VALUE);


        double minValue = -30;
        double maxValue = 30;
        double someStep = 5;
        drawYvalues(minValue, maxValue, someStep);

        double left = -30;
        double right = 30;
        double step = 0.1;
        drawXvalues(left, right, step);


        pane.getChildren().add(horizontalAxis);
        pane.getChildren().add(verticalAxis);

        List<Double> points = new ArrayList<>();
        while (left < right) {
            points.add(left * 50);
            points.add(Math.sin(left) * (-50) );
            left += step;
        }

        drawGraph(points, pane);

    }

    private void drawGraph(List<Double> points, MyPane pane) {
        Polyline graph = new Polyline();
        graph.getPoints().addAll(points);

        pane.getChildren().add(graph);
    }

    private void drawXvalues(double left, double right, double step) {
        for (double i = left; i < right; i += step) {
            double labelValue = i * 50;
            Label xLabel = new Label(String.valueOf(i));
            xLabel.setTranslateX(labelValue);
            xLabel.setTranslateY(0);
            pane.getChildren().add(xLabel);
        }
    }

    private void drawYvalues(double minValue, double maxValue, double someStep) {
        for (double i = minValue; i < maxValue; i += someStep) {
            double labelValue = i * 50;
            Label yLabel = new Label(String.valueOf(i));
            yLabel.setTranslateY((-1) * (labelValue));
            yLabel.setTranslateX(0);
            pane.getChildren().add(yLabel);
        }
    }

}
