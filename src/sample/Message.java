package sample;

import javafx.scene.image.Image;

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




    // Message includes both sender ID and Image being sent
    private String sendeR;
    // the guess
    private String guesS;


    // Message includes both sender ID and Image being sent
    private String sender;
    // Image is transient means that we have to provide our own code to read/write object
    private transient Image data;
    // the guess
    private String guess;

    // Message includes both sender ID and Image being sent
    private String sender;
    // Image is transient means that we have to provide our own code to read/write object
    private String text;




}