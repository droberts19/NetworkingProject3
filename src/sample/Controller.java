package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import java.awt.image.BufferedImage;

public class Controller {

    public Path path;
    public Group lineGroup;
    public Button btnClear;
    public Rectangle canvas;
    public TextField guessText;
    public Button send;
    public ImageView display;
    public Label label1;
    public Label label2;
    public Label label3;
    public Label label4;
    public TextField answerText;
    public Button send2;
    private SyncData syncData;
    private boolean isItSent;
    private SyncData inQueue;
    private SyncData outQueue;
    private boolean serverMode;
    static boolean connected;

    public void initialize() {

        inQueue = new SyncData();
        outQueue = new SyncData();
        connected = false;

        isItSent = false;
        syncData = new SyncData();
        Threadz transmit = new Threadz(syncData, display);
        Thread thread = new Thread(transmit);
        thread.start();

        canvas.setFill(Color.LIGHTGRAY);
        canvas.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent me) {

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
                path = null;

            }
        });

        canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent me) {

                // keep lines within rectangle

                if (canvas.getBoundsInLocal().contains(me.getX(), me.getY())) {
                    path.getElements().add(new LineTo(me.getSceneX(), me.getSceneY()));
                }

            }
        });
    }

    public void clear() {
        lineGroup.getChildren().removeAll(lineGroup.getChildren());
        lineGroup.getChildren().add(canvas);
        display.setImage(null);
        guessText.setText("");
        answerText.setText("");
    }

    public void send() {
        Image sendPic = getImage(lineGroup);
        if (sendPic != null) {
            while (!syncData.put(sendPic)) {
                Thread.currentThread().yield();
            }
        }
        isItSent = true;
    }

    public void guessAnswer() {
        if (isItSent == true) {
            System.out.println("label created with text: " + guessText.getText());
            System.out.println("guess is: " + answerText.getText());
            if (answerText.getText().equals(guessText.getText())) {
                System.out.println("they same");
                //answerLabel.setText("YAY");
            } else {
                //answerLabel.setText("you suck");
            }
        }
        isItSent = false;
    }

    Image getImage(Node node){
        WritableImage snapshot = node.snapshot(new SnapshotParameters(), null);
        BufferedImage buffImg = SwingFXUtils.fromFXImage(snapshot, null);
        Image image = SwingFXUtils.toFXImage(buffImg, null );
        return image;
    }
}