package graph;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.util.Pair;

import java.util.List;

public class GraphController {

    public static final Double SCALING = 50.;
    @FXML
    GridPane gridPane;

    MyPane pane = new MyPane();

    @FXML
    void initialize() {

        assert gridPane != null;
        assert pane != null;

        gridPane.getChildren().addAll(pane);

        Line horizontalAxis = new Line(-Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 0);
        Line verticalAxis = new Line(0, -Integer.MAX_VALUE, 0, Integer.MAX_VALUE);

        pane.getChildren().add(horizontalAxis);
        pane.getChildren().add(verticalAxis);

        assert !pane.getChildren().isEmpty();


    }

    public void drawGraph(List<Pair<Double, Double>> points) {

        assert !points.isEmpty();

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

        GraphUtils.drawYvalues(pane, minYvalue, maxYvalue, 1);
        GraphUtils.drawXvalues(pane, minXvalue, maxXvalue, 1);

        graph.setOnMouseEntered(new MouseOverEventHandler(pane));
        graph.setOnMouseExited(new MouseOverExitEventHandler(pane));

        pane.getChildren().add(graph);

        assert !pane.getChildren().isEmpty();

        pane.setTranslateX(pane.getWidth() / 2);
        pane.setTranslateY(pane.getHeight() / 2);
    }

}
