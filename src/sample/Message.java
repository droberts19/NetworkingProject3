package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Message implements Serializable {
    // Message includes both sender ID and Image being sent
    private String sender;

    private String text;

    // Image is transient means that we have to provide our own code to read/write object
    private transient Image data;

    private  int type;


    Message(String name, Image image, String guess, int tp) {
        sender = name;
        data = image;
        text = guess;
        type = tp;
    }

    String sender() {
        return sender;
    }

    Image data() {
        return data;
    }

    String text() {return text;}

    int type() {
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

    private void writeObject(ObjectOutputStream outStream) throws IOException, ClassNotFoundException {
        // this writes sender String with default code
        outStream.defaultWriteObject();
        // this writes data Image using this custom code
        ImageIO.write(SwingFXUtils.fromFXImage(data, null), "png", outStream);
    }
}