package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Message {
    // Message includes both sender ID and Image being sent
    private String sender;

    private String text;

    // Image is transient means that we have to provide our own code to read/write object
    private transient Image data;

    // There are 3 different message types:
    // if type == 1, this is a guess message, so text is the guess attempt
    // if type == 2, this is a image message, so text is the guess answer and data actually contains an image
    // if type == 3, this is a communication message, so text is the communication text
    private int type;

    Message(String who, Image what, String when, int why) {
        sender = who;
        data = what;
        text = when;
        type = why;
    }

    String sender() {
        return sender;
    }

    Image data() {
        return data;
    }

    String text() {return text;}

    Integer type() {
        return type;
    }

    public String toString() {
        return "\"" + data + "\" from: " + sender + "\" with guess: " + text;
    }

    private void readObject(ObjectInputStream inStream) throws IOException, ClassNotFoundException {
        // this reads sender String with default code
        inStream.defaultReadObject();
        // this reads data Image using this custom code
        data = SwingFXUtils.toFXImage(ImageIO.read(inStream), null);
    }

    private void writeObject(ObjectOutputStream outStream) throws IOException {
        // this writes sender String with default code
        outStream.defaultWriteObject();
        // this writes data Image using this custom code
        ImageIO.write(SwingFXUtils.fromFXImage(data, null), "png", outStream);
    }
}