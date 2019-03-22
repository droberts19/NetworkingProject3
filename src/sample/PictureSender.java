package sample;

import javafx.application.Platform;

public class  PictureSender implements Runnable {
    Controller myController;

    PictureSender(Controller c) {
        myController = c;
    }

    public void run() {
        while (!Thread.interrupted()) {
            if (myController.guesser == false) {
                Platform.runLater(() -> {
                    myController.sendPicture();
                });
                try {
                    Thread.sleep(500);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
