package graph;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MouseOverExitEventHandler implements EventHandler<MouseEvent> {
    private final MyPane pane;

    public MouseOverExitEventHandler(MyPane pane) {
        this.pane = pane;
    }

    @Override
    public void handle(MouseEvent event) {
        pane.yValueLabel.setVisible(false);
    }
}
