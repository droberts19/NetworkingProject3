package sample;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class Controller {

    public Path path;
    public Group lineGroup;
    public Button btnClear;
    public Rectangle canvas;
    public TextField guessText;
    public Button guessButton;
    public Button send;

    public void initialize() {

        canvas.setFill(Color.LIGHTGRAY);
        canvas.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent me) {
                System.out.println("canvas.setOnMousePressed()");

                path = new Path();
                path.setMouseTransparent(true);
                path.setStrokeWidth(2.0);
                path.setStroke(Color.BLACK);
                lineGroup.getChildren().add(path);
                path.getElements().add(new MoveTo(me.getSceneX(), me.getSceneY()));
            }
        });

        canvas.setOnMouseReleased(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent me) {
                System.out.println("canvas.setOnMouseReleased()");
                path = null;

            }
        });

        canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent me) {
                System.out.println("canvas.setOnMouseDragged()");

                // keep lines within rectangle

                if (canvas.getBoundsInLocal().contains(me.getX(), me.getY())) {
                    System.out.println(me);
                    path.getElements().add(new LineTo(me.getSceneX(), me.getSceneY()));
                }

            }
        });
    }

    public void clear() {
        lineGroup.getChildren().remove(path);
    }

    public void send() {

    }
}