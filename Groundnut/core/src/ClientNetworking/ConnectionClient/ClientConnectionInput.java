package ClientNetworking.ConnectionClient;

import ClientNetworking.ClientNetworkingManager;
import ClientNetworking.ConnectionState;
import Constants.NetworkingIdentifiers;
import ServerNetworking.GameServer.ServerHandler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

/**Recieves and handles connection confirms and rejections from the server.*/
public class ClientConnectionInput extends Thread{

    private boolean isRunning;

    //Data
    private final int BUFFER_LENGTH = 256;
    private byte[] buffer = new byte[BUFFER_LENGTH];

   //Connection socket
   private DatagramSocket connectionSocket;
   private DatagramPacket dgram;

   private ClientNetworkingManager parentHandler;

   ClientConnectionInput(ClientNetworkingManager parent){
       this.parentHandler = parent;

       try{
           dgram = new DatagramPacket(buffer, buffer.length);
           connectionSocket = new DatagramSocket(ServerHandler.clientPort);
           //connectionSocket.setSoTimeout(1000);

       }catch (IOException e){e.printStackTrace();}
   }

    /**Receieves and handles connection messages from server. Packets are identified by their identifies.
     * See handleConnectionConfirm() and handleConnectionReject() for specific handling.*/
    @Override public void run(){
        isRunning = true;
        while(isRunning) {
            try {
                if (parentHandler.getState() == ConnectionState.CONNECTING) { //must be in correct state.
                    //Recieve a packet and get its data.
                    connectionSocket.receive(dgram);
                    byte[] compoundData = dgram.getData();

                    //separate identifier from packet-data
                    byte[] identifier = Arrays.copyOfRange(compoundData, 0,
                            NetworkingIdentifiers.IDENTIFIER_LENGTH);
                    byte[] packetData = Arrays.copyOfRange(compoundData,
                            NetworkingIdentifiers.IDENTIFIER_LENGTH, compoundData.length);

                    //check if packet is a connect-confirm or connect-rejection (or something else).
                    if(Arrays.equals(identifier, NetworkingIdentifiers.ACCEPT_IDENTIFIER)){
                        handleConnectionConfirm(packetData);
                    }
                    else if(Arrays.equals(identifier, NetworkingIdentifiers.REJECTION_IDENTIFIER)){
                        handleConnectionReject(packetData);
                    }

                } else {
                    System.out.println("CLIENT: Connection packet recieved outside of connection-state");
                    parentHandler.setState(ConnectionState.DISCONNECTED);
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    /**Used by run() to handle connection-confirms from the server.
     * Requests the networking-manager to switch to CONNECTED
     * @param packetData the data of the received packet */
    private void handleConnectionConfirm(byte[] packetData){
       parentHandler.setState(ConnectionState.CONNECTED);
       String message = new String(packetData);

       System.out.println("Confirm Message: " + message);
    }

    /**Used by run() to handle connection-rejections from the server.
     * Requests the networking-manager to switch to DISCONNECTED.
     * @param packetData the data of the received packet*/
    private void handleConnectionReject(byte[] packetData){
       parentHandler.setState(ConnectionState.DISCONNECTED);
       String message = new String(packetData);

       System.out.println("Reject Message: " + message);
    }

    /**Closes the input-socket and stops the thread by allowing run() to exit. */
    public void close(){
        isRunning = false;
        connectionSocket.close();
    }
}
