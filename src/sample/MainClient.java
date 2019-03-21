package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainClient extends Application {

    private Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();

        Thread.currentThread().setName("PictureViewer MainClient GUI Thread");

        // Display the scene
        primaryStage.setTitle("PictureViewer CLIENT");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        controller = loader.getController();
        controller.setClientMode();
        controller.setStage(primaryStage);
        controller.setGuesserMode();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
