package main.java.sample;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;
import javafx.scene.transform.Scale;

public class PaneScrolled implements EventHandler<ScrollEvent> {

    MyPane pane;

    public PaneScrolled(MyPane pane) {
        this.pane = pane;
    }

    @Override
    public void handle(ScrollEvent event) {
        double zoomFactor = 1.05;

        double deltaY = event.getDeltaY();
        if (deltaY < 0) {
            zoomFactor = 2.0 - zoomFactor;
        }

        Scale newScale = new Scale();
        newScale.setPivotX(event.getX());
        newScale.setPivotY(event.getY());
        newScale.setX(pane.getScaleX() * zoomFactor);
        newScale.setY(pane.getScaleY() * zoomFactor);

        pane.getTransforms().add(newScale);
    }
}
