package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

// Serializable means that objects of this class can be read/written over ObjectStreams
public class Message implements Serializable {
    // Message includes both sender ID and Image being sent
    private String sender;
    // Image is transient means that we have to provide our own code to read/write object
    private transient Image data;
    // the guess
    private String guess;

    Message(String who, Image what, String when) {
        sender = who;
        data = what;
        guess = when;
    }

    public String toString() {
        return "\"" + data + "\" from: " + sender;
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