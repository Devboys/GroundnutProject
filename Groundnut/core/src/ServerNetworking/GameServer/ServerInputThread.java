package ServerNetworking.GameServer;

import Constants.NetworkingIdentifiers;
import Input.PlayerInput;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
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

    private ServerHandler serverHandler;

    /**@param parent The ServerHandler that this thread is attached too*/
    ServerInputThread(ServerHandler parent){
        serverHandler = parent;

        try {
            //Socket
            serverPort = ServerHandler.serverPort;
            dgram = new DatagramPacket(buffer, buffer.length);
            udpSocket = new DatagramSocket(serverPort, InetAddress.getByName("0.0.0.0"));

        } catch (IOException e) {
            System.out.println("heyyyy");
            e.printStackTrace();
        }
    }

    /**Runs a while-loop that continuously checks for various requests from the clientside.*/
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
                    handleConnectionRequest(dgram.getAddress(), packetData);
                }
                else if(Arrays.equals(identifier, NetworkingIdentifiers.MOVEMENT_PACKET_IDENTIFIER)){
                    if(serverHandler.isClientKnown(dgram.getAddress())) {
                        handleMovementInput(packetData, dgram.getAddress());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**Called by run() whenever a connection-request packet has been received. Checks whether the client can connect
     * and triggers the outputthread to return a connection confirmation or rejection based on the result.
     * @param packetIP The IP of the received packet
     * @param packetData The data of the received packet.
     * @throws IOException*/
    private void handleConnectionRequest(InetAddress packetIP, byte[] packetData) throws IOException{
        System.out.println("Connect request received from: " + packetIP.toString());

        int playerID = ByteBuffer.wrap(packetData).getInt();

        if(serverHandler.isClientKnown(packetIP)){
            //send connection confirm to client.
            ServerOutputThread.sendConnectionConfirm(packetIP);
        }
        else{
            if(serverHandler.isIndexFree(playerID)){ //client was accepted
                //setup serverside connection.
                try {
                    serverHandler.connectPlayer(packetIP, playerID);
                }catch (Exception e){
                    System.out.println("NEW PLAYER ATTEMPTED TO CONNECT TO ALREADY-FILLED INDEX");
                }

                //send connection confirm to client
                ServerOutputThread.sendConnectionConfirm(packetIP);
            }
            else{
                //connection rejected, reply client with rejection
                ServerOutputThread.sendConnectionReject(packetIP);
            }
        }
    }

    /**Called by run() whenever a movement-input packet has been detected.
     * Ties the user-input to the PlayerSocket in the ServerHandler if the client is known.
     * @param packetData The data of the received packet.
     * @param packetIP The IP-address attached to the packet.
     * @throws IOException*/
    private void handleMovementInput(byte[] packetData, InetAddress packetIP) throws IOException {
        System.out.println("input received from: " + packetIP.toString());

        if (serverHandler.isClientKnown(packetIP)) {
            ByteArrayInputStream bais = new ByteArrayInputStream(packetData);
            ObjectInputStream ois = new ObjectInputStream(bais);
            try {
                PlayerInput playerInput = (PlayerInput) ois.readObject();

                int pIndex = serverHandler.findExistingClientIndex(packetIP);
                serverHandler.getClient(pIndex).setInput(playerInput);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**Closes the server-loop by allowing the run() method to exit. Untested.*/
    public void close(){
        running = false;
    }
}