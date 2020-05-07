package frontend;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MainWindowTest extends ApplicationTest {

    private MainWindow mainWindow;

    @Override
    public void start(Stage stage) {
        mainWindow = new MainWindow();
        mainWindow.show();
    }

    @Test
    public void basicTest() {
        clickOn(mainWindow.getFunctionInput()).write("1 + x");
        clickOn(mainWindow.getLowInterval()).write("-10");
        clickOn(mainWindow.getHighInterval()).write("10");
        clickOn(mainWindow.getStep()).write("0.1");
        clickOn(mainWindow.getDraw());
        assertNotNull(mainWindow.getDrawWindow());
    }

    @Test
    public void basicTestWithIntegral() {
        clickOn(mainWindow.getFunctionInput()).write("sin ( x ) + cos ( x )");
        clickOn(mainWindow.getLowInterval()).write("-100");
        clickOn(mainWindow.getHighInterval()).write("100");
        clickOn(mainWindow.getStep()).write("0.1");
        clickOn(mainWindow.getIntegral());
        clickOn(mainWindow.getDraw());
        assertNotNull(mainWindow.getDrawWindow());
        HBox numbers = (HBox) mainWindow.getContent().getChildren().get(mainWindow.getContent().getChildren().size() - 1);
        assertEquals(3, numbers.getChildren().size());
        assertEquals("-0.9256553115663263", ((Label) numbers.getChildren().get(0)).getText());
        assertEquals("-0.9707619669660492", ((Label) numbers.getChildren().get(1)).getText());
        assertEquals("-1.0134422230114166", ((Label) numbers.getChildren().get(2)).getText());
    }

    @Test
    public void invalidLowIntervalNumberTest() {
        clickOn(mainWindow.getFunctionInput()).write("x ^ 2");
        clickOn(mainWindow.getLowInterval()).write("invalid");
        clickOn(mainWindow.getHighInterval()).write("300");
        clickOn(mainWindow.getStep()).write("0.5");
        checkAlert("Low interval number is not valid!");

    }

    @Test
    public void invalidHighIntervalNumberTest() {
        clickOn(mainWindow.getLowInterval()).write("-100");
        clickOn(mainWindow.getHighInterval()).write("invalid");
        checkAlert("High interval number is not valid! ");
    }

    @Test
    public void invalidStepNumberTest() {
        clickOn(mainWindow.getLowInterval()).write("-100");
        clickOn(mainWindow.getHighInterval()).write("100");
        clickOn(mainWindow.getStep()).write("invalid");
        checkAlert("Step number is not valid!");
    }

    @Test
    public void lowBiggerThanHighTest() {
        clickOn(mainWindow.getLowInterval()).write("100");
        clickOn(mainWindow.getHighInterval()).write("-100");
        clickOn(mainWindow.getStep()).write("0.1");
        checkAlert("Low bigger than high !!");
    }

    @Test
    public void stepBiggerThanInterval() {
        clickOn(mainWindow.getLowInterval()).write("-100");
        clickOn(mainWindow.getHighInterval()).write("100");
        clickOn(mainWindow.getStep()).write("201");
        checkAlert("Step bigger than interval!");
    }

    @Test
    public void wrongFunctionTest() {
        clickOn(mainWindow.getFunctionInput()).write("invalid");
        clickOn(mainWindow.getLowInterval()).write("-100");
        clickOn(mainWindow.getHighInterval()).write("100");
        clickOn(mainWindow.getStep()).write("0.1");
        checkAlert("NaN");
    }

    private void checkAlert(String s) {
        assertFalse(mainWindow.getVeil().visibleProperty().get());
        clickOn(mainWindow.getDraw());
        Alert alert = mainWindow.getWindowManager().getCurrentAlert();
        assertEquals(s, alert.getContentText());
        assertTrue(alert.showingProperty().get());
        assertTrue(mainWindow.getVeil().visibleProperty().get());
        assertEquals(Alert.AlertType.ERROR, alert.getAlertType());
        clickOn(alert.getDialogPane().lookupButton(ButtonType.OK));
        assertFalse(alert.showingProperty().get());
        assertFalse(mainWindow.getVeil().visibleProperty().get());
    }
}
