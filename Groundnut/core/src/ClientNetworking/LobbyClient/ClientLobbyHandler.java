package ClientNetworking.LobbyClient;

import ClientNetworking.ClientNetworkingHandler;
import ClientNetworking.ConnectionState;
import ClientNetworking.NetworkingHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

public class ClientLobbyHandler implements NetworkingHandler {

    private static final String hostIP = "localhost";
    private static final int portNum = 1102;

    private ClientLobbyListener clientIn;
    private UserInputWriter clientOut;
    private Socket socket;

    public ClientLobbyHandler(){
        try {
            socket = new Socket(hostIP, portNum);
            PrintWriter outputWriter = new PrintWriter(socket.getOutputStream());
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            clientIn = new ClientLobbyListener(inputReader);
            clientIn.start();

            clientOut = new UserInputWriter(outputWriter);
            clientOut.start();

        }catch (ConnectException e){
            System.out.println("Lobby not available");
            ClientNetworkingHandler.setState(ConnectionState.DISCONNECTED);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override public void close(){
//        try {
//            socket.close();
//        }catch (IOException e){
//            System.out.println("Something went wrong when closing socket in ClientLobbyHandler.");
//        }

        clientIn.close();
        clientOut.close();
    }

}
