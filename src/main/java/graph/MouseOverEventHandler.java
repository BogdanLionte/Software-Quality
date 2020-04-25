package graph;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


public class MouseOverEventHandler implements EventHandler<MouseEvent> {
    private final MyPane pane;

    public MouseOverEventHandler(MyPane pane) {
        this.pane = pane;
    }

    @Override
    public void handle(MouseEvent event) {
        Double yValue = event.getY() / (-GraphController.SCALING);
        pane.yValueLabel.setText(String.valueOf(yValue));
        pane.yValueLabel.setVisible(true);
        pane.yValueLabel.setTranslateX(event.getX());
        pane.yValueLabel.setTranslateY(event.getY() - 15);
    }
}
