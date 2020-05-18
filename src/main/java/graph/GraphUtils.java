package graph;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import static graph.GraphController.SCALING;

public class GraphUtils {

    public static void drawXvalues(Pane pane, double left, double right, double step) {

        assert left < right;
        assert  step > 0;

        for (double i = left; i <= right; i += step) {
            if (i == 0) {
                continue;
            }
            double labelValue = i * SCALING;
            Label xLabel = new Label(String.format("%.2f", i));
            xLabel.setTranslateX(labelValue);
            xLabel.setTranslateY(0);
            pane.getChildren().add(xLabel);
        }
    }

    public static void drawYvalues(Pane pane, double minValue, double maxValue, double someStep) {

        assert minValue < maxValue;
        assert someStep > 10;

        for (double i = minValue; i <= maxValue; i += someStep) {
            double labelValue = i * SCALING;
            if (labelValue <= 0.3 && labelValue >= -0.3) {
                continue;
            }
            Label yLabel = new Label(String.format("%.2f", i));
            yLabel.setTranslateY((-1) * (labelValue));
            yLabel.setTranslateX(0);
            pane.getChildren().add(yLabel);
        }
    }
}
