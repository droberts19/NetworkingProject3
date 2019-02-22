package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGuesser extends Application {

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
        controller.setGuesserMode();
        controller.setStage(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
