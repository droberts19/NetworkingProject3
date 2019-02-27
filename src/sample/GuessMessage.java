package sample;

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
}