package ClientNetworking.ConnectionClient;

import ClientNetworking.ClientNetworkingManager;
import ClientNetworking.ConnectionState;
import Constants.NetworkingIdentifiers;
import Core.SimulationHandler;
import ServerNetworking.GameServer.ServerHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;

/**Sends connection-requests to the server in intervals provided by ServerHandler.clientTickRate. */
public class ClientConnectionOutput extends Thread {

    //Socket
    private DatagramSocket udpSocket;
    private int serverPort;

    //timeout
    private int numTimeoutChecks;
    private static final int maxTimeoutChecks = 10;

    private boolean isRunning;

    private ClientNetworkingManager parentHandler;

    ClientConnectionOutput(ClientNetworkingManager parent){
        this.parentHandler = parent;

        try {
            serverPort = ServerHandler.serverPort;

            udpSocket = new DatagramSocket();
            udpSocket.connect(parent.getHostIP(), serverPort);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**Continuously sends connection-requests to the server.*/
    @Override public void run(){
        isRunning = true;
        while(isRunning){
            try {
                if (parentHandler.getState() == ConnectionState.CONNECTING) {
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

    /**@return whether the connection-request has timed out.*/
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

    /**Used by run() to send a connection-request to the server IP-address as defined in the networking-manager.
     * @throws IOException when identifier-adding process fails*/
    private void sendConnectionRequest() throws IOException{
        byte[] playerIDBytes = ByteBuffer.allocate(4).putInt(SimulationHandler.getInstance().getClientID()).array();

        //Compound the data with a connection-request identifier, such that the byte array will be [identifier][data]
        ByteArrayOutputStream compoundingStream = new ByteArrayOutputStream();
        compoundingStream.write(NetworkingIdentifiers.CONNECT_REQUEST_IDENTIFIER);
        compoundingStream.write(playerIDBytes);

        //send the compounded output to the server.
        byte[] compoundOutput = compoundingStream.toByteArray();
        udpSocket.send(
                new DatagramPacket(compoundOutput, compoundOutput.length, parentHandler.getHostIP(), serverPort)
        );

        System.out.println("Connect-Request sent to:" + parentHandler.getHostIP() + "(" + serverPort + ")");
    }

    /**Closes the output-socket and stops the thread by allowing run() to exit.*/
    public void close(){
        isRunning = false;
        udpSocket.close();
    }
}
