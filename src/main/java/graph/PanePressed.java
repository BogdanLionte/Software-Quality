package graph;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class PanePressed implements EventHandler<MouseEvent> {

    MyPane pane;

    public PanePressed(MyPane pane) {
        this.pane = pane;
    }


    @Override
    public void handle(MouseEvent event) {
        pane.dragDeltaX = (float) (pane.getTranslateX() - event.getSceneX());
        pane.dragDeltaY = (float) (pane.getTranslateY() - event.getSceneY());
    }
}
