package ClientNetworking.ConnectionClient;

import ClientNetworking.ClientNetworkingHandler;
import ClientNetworking.ConnectionState;
import Constants.NetworkingIdentifiers;
import ServerNetworking.GameServer.ServerHandler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class ClientConnectionInput extends Thread{

    private boolean isRunning;

    //Data
    private final int BUFFER_LENGTH = 256;
    private byte[] buffer = new byte[BUFFER_LENGTH];

   //Connection socket
   private DatagramSocket connectionSocket;
   private DatagramPacket dgram;

   public ClientConnectionInput(){
       try{
           dgram = new DatagramPacket(buffer, buffer.length);
           connectionSocket = new DatagramSocket(ServerHandler.clientPort);

       }catch (IOException e){e.printStackTrace();}
   }

    @Override public void run(){

        isRunning = true;
        while(isRunning) {
            try {
                if (ClientNetworkingHandler.getState() == ConnectionState.CONNECTING) {
                    connectionSocket.receive(dgram);
                    byte[] compoundData = dgram.getData();

                    byte[] identifier = Arrays.copyOfRange(compoundData, 0, NetworkingIdentifiers.IDENTIFIER_LENGTH);
                    byte[] packetData = Arrays.copyOfRange(compoundData,
                            NetworkingIdentifiers.IDENTIFIER_LENGTH, compoundData.length);

                    //UNFINISHED, MORE

                    if(Arrays.equals(identifier, NetworkingIdentifiers.ACCEPT_IDENTIFIER)){
                        handleConnectionConfirm(packetData);
                    }
                    else if(Arrays.equals(identifier, NetworkingIdentifiers.REJECTION_IDENTIFIER)){
                        handleConnectionReject(packetData);
                    }

                } else {
                    System.out.println("CLIENT: Connection packet recieved outside of connection-state");
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    private void handleConnectionConfirm(byte[] packetData){
       ClientNetworkingHandler.setState(ConnectionState.CONNECTED);
       String message = new String(packetData);
       System.out.println("Confirm Message: " + message);
    }

    private void handleConnectionReject(byte[] packetData){
       ClientNetworkingHandler.setState(ConnectionState.DISCONNECTED);
       String message = new String(packetData);
       System.out.println("Reject Message: " + message);
    }

    public void close(){
        isRunning = false;
        connectionSocket.close();
    }
}
