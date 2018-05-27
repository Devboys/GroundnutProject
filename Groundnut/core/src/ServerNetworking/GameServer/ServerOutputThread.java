package ServerNetworking.GameServer;

import Constants.NetworkingIdentifiers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;


public class ServerOutputThread extends Thread {

    //Multicast Socket
    private MulticastSocket udpMulticastSocket;
    private InetAddress multicastGroupIP;
    private int multicastPort;

    //single-user socket
    private static DatagramSocket udpSocket;

    private boolean running;

    ServerOutputThread() {
        try {
            //Multicast
            multicastGroupIP = InetAddress.getByName(ServerHandler.groupIP);
            multicastPort = ServerHandler.multicastPort;
            udpMulticastSocket = new MulticastSocket();
            udpMulticastSocket.joinGroup(multicastGroupIP);

            //single-user
            udpSocket = new DatagramSocket();
        } catch (IOException e) {
            System.out.println("SERVER Error creating Output multicast socket or data stream.");
        }
    }

    /**Runs a while-loop that continuously sends samples of the serverside game-state at intervals based on
     * ServerHandler.serverTickRate.*/
    @Override public void run(){
        running = true;
        while(running) {
            try {
                sendSimulationState();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(ServerHandler.serverTickRate);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Error in ServerOutputThread");
            }
        }
    }

    /**Called by run() whenever a simulation state needs to be sent. This creates a new GameStateSample object,
     * serializes it and sends it to the multicast-address defined in ServerHandler.groupIP.
     * @throws IOException whenever the serialization-process or identifier-adding process fails.*/
    private void sendSimulationState() throws IOException{
        //Data
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        oos.writeObject(new GameStateSample());
        oos.flush();

        byte[] objectBytes = baos.toByteArray();

        //add identifier to serialized object
        ByteArrayOutputStream compoundingStream = new ByteArrayOutputStream();
        compoundingStream.write(NetworkingIdentifiers.SIMULATION_STATE_IDENTIFIER);
        compoundingStream.write(objectBytes);

        compoundingStream.flush();

        //send compounded packet
        byte[] compoundOutput = compoundingStream.toByteArray();
        udpMulticastSocket.send(new DatagramPacket(compoundOutput, compoundOutput.length, multicastGroupIP, multicastPort));
        baos.reset();
    }

    /**Called whenever a connection confirmation should be send to a client.
     * Sends connection-confirm directly to the given IP-address at the port defined in ServerHandler.clientPort.
     * @param targetIP the IP-address of the confirm-message target.
     * @throws IOException Whenever the identifier-adding process fails*/
    public static void sendConnectionConfirm(InetAddress targetIP) throws IOException{
        String packetMessage = "Connection confirmed";

        ByteArrayOutputStream compoundingStream = new ByteArrayOutputStream();
        compoundingStream.write(NetworkingIdentifiers.ACCEPT_IDENTIFIER);
        compoundingStream.write(packetMessage.getBytes());

        byte[] compoundOutput = compoundingStream.toByteArray();
        udpSocket.send(new DatagramPacket(compoundOutput, compoundOutput.length, targetIP, ServerHandler.clientPort));

        System.out.println("confirm sent to: " + targetIP.toString() + "(" + ServerHandler.clientPort + ")");
    }

    /**Called whenever a connection rejection should be send to a client.
     * Sends connection-rejection directly to the given IP-address at the port defined in ServerHandler.clientPort.
     * @param targetIP the IP-address of the confirm-message target.
     * @throws IOException Whenever the identifier-adding process fails*/
    public static void sendConnectionReject(InetAddress targetIP) throws IOException{
        String packetMessage = "Connection denied";

        ByteArrayOutputStream compoundingStream = new ByteArrayOutputStream();
        compoundingStream.write(NetworkingIdentifiers.REJECTION_IDENTIFIER);
        compoundingStream.write(packetMessage.getBytes());

        byte[] compoundOutput = compoundingStream.toByteArray();
        udpSocket.send(new DatagramPacket(compoundOutput, compoundOutput.length, targetIP, ServerHandler.clientPort));

        System.out.println("Reject sent to: " + targetIP.toString() + "(" + ServerHandler.clientPort + ")");
    }

    /**Closes this thread by allowing the run() method to exit. Untested.*/
    public void close(){ running = false; }
}