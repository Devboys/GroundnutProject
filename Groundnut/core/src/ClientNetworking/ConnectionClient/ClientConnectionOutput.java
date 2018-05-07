package ClientNetworking.ConnectionClient;

import ClientNetworking.ClientNetworkingHandler;
import ClientNetworking.ConnectionState;
import Constants.NetworkingIdentifiers;
import ServerNetworking.GameServer.ServerHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**Sends connection-requests to the server in intervals provided by ServerHandler.clientTickRate. */
public class ClientConnectionOutput extends Thread {

    //Socket
    private DatagramSocket udpSocket;
    private InetAddress serverIP;
    private int serverPort;

    //timeout
    private int numTimeoutChecks;
    private static final int maxTimeoutChecks = 10;

    private boolean isRunning;

    ClientConnectionOutput(InetAddress hostIP){
        try {
            serverIP = hostIP;
            serverPort = ServerHandler.serverPort;

            udpSocket = new DatagramSocket();
            udpSocket.connect(serverIP, serverPort);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override public void run(){

        isRunning = true;
        while(isRunning){
            try {
                if (ClientNetworkingHandler.getState() == ConnectionState.CONNECTING) {
                    if (checkTimeout()) {
                        sendConnectionRequest();
                    }
                } else {
                    System.out.println("CLIENT: connection handler started in incorrect state");
                    close();
                }
            }catch (IOException e){
                e.printStackTrace();
                close();
            }

            try{
                Thread.sleep(ServerHandler.clientTickRate);
            }catch (InterruptedException e){
                close();
                e.printStackTrace();
            }
        }
    }

    /**
     * @return A boolean describing whether the connection-request has timed out. True - yes, False - no.*/
    private boolean checkTimeout(){
        if(numTimeoutChecks == 0) {
            numTimeoutChecks++;
            return true;
        }
        else numTimeoutChecks++;

        if(numTimeoutChecks > maxTimeoutChecks){
            numTimeoutChecks = 0;
        }
        return false;
    }

    private void sendConnectionRequest() throws IOException{
        String connectionRequestMessage = "Connection request";

        //Compound the data with a connection-request identifier, such that the byte array will be [identifier][data]
        ByteArrayOutputStream compoundingStream = new ByteArrayOutputStream();
        compoundingStream.write(NetworkingIdentifiers.CONNECT_REQUEST_IDENTIFIER);
        compoundingStream.write(connectionRequestMessage.getBytes());

        //send the compounded output to the server.
        byte[] compoundOutput = compoundingStream.toByteArray();
        udpSocket.send(new DatagramPacket(compoundOutput, compoundOutput.length, serverIP, serverPort));

        System.out.println("connection request sent");
    }

    /**Closes the output-socket and stops the thread. */
    public void close(){
        isRunning = false;
        udpSocket.close();
    }
}
