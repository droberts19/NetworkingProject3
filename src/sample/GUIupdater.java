package sample;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class GUIupdater implements Runnable {
    private SyncData syncData;
    private ImageView display;
    private Label whatIsTheDrawing;

    GUIupdater(SyncData sd, ImageView iv, Label lb) {
        syncData = sd;
        display = iv;
        whatIsTheDrawing = lb;
    }

    public void run() {
        while (!Thread.interrupted()) {
            Message next = (Message) syncData.get();
            while (next == null) {
                Thread.currentThread().yield();
                next = (Message) syncData.get();
            }
            display.setImage(next.data());
            whatIsTheDrawing.setText("Drawing: " + next.guess());
        }
    }
}