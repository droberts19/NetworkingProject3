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
        controller = loader.getController();

        primaryStage.setTitle("Pictogram Guesser");
        primaryStage.setScene(new Scene(root, 550, 700));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
