package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import java.awt.image.BufferedImage;

public class Controller {

    public Path path;
    public Group lineGroup;
    public Button btnClear;
    public Rectangle canvas;
    public TextField guessText;
    public Button guessButton;
    public Button send;
    public ImageView display;

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
        lineGroup.getChildren().removeAll(lineGroup.getChildren());
        lineGroup.getChildren().add(canvas);
    }

    public void send() {
        System.out.println("pic displayed");
        display.setImage(getImage(lineGroup));
    }

    Image getImage(Node node){
        System.out.println("pic taken");
        WritableImage snapshot = node.snapshot(new SnapshotParameters(), null);
        BufferedImage buffImg = SwingFXUtils.fromFXImage(snapshot, null);
        Image image = SwingFXUtils.toFXImage(buffImg, null );
        return image;
    }
}