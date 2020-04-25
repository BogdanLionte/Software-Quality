package graph;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class PaneMouseDragging implements EventHandler<MouseEvent> {

    MyPane pane;

    public PaneMouseDragging(MyPane pane) {
        this.pane = pane;
    }

    @Override
    public void handle(MouseEvent event)
    {
        Point2D pointDelta = new Point2D(event.getSceneX() + pane.dragDeltaX, event.getSceneY() + pane.dragDeltaY);

        this.pane.setTranslateX(pointDelta.getX());
        this.pane.setTranslateY(pointDelta.getY());

    }
}
