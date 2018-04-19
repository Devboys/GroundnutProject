package ServerNetworking.GameServer;

import Input.PlayerInput;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;


public class ServerInputThread extends Thread {

    //Data
    private final int BUFFER_LENGTH = 256;
    private byte[] buffer = new byte[BUFFER_LENGTH];

    //Socket
    private DatagramSocket udpSocket;
    private DatagramPacket dgram;

    private int serverPort;

    public ServerInputThread(){
        try {
            //Socket
            serverPort = ServerHandler.getGamePort();
            dgram = new DatagramPacket(buffer, buffer.length);
            udpSocket = new DatagramSocket(serverPort, InetAddress.getByName("0.0.0.0"));

            System.out.println("SERVER Created socket on LOCALHOST port: " + serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkDatagram(DatagramPacket dgram, PlayerInput playerInput){
        InetAddress dgramAddress = dgram.getAddress();
        ArrayList<PlayerSocket> playerList = ServerHandler.getPlayerList();

        boolean playerIsKnown = false;
        for(int i = 0; i < playerList.size(); i++){
            PlayerSocket currPlayerSocket = playerList.get(i);

            if(currPlayerSocket.getPlayerIP().equals(dgramAddress)){
                currPlayerSocket.setInputSource(playerInput);
                playerIsKnown = false;
            }
        }
        if(!playerIsKnown && ServerHandler.getNumConnectedPlayers() < ServerHandler.getMaxPlayerCount()){
            PlayerSocket pSocket = new PlayerSocket(dgramAddress, playerList.size());
            pSocket.setInputSource(playerInput);
            ServerHandler.addPlayer(pSocket);
        }
    }


    @Override public void run() {
        while (true) {
            //System.out.println("SERVER Waiting for datagram to be received...");
            try {
                udpSocket.receive(dgram);
                //System.out.println("SERVER Datagram received");
                byte[] data = dgram.getData();
                ByteArrayInputStream bais = new ByteArrayInputStream(data);
                ObjectInputStream ois = new ObjectInputStream(bais);
                try{
                    PlayerInput playerInput = (PlayerInput) ois.readObject();
                    checkDatagram(dgram, playerInput);

                } catch(Exception e){
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error in ServerInputThread");
            }
        }
    }
}