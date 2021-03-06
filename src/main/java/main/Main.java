package main;

import frontend.MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        new MainWindow().show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
