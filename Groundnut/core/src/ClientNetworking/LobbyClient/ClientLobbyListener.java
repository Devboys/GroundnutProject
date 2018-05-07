package ClientNetworking.LobbyClient;

import ClientNetworking.ClientNetworkingHandler;
import ClientNetworking.ConnectionState;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientLobbyListener extends Thread {

    private boolean isRunning;
    private BufferedReader inputReader; //reads input from server.

    public ClientLobbyListener(BufferedReader inputReader){
        this.inputReader = inputReader;
    }

    public void run(){
        System.out.println("client thread running");
        isRunning = true;

        while(isRunning){
            try {
                String serverInput = inputReader.readLine();
                handleInput(serverInput);

            } catch (IOException e) {
                System.out.println("killing Thread");
                close();
                e.printStackTrace();
            }
        }
    }

    private void handleInput(String input) throws UnknownHostException{
        if(input.startsWith("#")){
            String command = input.substring(1);

            if(command.startsWith("S")){
                //do stuff here
            }

            else if(command.startsWith("C")){
                String[] splitInput = command.split(" ");
                InetAddress serverHostAddress = InetAddress.getByName(splitInput[1]);

                int playerID = Integer.parseInt(splitInput[2]);

                ClientNetworkingHandler.setHostIP(serverHostAddress);
                ClientNetworkingHandler.setState(ConnectionState.CONNECTING);
            }
        }
        else{
            System.out.println(input);
        }
    }

    public void close(){
        isRunning = false;
    }
}