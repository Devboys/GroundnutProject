package ServerNetworking.LobbyServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class LobbyHandler {

    //TODO: FIND A WAY TO  CLOSE THE LOBBY THREADS ELEGANTLY

    private static final int serverPort = 1102;
    private static ServerSocket serverSocket;

    private boolean isRunning;

    public LobbyHandler() {
        Socket newPlayerSocket;

        LobbyWriter serverWriter = new LobbyWriter();
        serverWriter.start();

        try {
            serverSocket = new ServerSocket(serverPort);

        }catch (IOException e) {
            System.out.println("Error creating lobby");
            System.exit(0);
            e.printStackTrace();
        }

        isRunning = true;
        while (isRunning) {
            try {
                newPlayerSocket = serverSocket.accept();
                serverWriter.addNewPlayer(newPlayerSocket);

                System.out.println("socket accepted");

            } catch (IOException e) {
                System.out.println("Error accepting socket");
                System.exit(0);
                e.printStackTrace();
            }
        }
    }
}