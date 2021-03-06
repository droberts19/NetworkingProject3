package sample;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;

public class GUIupdater implements Runnable {
    private SyncData syncData;
    private ImageView display;
    private Label whatIsTheDrawing;
    private ListView<String> player;
    private ArrayList<String> list;
    private Label turn;
    private Label label1;
    private Group lineGroup;
    private Rectangle canvas;
    private TextField guessText;
    private Controller myController;

    GUIupdater(SyncData sd, Controller controller, ImageView iv, Label lb, ListView<String> pl, ArrayList<String> al, Label tr, Label l1, TextField tx, Group gp, Rectangle rc) {
        syncData = sd;
        display = iv;
        whatIsTheDrawing = lb;
        player = pl;
        list = al;
        turn = tr;
        label1 = l1;
        guessText = tx;
        lineGroup = gp;
        canvas = rc;
        myController = controller;
    }
//Get rid of guess button
    //A thread that sends the draw as a message
        //Get rid of send button
            //Put variables into a for loop so that it continously sends drawing as a message



    public void run() {
        while (!Thread.interrupted()) {
            Message next = (Message) syncData.get();
            while (next == null) {
                Thread.currentThread().yield();
                next = (Message) syncData.get();
            }

            Message finalMessage = next;
            Platform.runLater(() -> {
                if (finalMessage.type() == 1) { //identification
                    System.out.println("Identification DID work");
                    player.getItems().add(finalMessage.sender());
                    list.add(finalMessage.sender());
                }
                if (finalMessage.type() == 2) { //drawing
                    System.out.println("drawing DID work");
                    display.setImage(null);
                    display.setImage(finalMessage.data());
                    whatIsTheDrawing.setText(finalMessage.text());
                    myController.ableSendButton();
                }
                if (finalMessage.type() == 3) { //guess
                    System.out.println("guess DID work");
                    turn.setText(finalMessage.sender() + " got it right!");
                    if (player.getItems().contains(finalMessage.sender())) {
                        player.getItems().remove(finalMessage.sender());
                        player.getItems().add(finalMessage.sender() + ": 1");
                        return;
                    }
                    if (player.getItems().contains(finalMessage.sender() + ": 1")) {
                        player.getItems().remove(finalMessage.sender() + ": 1");
                        player.getItems().add(finalMessage.sender() + ": 2");
                        return;
                    }
                    if (player.getItems().contains(finalMessage.sender() + ": 2")) {
                        player.getItems().remove(finalMessage.sender() + ": 2");
                        player.getItems().add(finalMessage.sender() + ": 3");
                        return;
                    }
                    if (player.getItems().contains(finalMessage.sender() + ": 3")) {
                        player.getItems().remove(finalMessage.sender() + ": 3");
                        player.getItems().add(finalMessage.sender() + ": 4");
                        return;
                    }
                    if (player.getItems().contains(finalMessage.sender() + ": 4")) {
                        player.getItems().remove(finalMessage.sender() + ": 4");
                        player.getItems().add(finalMessage.sender() + ": 5");
                        return;
                    }
                    if (player.getItems().contains(finalMessage.sender() + ": 5")) {
                        player.getItems().remove(finalMessage.sender() + ": 5");
                        player.getItems().add(finalMessage.sender() + ": 6");
                        return;
                    }
                    if (player.getItems().contains(finalMessage.sender() + ": 6")) {
                        player.getItems().remove(finalMessage.sender() + ": 6");
                        player.getItems().add(finalMessage.sender() + ": 7");
                        return;
                    }
                    if (player.getItems().contains(finalMessage.sender() + ": 7")) {
                        player.getItems().remove(finalMessage.sender() + ": 7");
                        player.getItems().add(finalMessage.sender() + ": 8");
                        return;
                    }
                    if (player.getItems().contains(finalMessage.sender() + ": 8")) {
                        player.getItems().remove(finalMessage.sender() + ": 8");
                        player.getItems().add(finalMessage.sender() + ": 9");
                        return;
                    }
                    if (player.getItems().contains(finalMessage.sender() + ": 9")) {
                        player.getItems().remove(finalMessage.sender() + ": 9");
                        player.getItems().add(finalMessage.sender() + ": 10 and wins");
                        return;
                    }
                }
                if (finalMessage.type() == 4) { //drawer
                    System.out.println("drawer DID work");
                    whatIsTheDrawing.setText("");
                    display.setImage(null);
                    lineGroup.getChildren().removeAll(lineGroup.getChildren());
                    lineGroup.getChildren().add(display);
                    lineGroup.getChildren().add(canvas);
                    guessText.setText("");
                    label1.setText("What are you drawing?");
                    myController.setDrawerMode();
                }
                if (finalMessage.type() == 5) { //guesser
                    System.out.println("guesser DID work");
                    whatIsTheDrawing.setText("");
                    display.setImage(null);
                    lineGroup.getChildren().removeAll(lineGroup.getChildren());
                    lineGroup.getChildren().add(display);
                    lineGroup.getChildren().add(canvas);
                    guessText.setText("");
                    label1.setText("What is your guess?");
                    myController.setGuesserMode();
                    myController.disableDrawButton();
                    turn.setText(finalMessage.sender() + " is drawing");
                }
            });
        }
    }
}