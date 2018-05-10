package ClientNetworking.ConnectionClient;

import ClientNetworking.ClientNetworkingHandler;
import ClientNetworking.ConnectionState;
import Constants.NetworkingIdentifiers;
import Core.SimulationHandler;
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
    private int serverPort;

    //timeout
    private int numTimeoutChecks;
    private static final int maxTimeoutChecks = 10;

    private boolean isRunning;

    private ClientNetworkingHandler parentHandler;

    ClientConnectionOutput(ClientNetworkingHandler parent){
        this.parentHandler = parent;

        try {
            serverPort = ServerHandler.serverPort;

            udpSocket = new DatagramSocket();
            udpSocket.connect(parent.getHostIP(), serverPort);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

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
        String connectionRequestMessage = "" + SimulationHandler.getInstance().getClientID();

        //Compound the data with a connection-request identifier, such that the byte array will be [identifier][data]
        ByteArrayOutputStream compoundingStream = new ByteArrayOutputStream();
        compoundingStream.write(NetworkingIdentifiers.CONNECT_REQUEST_IDENTIFIER);
        compoundingStream.write(connectionRequestMessage.getBytes());

        //send the compounded output to the server.
        byte[] compoundOutput = compoundingStream.toByteArray();
        udpSocket.send(
                new DatagramPacket(compoundOutput, compoundOutput.length, parentHandler.getHostIP(), serverPort)
        );

        System.out.println("Connect-Request sent to:" + parentHandler.getHostIP() + "(" + serverPort + ")");
    }

    /**Closes the output-socket and stops the thread. */
    public void close(){
        isRunning = false;
        udpSocket.close();
    }
}
