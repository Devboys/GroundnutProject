package ServerNetworking.GameServer;

import Constants.NetworkingIdentifiers;
import Input.PlayerInput;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;


public class ServerInputThread extends Thread {

    //Data
    private final int BUFFER_LENGTH = 256;
    private byte[] buffer = new byte[BUFFER_LENGTH];

    //Socket
    private DatagramSocket udpSocket;
    private DatagramPacket dgram;

    private int serverPort;
    private boolean running;

    public ServerInputThread(){
        try {
            //Socket
            serverPort = ServerHandler.serverPort;
            dgram = new DatagramPacket(buffer, buffer.length);
            udpSocket = new DatagramSocket(serverPort, InetAddress.getByName("0.0.0.0"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void checkDatagram(InetAddress playerIP, PlayerInput playerInput){
//        ArrayList<PlayerSocket> playerList = ServerHandler.getClientList();
//
//        //check if player is known
//        boolean playerIsKnown = false;
//        for(PlayerSocket currPlayer : playerList){
//            if(currPlayer.getPlayerIP().equals(playerIP)){
//                currPlayer.setInputSource(playerInput);
//                playerIsKnown = true;
//            }
//        }
//        if(!playerIsKnown && ServerHandler.getNumConnectedPlayers() < ServerHandler.maxPlayerCount){
//            PlayerSocket pSocket = new PlayerSocket(playerIP, playerList.size());
//            pSocket.setInputSource(playerInput);
//            ServerHandler.addPlayer(pSocket);
//        }
//    }

    @Override public void run() {
        running = true;
        while (running) {
            try {
                udpSocket.receive(dgram);
                byte[] compoundData = dgram.getData();

                //split recieved data into identifier and packetdata
                byte[] identifier = Arrays.copyOfRange(compoundData, 0, NetworkingIdentifiers.IDENTIFIER_LENGTH);
                byte[] packetData = Arrays.copyOfRange(compoundData,
                        NetworkingIdentifiers.IDENTIFIER_LENGTH, compoundData.length);

                if(Arrays.equals(identifier, NetworkingIdentifiers.CONNECT_REQUEST_IDENTIFIER)){
                    handleConnectionRequest(dgram.getAddress());
                }
                else if(Arrays.equals(identifier, NetworkingIdentifiers.MOVEMENT_PACKET_IDENTIFIER)){
                    handleMovementInput(packetData, dgram.getAddress());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close(){
        running = false;
    }

    private void handleConnectionRequest(InetAddress packetIP) throws IOException{
        boolean isKnown = ServerHandler.getInstance().isClientKnown(packetIP);

        if(isKnown){
            //send connection confirm to client.
            ServerOutputThread.sendConnectionConfirm(packetIP);
        }
        else{
            int pIndex = ServerHandler.getInstance().findFreeClientIndex();
            if(pIndex != -1){ //client was accepted
                //setup serverside connection.
                ServerHandler.getInstance().getClient(pIndex).setPlayerIP(packetIP);
                ServerHandler.getInstance().getClient(pIndex).setConnected(true);

                //send connection confirm to client
                ServerOutputThread.sendConnectionConfirm(packetIP);
            }
            else{
                //connection rejected, reply client with rejection
                ServerOutputThread.sendConnectionReject(packetIP);
            }
        }
    }

    private void handleMovementInput(byte[] packetData, InetAddress packetIP) throws IOException{
        ByteArrayInputStream bais = new ByteArrayInputStream(packetData);
        ObjectInputStream ois = new ObjectInputStream(bais);
        try{
            PlayerInput playerInput = (PlayerInput) ois.readObject();

            int pIndex = ServerHandler.getInstance().findExistingClientIndex(packetIP);
            ServerHandler.getInstance().getClient(pIndex).setInputSource(playerInput);

        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}