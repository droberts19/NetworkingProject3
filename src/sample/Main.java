package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    static boolean multicastMode = true;

    private Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();

        Thread.currentThread().setName("PictureViewer MainServer GUI Thread");

        // Display the scene
        if (multicastMode) {
            primaryStage.setTitle("PictureViewer SERVER Multi-cast");
        } else {
            primaryStage.setTitle("PictureViewer SERVER");
        }
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        controller = loader.getController();
        controller.setServerMode();
        //controller.setGuesserMode();
        controller.setStage(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
