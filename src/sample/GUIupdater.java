package sample;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class GUIupdater implements Runnable {
    private SyncData syncData;
    private ImageView display;
    private Label whatIsTheDrawing;
    private Label player;
    private ArrayList<String> list;
    private Label turn;

    GUIupdater(SyncData sd, ImageView iv, Label lb, Label pl, ArrayList<String> al, Label tr) {
        syncData = sd;
        display = iv;
        whatIsTheDrawing = lb;
        player = pl;
        list = al;
        turn = tr;
    }

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
                    player.setText("Player: " + finalMessage.sender());
                    list.add(finalMessage.sender());
                }
                if (finalMessage.type() == 2) { //drawing
                    display.setImage(null);
                    display.setImage(finalMessage.data());
                    whatIsTheDrawing.setText(finalMessage.text());
                }
                if (finalMessage.type() == 3) { //guess
                    turn.setText("");
                }
            });
            }
    }
}