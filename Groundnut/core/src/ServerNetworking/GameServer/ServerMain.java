package ServerNetworking.GameServer;

import java.io.IOException;

public class ServerMain {

    public static void main(String[] args){
        ServerHandler serverHandler = new ServerHandler();

        ServerInputThread serverInput = new ServerInputThread();
        ServerOutputThread serverOutput = null;
        try {
            serverOutput = new ServerOutputThread();
        } catch (IOException e) {
            e.printStackTrace();
        }

        serverInput.start();
        serverOutput.start();
    }
}