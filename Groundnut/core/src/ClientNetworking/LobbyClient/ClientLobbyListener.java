package ClientNetworking.LobbyClient;

import ClientNetworking.ClientNetworkingHandler;
import ClientNetworking.ConnectionState;
import Core.SimulationHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientLobbyListener extends Thread {

    private boolean isRunning;

    private BufferedReader inputReader; //reads input from server through socket in handler.

    private ClientNetworkingHandler parentHandler;

    public ClientLobbyListener(BufferedReader inputReader, ClientNetworkingHandler parent){
        parentHandler = parent;

        this.inputReader = inputReader;
    }

    public void run(){
        System.out.println("Client thread running");
        isRunning = true;

        while(isRunning){
            try {
                String serverInput = inputReader.readLine();
                handleInput(serverInput);

            } catch (IOException e) {
                System.out.println("Killing Thread");
                close();
                e.printStackTrace();
            }
        }
    }

    private void handleInput(String input) throws UnknownHostException{
        if(input.startsWith("#")){
            String command = input.substring(1);

            if(command.startsWith("S")){
                //TODO: WHAT THIS IS SUPPOSED TO DO.
            }

            else if(command.startsWith("C")){
                String[] splitInput = command.split(" ");
                System.out.println("Received Host IP: " + splitInput[1]);

                InetAddress serverHostAddress = InetAddress.getByName(splitInput[1]);

                //inform local player of his ID on the serverSide;
                int playerID = Integer.parseInt(splitInput[2]);
                SimulationHandler.getInstance().setClientID(playerID);

                //When hosts starts the game, provide the client with the hosts IP and begin connection with server.
                parentHandler.setHostIP(serverHostAddress);
                parentHandler.setState(ConnectionState.CONNECTING);
            }
        }
        else{
            System.out.println(input);
        }
    }

    /**Closes the thread.*/
    public void close(){
        isRunning = false;
    }
}
