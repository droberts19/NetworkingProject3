package sample;

<<<<<<< Updated upstream
=======
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
>>>>>>> Stashed changes
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

// Serializable means that objects of this class can be read/written over ObjectStreams
public class GuessMessage implements Serializable {
    // Message includes both sender ID and Image being sent
    private String sender;
    // the guess
    private String guess;

    GuessMessage(String who, String when) {
        sender = who;
        guess = when;
    }

    String sender() {
        return sender;
    }

    String guess() {
        return guess;
    }

    public String toString() {
        return "\" from: " + sender + "\" with guess: " + guess;
    }

    private void readObject(ObjectInputStream inStream) throws IOException, ClassNotFoundException {
        // this reads sender String with default code
        inStream.defaultReadObject();
        // this reads data Image using this custom code

    }

    private void writeObject(ObjectOutputStream outStream) throws IOException {
        // this writes sender String with default code
        outStream.defaultWriteObject();
        // this writes data Image using this custom code
    }


}