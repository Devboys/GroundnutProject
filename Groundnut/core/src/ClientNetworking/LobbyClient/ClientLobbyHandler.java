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

//TODO: FIX SOCKET CLOSE ON TCP CONNECTION WITH LOBBY (SCANNER TOO IN INPUTWRITER).

public class ClientLobbyHandler implements NetworkingHandler {

    private static final String LOBBY_HOST_IP = "localhost";
    private static final int portNum = 1102;

    private ClientLobbyListener clientIn;
    private UserInputWriter clientOut;
    private Socket socket;

    public ClientLobbyHandler(ClientNetworkingHandler parent){
        try {
            //establish socket connection with lobby
            socket = new Socket(LOBBY_HOST_IP, portNum);

            //wrap input- and output-streams in a reader and a writer.
            PrintWriter outputWriter = new PrintWriter(socket.getOutputStream());
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            clientIn = new ClientLobbyListener(inputReader, parent);
            clientIn.start();

            clientOut = new UserInputWriter(outputWriter);
            clientOut.start();

        }catch (ConnectException e){
            System.out.println("Lobby not available");
            parent.setState(ConnectionState.DISCONNECTED);
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
