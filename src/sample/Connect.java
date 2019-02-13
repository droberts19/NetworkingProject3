package sample;

import javafx.application.Platform;
import javafx.scene.control.TextField;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Connect {
    private int connectionPort;
    private ServerSocket connectionSocket;
    private SynchronizedQueue inTheQueue;
    private SynchronizedQueue outQueue;
    private ArrayList<ObjectOutputStream> clientOutputStreams;
    private TextField statusText;
    private TextField yourNameText;

    Connect(int port, SynchronizedQueue inQ, SynchronizedQueue outQ, TextField status, TextField name) {
        connectionPort = port;
        inTheQueue = inQ;
        outQueue = outQ;
        statusText = status;
        yourNameText = name;
        if (MainGuesser.multicastMode) {
            clientOutputStreams = new ArrayList<ObjectOutputStream>();
        }
    }

    public void run() {
        Thread.currentThread().setName("ConnectToNewClients Thread");
        System.out.println("ConnectToNewClients thread running");

        try {

            Platform.runLater(() -> statusText.setText("Listening on port " + connectionPort));
            connectionSocket = new ServerSocket(connectionPort);

            while (Controller.connected && !Thread.interrupted()) {
                // Wait until a client tries to connect
                Socket socketServerSide = connectionSocket.accept();
                Platform.runLater(() -> statusText.setText("Client has connected!"));
                ObjectOutputStream dataWriter = new ObjectOutputStream(socketServerSide.getOutputStream());
                ObjectInputStream dataReader = new ObjectInputStream(socketServerSide.getInputStream());
                CommunicationOut communicationOut;
                if (MainGuesser.multicastMode) {
                    clientOutputStreams.add(dataWriter);
                    communicationOut = new CommunicationOut(socketServerSide, clientOutputStreams, outQueue, statusText);
                } else {
                    communicationOut = new CommunicationOut(socketServerSide, dataWriter, outQueue, statusText);
            }
                Thread communicationOutThread = new Thread(communicationOut);
                communicationOutThread.start();

                //   Thread 2: handles communication FROM that client TO server
                CommunicationIn communicationIn = new CommunicationIn (socketServerSide, dataReader, inTheQueue, outQueue, statusText, yourNameText);
                Thread communicationInThread = new Thread(communicationIn);
                communicationInThread.start();
        }
            connectionSocket.close();
            System.out.println("ConnectToNewClients thread ended; connectionSocket closed.");

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Server ConnectToNewClients: networking failed.  Exiting...");
        }
    }
}
    }
}