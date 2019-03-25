package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.awt.image.BufferedImage;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class Controller {

    private Path path;
    public Group lineGroup;
    public Button btnClear;
    public Rectangle canvas;
    public TextField guessText;
    public ImageView display;
    public Label label1;
    public Label label3;
    public Button beginGameButton;
    public TextField IPAddressText;
    public TextField portText;
    public TextField yourNameText;
    public Label label4;
    public Button draw;
    public Label turn;
    public ListView<String> player;
    public Button setName;
    public boolean guesser = true;
    private Stage stage;
    private SyncData inQueue;
    private SyncData outQueue;
    private boolean serverMode;
    static boolean connected;
    private ArrayList<String> nameOfClients;

    public void initialize() {

        nameOfClients = new ArrayList<String>();

        inQueue = new SyncData();
        outQueue = new SyncData();
        connected = false;

        GUIupdater sendTransmit = new GUIupdater(inQueue, Controller.this, display, label3, player, nameOfClients, turn, label1, guessText, lineGroup, canvas);
        Thread thread1 = new Thread(sendTransmit);
        thread1.setName("GUI Updater Thread");
        thread1.start();


        portText.setText("5000");

        PictureSender pictureThread = new PictureSender(Controller.this);
        Thread thread2 = new Thread(pictureThread);
        thread2.start();

        canvas.setCursor(Cursor.CROSSHAIR);
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
        display.setImage(null);
        lineGroup.getChildren().removeAll(lineGroup.getChildren());
        lineGroup.getChildren().add(display);
        lineGroup.getChildren().add(canvas);
        label3.setText("");
        label4.setText("");
        guessText.setText("");
    }

    private void delay(int x) {
            try {
                Thread.sleep(x);
            } catch (InterruptedException e) {
            }
            System.out.println("Did delay of " + x / 1000 + " secs");
            return;
    }

    public void sendPicture() {
        Image sendPic = getImage(lineGroup);
        Message sendMessage = new Message(yourNameText.getText(), sendPic, guessText.getText(), 2);
        while (!outQueue.put(sendMessage)) {
            Thread.currentThread().yield();
        }
    }

    public void send() {
        if (guesser) {
            if (guessText.getText().equals(label3.getText())) {
                label4.setText("YAY");
                turn.setText("YOU got it right! Hit the Drawer button!");
                Image sendPic = getImage(lineGroup);
                Message sendMessage3 = new Message(yourNameText.getText(), sendPic, guessText.getText(), 3);
                while (!outQueue.put(sendMessage3)) {
                    Thread.currentThread().yield();
                }
                if (label4.getText().equals("YAY") || turn.getText().equals("YOU got it right! Hit the Drawer button!")) {
                    draw.setDisable(false);
                    if (player.getItems().contains(yourNameText.getText())) {
                        player.getItems().remove(yourNameText.getText());
                        player.getItems().add(yourNameText.getText() + ": 1");
                        return;
                    }
                    if (player.getItems().contains(yourNameText.getText() + ": 1")) {
                        player.getItems().remove(yourNameText.getText() + ": 1");
                        player.getItems().add(yourNameText.getText() + ": 2");
                        return;
                    }
                    if (player.getItems().contains(yourNameText.getText() + ": 2")) {
                        player.getItems().remove(yourNameText.getText() + ": 2");
                        player.getItems().add(yourNameText.getText() + ": 3");
                        return;
                    }
                    if (player.getItems().contains(yourNameText.getText() + ": 3")) {
                        player.getItems().remove(yourNameText.getText() + ": 3");
                        player.getItems().add(yourNameText.getText() + ": 4");
                        return;
                    }
                    if (player.getItems().contains(yourNameText.getText() + ": 4")) {
                        player.getItems().remove(yourNameText.getText() + ": 4");
                        player.getItems().add(yourNameText.getText() + ": 5");
                        return;
                    }
                    if (player.getItems().contains(yourNameText.getText() + ": 5")) {
                        player.getItems().remove(yourNameText.getText() + ": 5");
                        player.getItems().add(yourNameText.getText() + ": 6");
                        return;
                    }
                    if (player.getItems().contains(yourNameText.getText() + ": 6")) {
                        player.getItems().remove(yourNameText.getText() + ": 6");
                        player.getItems().add(yourNameText.getText() + ": 7");
                        return;
                    }
                    if (player.getItems().contains(yourNameText.getText() + ": 7")) {
                        player.getItems().remove(yourNameText.getText() + ": 7");
                        player.getItems().add(yourNameText.getText() + ": 8");
                        return;
                    }
                    if (player.getItems().contains(yourNameText.getText() + ": 8")) {
                        player.getItems().remove(yourNameText.getText() + ": 8");
                        player.getItems().add(yourNameText.getText() + ": 9");
                        return;
                    }
                    if (player.getItems().contains(yourNameText.getText() + ": 9")) {
                        player.getItems().remove(yourNameText.getText() + ": 9");
                        player.getItems().add(yourNameText.getText() + ": 10 and wins");
                        return;
                    }

                }
            } else {
                label4.setText("NOO");
            }
        } else {
            Image sendPic = getImage(lineGroup);
            Message sendMessage = new Message(yourNameText.getText(), sendPic, guessText.getText(), 2);
            while (!outQueue.put(sendMessage)) {
                Thread.currentThread().yield();
            }

        }
    }

    public void setName() {
        if (connected) {
            player.getItems().add(yourNameText.getText());
            Image sendPic1 = getImage(lineGroup);
            Message sendMessage2 = new Message(yourNameText.getText(), sendPic1, guessText.getText(), 1);
            while (!outQueue.put(sendMessage2)) {
                Thread.currentThread().yield();
            }
        } else {
        }
    }

    public void setGuesserMode() {
        clear();
        guesser = true;
        label1.setText("What is your guess?");
    }

    public void disableDrawButton() {
        draw.setDisable(true);
    }

    public void ableSendButton() {
    }

    public void sendGuess() {
        Image sendPic2 = getImage(lineGroup);
        Message draw = new Message(yourNameText.getText(), sendPic2, guessText.getText(), 2);
        while (!outQueue.put(draw)) {
            Thread.currentThread().yield();
        }
    }

    public void setDrawerMode() {
        clear();
        guesser = false;
        draw.setDisable(true);
        turn.setText("YOU are drawing");
        label1.setText("What are you drawing?");
        Image sendPic3 = getImage(lineGroup);
        Message guess = new Message(yourNameText.getText(), sendPic3, guessText.getText(), 5);
        while (!outQueue.put(guess)) {
            Thread.currentThread().yield();
        }
    }

    public void sendDrawerMsg() {

    }

    public void setStage(Stage theStage) {
        stage = theStage;
    }


    void setServerMode() {
        serverMode = true;
        beginGameButton.setText("Start");
        try {
            IPAddressText.setText(InetAddress.getLocalHost().getHostAddress());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void setClientMode() {
        serverMode = false;
        beginGameButton.setText("Connect");
        // display the IP address for the local computer
        IPAddressText.setText("10.85.216.236");
    }

    public void startButtonPressed() {
        // If we're already connected, start button should be disabled
        if (connected) {
            // don't do anything else; the threads will stop and everything will be cleaned up by them.
            return;
        }
        // We can't start network connection if Port number is unknown
        if (portText.getText().isEmpty()) {
            // user did not enter a Port number, so we can't connect.
            return;
        }

        if (yourNameText.getText().isEmpty()) {
            return;
        }

        // We're gonna start network connection!
        connected = true;
        beginGameButton.setDisable(true);

        if (serverMode) {

            // We're a server: create a thread for listening for connecting clients
            Connect connectToNewClients = new Connect(Integer.parseInt(portText.getText()), inQueue, outQueue, yourNameText);
            Thread connectThread = new Thread(connectToNewClients);
            connectThread.start();

        } else {

            // We're a client: connect to a server
            try {
                System.out.println("Socket is trying to connect");
                Socket socketClientSide = new Socket(IPAddressText.getText(), Integer.parseInt(portText.getText()));
                System.out.println("Socket tried and did its job yay");

                // The socketClientSide provides 2 separate streams for 2-way communication
                //   the InputStream is for communication FROM server TO client
                //   the OutputStream is for communication TO server FROM client
                // Create data reader and writer from those stream (NOTE: ObjectOutputStream MUST be created FIRST)

                // Every client prepares for communication with its server by creating 2 new threads:
                //   Thread 1: handles communication TO server FROM client
                CommunicationOut communicationOut = new CommunicationOut(socketClientSide, new ObjectOutputStream(socketClientSide.getOutputStream()), outQueue);
                Thread communicationOutThread = new Thread(communicationOut);
                communicationOutThread.start();
                //   Thread 2: handles communication FROM server TO client
                CommunicationIn communicationIn = new CommunicationIn(socketClientSide, new ObjectInputStream(socketClientSide.getInputStream()), inQueue, null, yourNameText);
                Thread communicationInThread = new Thread(communicationIn);
                communicationInThread.start();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            // We connected!
        }
    }

    Image getImage(Node node){
        WritableImage snapshot = node.snapshot(new SnapshotParameters(), null);
        BufferedImage buffImg = SwingFXUtils.fromFXImage(snapshot, null);
        Image image = SwingFXUtils.toFXImage(buffImg, null );
        return image;
    }
}