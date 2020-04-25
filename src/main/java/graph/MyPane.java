package graph;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


public class MyPane extends Pane {

    public double dragDeltaX;
    public double dragDeltaY;

    public Label yValueLabel = new Label();

    public MyPane() {
        getChildren().add(yValueLabel);

        addEventFilter(MouseEvent.MOUSE_DRAGGED, new PaneMouseDragging(this));
        addEventFilter(MouseEvent.MOUSE_PRESSED, new PanePressed(this));
        setOnScroll(new PaneScrolled(this));
    }

}
