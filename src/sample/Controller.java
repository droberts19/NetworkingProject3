package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
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
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

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
    public Button beginGameButton;
    public TextField IPAddressText;
    public TextField statusText;
    public TextField portText;
    public TextField yourNameText;
    public Label label4;
    private Stage stage;
    private SyncData inQueue;
    private SyncData outQueue;
    private boolean serverMode;
    static boolean connected;

    public void initialize() {

        inQueue = new SyncData();
        outQueue = new SyncData();
        connected = false;

        GUIupdater transmit = new GUIupdater(outQueue, display, label3);
        Thread thread = new Thread(transmit);
        thread.start();
        GUIupdater sendTrasmit = new GUIupdater(inQueue, display, label3);
        Thread thread1 = new Thread(sendTrasmit);
        thread1.start();

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
        lineGroup.getChildren().removeAll(lineGroup.getChildren());
        lineGroup.getChildren().add(canvas);
        lineGroup.getChildren().add(display);
        label3.setText("");
        label4.setText("");
    }

    public void send() {
        Image sendPic = getImage(lineGroup);
        Message sendMessage = new Message(yourNameText.getText(), sendPic, guessText.getText());
        if (sendPic != null) {
            while (!outQueue.put(sendMessage)) {
                Thread.currentThread().yield();
            }
        }
    }

    public void guess() {
        if (guessText.getText().equals(label3.getText())) {
            label4.setText("YAY");
        } else {
            label4.setText("NOO");
        }
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
            statusText.setText("Server start: getLocalHost failed. Exiting....");
        }

    }

    void setClientMode() {
        serverMode = false;
        beginGameButton.setText("Connect");
        // display the IP address for the local computer
        IPAddressText.setText("10.85.218.107");
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
            statusText.setText("Type a port number BEFORE connecting.");
            return;
        }

        // We're gonna start network connection!
        connected = true;
        beginGameButton.setDisable(true);

        if (serverMode) {

            // We're a server: create a thread for listening for connecting clients
            Connect connectToNewClients = new Connect(Integer.parseInt(portText.getText()), inQueue, outQueue, statusText, yourNameText);
            Thread connectThread = new Thread(connectToNewClients);
            connectThread.start();

        } else {

            // We're a client: connect to a server
            try {
                Socket socketClientSide = new Socket(IPAddressText.getText(), Integer.parseInt(portText.getText()));
                statusText.setText("Connected to server at IP address " + IPAddressText.getText() + " on port " + portText.getText());

                // The socketClientSide provides 2 separate streams for 2-way communication
                //   the InputStream is for communication FROM server TO client
                //   the OutputStream is for communication TO server FROM client
                // Create data reader and writer from those stream (NOTE: ObjectOutputStream MUST be created FIRST)

                // Every client prepares for communication with its server by creating 2 new threads:
                //   Thread 1: handles communication TO server FROM client
                CommunicationOut communicationOut = new CommunicationOut(socketClientSide, new ObjectOutputStream(socketClientSide.getOutputStream()), outQueue, statusText);
                Thread communicationOutThread = new Thread(communicationOut);
                communicationOutThread.start();

                //   Thread 2: handles communication FROM server TO client
                CommunicationIn communicationIn = new CommunicationIn(socketClientSide, new ObjectInputStream(socketClientSide.getInputStream()), inQueue, null, statusText, yourNameText);
                Thread communicationInThread = new Thread(communicationIn);
                communicationInThread.start();

            } catch (Exception ex) {
                ex.printStackTrace();
                statusText.setText("Client start: networking failed. Exiting....");
            }
            // We connected!
        }
    }

    public void setGuesserMode() {
        label1.setText("What is your guess?");
        label2.setText("Are you ready to guess?");
        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                guess();
            }
        });

    }

    public void setDrawerMode() {
        label1.setText("What did you draw?");
        label2.setText("Are you done drawing?");
    }

    Image getImage(Node node){
        WritableImage snapshot = node.snapshot(new SnapshotParameters(), null);
        BufferedImage buffImg = SwingFXUtils.fromFXImage(snapshot, null);
        Image image = SwingFXUtils.toFXImage(buffImg, null );
        return image;
    }
}