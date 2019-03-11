package sample;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class GUIupdater implements Runnable {
    private SyncData syncData;
    private ImageView display;
    private Label whatIsTheDrawing;
    private ListView<String> player;
    private ArrayList<String> list;
    private Label turn;
    private Label label1;
    private Label label2;
    private Button send;

    GUIupdater(SyncData sd, ImageView iv, Label lb, ListView<String> pl, ArrayList<String> al, Label tr, Label l1, Label l2, Button sn) {
        syncData = sd;
        display = iv;
        whatIsTheDrawing = lb;
        player = pl;
        list = al;
        turn = tr;
        label1 = l1;
        label2 = l2;
        send = sn;
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
                    player.getItems().add(finalMessage.sender());
                }else{
                    System.out.println("Identification did not work");
                }
                if (finalMessage.type() == 2) { //drawing
                    display.setImage(null);
                    display.setImage(finalMessage.data());
                    whatIsTheDrawing.setText(finalMessage.text());
                }else{
                    System.out.println("drawing did not work");
                }
                if (finalMessage.type() == 3) { //guess
                    turn.setText("Player got it right");
                }else{
                    System.out.println("guess did not work");
                }
                if (finalMessage.type() == 4) { //drawer
                    label1.setText("What did you draw?");
                    label2.setText("Are you done drawing?");
                    send.setText("Send");
                }else{
                    System.out.println("drawer did not work");
                }
                if (finalMessage.type() == 5) { //guesser
                    label1.setText("What is your guess?");
                    label2.setText("Are you ready to guess?");
                    send.setText("Guess");
                }else{
                    System.out.println("guesser did not work");
                }
            });
            }
    }
}