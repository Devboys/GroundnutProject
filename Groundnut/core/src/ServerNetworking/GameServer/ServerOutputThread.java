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

    //Data
    private byte[] buffer;

    //Multicast Socket
    private MulticastSocket udpMulticastSocket;
    private InetAddress multicastGroupIP;
    private int multicastPort;

    //single-user socket
    private static DatagramSocket udpSocket;

    private boolean running;

    public ServerOutputThread() {
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

    @Override
    public void run(){
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

    public void sendSimulationState() throws IOException{
        //Data
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        oos.writeObject(new GameStateSample());
        oos.flush();

        //add identifier to serialized object
        ByteArrayOutputStream compoundingStream = new ByteArrayOutputStream();
        compoundingStream.write(NetworkingIdentifiers.SIMULATION_STATE_IDENTIFIER);
        compoundingStream.write(baos.toByteArray());

        //send compounded packet
        byte[] compoundOutput = compoundingStream.toByteArray();
        udpMulticastSocket.send(new DatagramPacket(compoundOutput, compoundOutput.length, multicastGroupIP, multicastPort));
        baos.reset();
    }

    public static void sendConnectionConfirm(InetAddress targetIP) throws IOException{
        String packetMessage = "Connection confirmed";

        ByteArrayOutputStream compoundingStream = new ByteArrayOutputStream();
        compoundingStream.write(NetworkingIdentifiers.ACCEPT_IDENTIFIER);
        compoundingStream.write(packetMessage.getBytes());

        byte[] compoundOutput = compoundingStream.toByteArray();
        udpSocket.send(new DatagramPacket(compoundOutput, compoundOutput.length, targetIP, ServerHandler.clientPort));

        System.out.println("confirm sent to: " + targetIP.toString() + "(" + ServerHandler.clientPort + ")");
    }

    public static void sendConnectionReject(InetAddress targetIP) throws IOException{
        String packetMessage = "Connection denied";

        ByteArrayOutputStream compoundingStream = new ByteArrayOutputStream();
        compoundingStream.write(NetworkingIdentifiers.REJECTION_IDENTIFIER);
        compoundingStream.write(packetMessage.getBytes());

        byte[] compoundOutput = compoundingStream.toByteArray();
        udpSocket.send(new DatagramPacket(compoundOutput, compoundOutput.length, targetIP, ServerHandler.clientPort));

        System.out.println("deny sent");
    }

    public void close(){ running = false; }
}