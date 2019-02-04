package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Threadz implements Runnable {
    private SyncData syncData;
    private ImageView display;

    Threadz(SyncData sd, ImageView iv) {
        syncData = sd;
        display = iv;
    }

    public void run() {
        while (!Thread.interrupted()) {
            Image next = (Image)syncData.get();
            while (next == null) {
                Thread.currentThread().yield();
                next = (Image)syncData.get();
            }
            display.setImage(next);
        }
    }
}
