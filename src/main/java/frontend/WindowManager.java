package frontend;

import javafx.scene.control.Alert;
import javafx.scene.layout.Region;

class WindowManager {

    private Region veil;

    WindowManager(Region veil) {
        this.veil = veil;
    }

    void openAlert(String text){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        veil.visibleProperty().bind(alert.showingProperty());
        alert.setContentText(text);
        alert.show();
    }
}
