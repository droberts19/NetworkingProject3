package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    static boolean multicastMode = false;

    private Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        controller = loader.getController();

        if (multicastMode) {
            primaryStage.setTitle("PictureSERVER Multi-cast");
        } else {
            primaryStage.setTitle("PictureSERVER");
        }
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        primaryStage.setTitle("Pictogram");
        primaryStage.setScene(new Scene(root, 550, 700));
        primaryStage.show();

        controller = loader.getController();
        controller.setServerMode();
        controller.setStage(primaryStage);


    }


    public static void main(String[] args) {
        launch(args);
    }
}
