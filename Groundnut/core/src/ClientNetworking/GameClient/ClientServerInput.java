package ClientNetworking.GameClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

import ClientNetworking.ClientNetworkingManager;
import ClientNetworking.ConnectionState;
import Constants.NetworkingIdentifiers;
import Core.SimulationHandler;
import ServerNetworking.GameServer.ServerHandler;
import ServerNetworking.GameServer.GameStateSample;

/**Recieves simulation-states from authoritative server and handles these.*/
public class ClientServerInput extends Thread {

    //Data
    private final int BUFFER_LENGTH = 576; //THIS MUST BE LARGE ENOUGH - ELSE EOFEXCEPTION
    private byte[] buffer = new byte[BUFFER_LENGTH];

    //multicast Socket
    private MulticastSocket udpMulticastSocket;
    private DatagramPacket dgram;

    private boolean isRunning;

    private ClientNetworkingManager parentHandler;

    ClientServerInput(ClientNetworkingManager parent) {
        parentHandler = parent;

        try {
            InetAddress multicastGroup = InetAddress.getByName(ServerHandler.groupIP);
            int multicastPort = ServerHandler.multicastPort;
            dgram = new DatagramPacket(buffer, buffer.length, multicastGroup, multicastPort); //fix

            udpMulticastSocket = new MulticastSocket(multicastPort);
            udpMulticastSocket.joinGroup(multicastGroup);

        }catch(IOException e){
            System.out.println("Error while creating ClientServerInput Socket.");
        }

    }

    /**Receives and handles authoritative game-states from the server.*/
    @Override public void run() {
        isRunning = true;
        while(isRunning) {
            try {
                if(parentHandler.getState() == ConnectionState.CONNECTED) {

                    //Receive a packet and get its data.
                    udpMulticastSocket.receive(dgram);
                    byte[] compoundData = dgram.getData();

                    //split recieved data into identifier and packetdata
                    byte[] identifier = Arrays.copyOfRange(compoundData, 0,
                            NetworkingIdentifiers.IDENTIFIER_LENGTH);
                    byte[] packetData = Arrays.copyOfRange(compoundData,
                            NetworkingIdentifiers.IDENTIFIER_LENGTH, compoundData.length);

                    //ignore all other packages than simulation-states.
                    if(Arrays.equals(identifier, NetworkingIdentifiers.SIMULATION_STATE_IDENTIFIER)){
                        System.out.println("State received from: " + dgram.getAddress());
                        handleStateInput(packetData);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**Used by run() to handle received game-states.
     * Deserializes the game-state and requests the SimulationHandler to synchronize with it.
     * @param packetData the data of the packet.
     * @throws IOException When the ObjectOutputStream creation has failed */
    private void handleStateInput(byte[] packetData) throws IOException{
        //convert state-object back into an object
        ByteArrayInputStream bais = new ByteArrayInputStream(packetData);
        ObjectInputStream ois = new ObjectInputStream(bais);

        try {
            GameStateSample serverState = (GameStateSample) ois.readObject();
            SimulationHandler.getInstance().synchronizeSimulation(serverState);
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("CLIENT Error reading object");
        }
    }

    /**Closes the input-socket and stops the thread by allowing run() to exit. */
    public void close(){
        isRunning = false;
        udpMulticastSocket.close();
    }
}
