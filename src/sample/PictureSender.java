package sample;

import javafx.application.Platform;

public class  PictureSender implements Runnable {
    Controller myController;

    PictureSender(Controller c) {
        myController = c;
    }

    public void run() {
        while (!Thread.interrupted()) {
            if (!myController.guesser) {
                Platform.runLater(() -> {
                    myController.sendPicture();
                });
                try {
                    Thread.sleep(1000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
