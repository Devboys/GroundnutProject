package ClientNetworking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

public class ClientListenerThread extends Thread {

    private MulticastSocket multiSocket;
    private InetAddress groupAdress;

    String packetInputData;

    private static int[][] playerPositions = new int[4][2];

    static int coordX = 50;
    static int coordY = 50;

    int clientPort = 24001;

    public ClientListenerThread() {
        try {
            multiSocket = new MulticastSocket(clientPort);
            groupAdress = InetAddress.getByName("230.0.0.0");
            multiSocket.joinGroup(groupAdress);
        }catch(IOException e){
            //handle this exception
        }
    }

    @Override
    public void run() {
        boolean running = true;
        while(running) {
            try {
                byte[] buffer = new byte[256];
                DatagramPacket readPacket = new DatagramPacket(buffer, buffer.length);
                multiSocket.receive(readPacket);

                System.out.println("package received");

                packetInputData = new String(readPacket.getData());
                System.out.println(packetInputData);

                String[] stringCoords = packetInputData.split("@");
                System.out.println(stringCoords.length);

                playerPositions[0][0] = Integer.parseInt(stringCoords[1]);
                playerPositions[0][1] = Integer.parseInt(stringCoords[2]);

                playerPositions[1][0] = Integer.parseInt(stringCoords[3]);
                playerPositions[1][1] = Integer.parseInt(stringCoords[4]);

                playerPositions[2][0] = Integer.parseInt(stringCoords[5]);
                playerPositions[2][1] = Integer.parseInt(stringCoords[6]);

                playerPositions[3][0] = Integer.parseInt(stringCoords[7]);
                playerPositions[3][1] = Integer.parseInt(stringCoords[8]);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int[][] getPositions() {
        return playerPositions;
    }

    public static int getX(){
        return coordX;
    }

    public static int getY(){
        return coordY;
    }
}
