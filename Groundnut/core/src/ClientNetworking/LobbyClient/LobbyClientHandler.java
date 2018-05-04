package ClientNetworking.LobbyClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LobbyClientHandler {

    private static final String hostIP = "localhost";
    private static final int portNum = 1102;

    private LobbyClientListener clientIn;
    private UserInputWriter clientOut;

    public LobbyClientHandler(){
        try {
            Socket socket = new Socket(hostIP, portNum);
            PrintWriter outputWriter = new PrintWriter(socket.getOutputStream());
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            clientIn = new LobbyClientListener(inputReader);
            clientIn.start();

            clientOut = new UserInputWriter(outputWriter);
            clientOut.start();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void close(){
        clientIn.close();
        clientOut.close();
    }

}
