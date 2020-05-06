package graph;

import de.saxsys.javafx.test.JfxRunner;
import javafx.event.Event;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.util.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

//@RunWith(MockitoJUnitRunner.class)
@RunWith(JfxRunner.class)
public class GraphControllerTest {


    @InjectMocks
    GraphController cut;
    //
    @Mock
    MyPane pane;

//    @Mock
//    Label yValueLabel;

    @Test
    public void test() {
        cut = new GraphController();
        cut.gridPane = new GridPane();
        cut.initialize();

        assertTrue(cut.gridPane.getChildren().contains(cut.pane));
        assertEquals(3, cut.pane.getChildren().size());
        assertEquals(1, 1);

        Label label = (Label) cut.pane.getChildren().get(0);
        assertEquals("", label.getText());

        Line horizontal = (Line) cut.pane.getChildren().get(1);
        assertEquals(Integer.MIN_VALUE, horizontal.getStartX(), 1);
        assertEquals(0, horizontal.getStartY(), 1);
        assertEquals(Integer.MAX_VALUE, horizontal.getEndX(), 1);
        assertEquals(0, horizontal.getEndY(), 1);

        Line vertical = (Line) cut.pane.getChildren().get(2);
        assertEquals(0, vertical.getStartX(), 1);
        assertEquals(Integer.MIN_VALUE, vertical.getStartY(), 1);
        assertEquals(0, vertical.getEndX(), 1);
        assertEquals(Integer.MAX_VALUE, vertical.getEndY(), 1);

    }

    @Test
    public void testDrawGraph() {
        cut = new GraphController();
        cut.gridPane = new GridPane();
        cut.initialize();
        List<Pair<Double, Double>> points = new ArrayList<>();
        Pair<Double, Double> pair1 = new Pair<>(1.0, 2.0);
        Pair<Double, Double> pair2 = new Pair<>(12.0, 22.0);
        Pair<Double, Double> pair3 = new Pair<>(13.0, 23.0);
        Pair<Double, Double> pair4 = new Pair<>(21.0, 26.0);
        Pair<Double, Double> pair5 = new Pair<>(16.0, 42.0);
        points.add(pair1);
        points.add(pair2);
        points.add(pair3);
        points.add(pair4);
        points.add(pair5);

        cut.drawGraph(points);

        Polyline graph = (Polyline) cut.pane.getChildren().get(cut.pane.getChildren().size() - 1);
        assertEquals(points.size() * 2, graph.getPoints().size());
        System.out.println(graph.getPoints());
        assertTrue(graph.getPoints().containsAll(new ArrayList<>(
                Arrays.asList(1.0 * GraphController.SCALING,
                        -2.0 * GraphController.SCALING,
                        12.0 * GraphController.SCALING,
                        -22.0 * GraphController.SCALING,
                        13.0 * GraphController.SCALING,
                        -23.0 * GraphController.SCALING,
                        21.0 * GraphController.SCALING,
                        -26.0 * GraphController.SCALING,
                        16.0 * GraphController.SCALING,
                        -42.0 * GraphController.SCALING))));

        Event.fireEvent(cut.pane, new MouseEvent(MouseEvent.MOUSE_PRESSED, 0.0, 0.0, 0.0, 0.0, MouseButton.PRIMARY, 1,
                true, true, true, true, true, true, true, true, true, true, null));
        Event.fireEvent(cut.pane, new MouseEvent(MouseEvent.MOUSE_DRAGGED, 0.0, 0.0, 0.0, 0.0, MouseButton.PRIMARY, 1,
                true, true, true, true, true, true, true, true, true, true, null));
        Event.fireEvent(graph, new MouseEvent(MouseEvent.MOUSE_ENTERED, 0.0, 0.0, 0.0, 0.0, MouseButton.PRIMARY, 1,
                true, true, true, true, true, true, true, true, true, true, null));
        Event.fireEvent(graph, new MouseEvent(MouseEvent.MOUSE_EXITED_TARGET, 0.0, 0.0, 0.0, 0.0, MouseButton.PRIMARY, 1,
                true, true, true, true, true, true, true, true, true, true, null));
        Event.fireEvent(graph, new ScrollEvent(ScrollEvent.SCROLL, 0.0, 0.0, 0.0, 0.0, true, true, true, true, true, true, 0.0, -5.0, 0.0, -2.0, null, 0.0, null, 0.0, 0, null));

    }

}
