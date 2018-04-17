package ServerNetworking.GameServer;

import java.io.IOException;

public class ServerMain {

    public static void main(String[] args){
        ServerHandler serverHandler = new ServerHandler();

        ServerOutputThread serverOutput = null;
        try {
            serverOutput = new ServerOutputThread();
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverOutput.start();

        ServerInputThread serverInput = new ServerInputThread();
        serverInput.start();
    }
}