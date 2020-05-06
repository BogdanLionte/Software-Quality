package graph;

import static org.junit.Assert.assertEquals;

import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(JfxRunner.class)
public class GraphUtilsTest {

    @Test
    public void testDrawXvalues() {
        double left = -10;
        double step = 1;
        double right = 10;

        Pane pane = new Pane();
        GraphUtils.drawYvalues(pane, left, right, step);
        List<Node> children = pane.getChildren();

        Label first = (Label) children.get(0);
        Label last = (Label) children.get(children.size() - 1);
        Label middle = (Label) children.get(children.size() / 2 );

        assertEquals(20, children.size());

        assertEquals("-10.00", first.getText());
        assertEquals("10.00", last.getText());
        assertEquals("1.00", middle.getText());

        assertEquals(0.0, first.getTranslateX(), 0.01);
        assertEquals(500.0, first.getTranslateY(), 0.01);

        assertEquals(0.0, last.getTranslateX(), 0.01);
        assertEquals(-500.0, last.getTranslateY(), 0.01);

        assertEquals(0.0, middle.getTranslateX(), 0.01);
        assertEquals(-50.0, middle.getTranslateY(), 0.01);
    }
    @Test
    public void testDrawYvalues() {
        double left = -10;
        double step = 1;
        double right = 10;

        Pane pane = new Pane();
        GraphUtils.drawXvalues(pane, left, right, step);
        List<Node> children = pane.getChildren();

        Label first = (Label) children.get(0);
        Label last = (Label) children.get(children.size() - 1);
        Label middle = (Label) children.get(children.size() / 2 );

        assertEquals(20, children.size());

        assertEquals("-10.00", first.getText());
        assertEquals("10.00", last.getText());
        assertEquals("1.00", middle.getText());

        assertEquals(-500.0, first.getTranslateX(), 0.01);
        assertEquals(0.0, first.getTranslateY(), 0.01);

        assertEquals(500.0, last.getTranslateX(), 0.01);
        assertEquals(0.0, last.getTranslateY(), 0.01);

        assertEquals(50.0, middle.getTranslateX(), 0.01);
        assertEquals(0.0, middle.getTranslateY(), 0.01);
    }

}
