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

public class ClientConnectionOutput extends Thread {

    //Socket
    private DatagramSocket udpSocket;
    private InetAddress serverIP;
    private int serverPort;

    //timeout
    private int numTimeoutChecks;
    private static final int maxTimeoutChecks = 10;

    private boolean isRunning;

    public ClientConnectionOutput(InetAddress hostIP){
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

        ByteArrayOutputStream compoundingStream = new ByteArrayOutputStream();
        compoundingStream.write(NetworkingIdentifiers.CONNECT_REQUEST_IDENTIFIER);
        compoundingStream.write(connectionRequestMessage.getBytes());

        byte[] compoundOutput = compoundingStream.toByteArray();
        udpSocket.send(new DatagramPacket(compoundOutput, compoundOutput.length, serverIP, serverPort));

        System.out.println("connection request sent");
    }

    public void close(){
        isRunning = false;
        udpSocket.close();
    }
}
