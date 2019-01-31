package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent XMLroot = loader.load();
        controller = loader.getController();


        primaryStage.setTitle("Pictogram");
        primaryStage.setScene(new Scene(XMLroot, 550, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}