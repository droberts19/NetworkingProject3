package sample;

public class PictureSender extends Runnable {
    Controller myController;

    PictureSender(Controller c) {
        myController = c;
    }

    public void run() {
        while (!Thread.interrupted()) {
            myController.sendPicture();
            try {
                Thread.sleep(500);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
