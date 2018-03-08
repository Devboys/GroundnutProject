package Code;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class ServerListener extends Thread {

    public static final String DATATYPESPLITTER= "-";
    public static final String ELEMENTSPLITTER = "|";
    public static final String COORDKEYWORKD = "COORD";

    private ArrayList<InetAddress> IPaddressArray = new ArrayList<InetAddress>();

    DatagramSocket readSocket;
    byte[] buf = new byte[256];



    public ServerListener() {
        try {
            readSocket = new DatagramSocket(PublicMain.serverPort);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {

            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                readSocket.receive(packet);
                boolean IPexists = true;
                for (int i = 0; i < IPaddressArray.size(); i++) {
                    if (IPaddressArray.get(i) == packet.getAddress()) {
                        IPexists = false;
                    }
                }
                if (IPexists = true) {
                    IPaddressArray.add(packet.getAddress());
                }
                String input = new String(packet.getData(), 0, packet.getLength());
                parseString(input, packet.getAddress());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void parseString(String input, InetAddress packetAddress ) {
        for(int playerindex = 0; playerindex < IPaddressArray.size(); playerindex++){
            if(IPaddressArray.get(playerindex) == packetAddress){
                String[] allData = input.split(DATATYPESPLITTER);
                for(String e : allData){

                    //Coordinates
                    if(e.startsWith(COORDKEYWORKD)){
                        String[] coords = e.split(ELEMENTSPLITTER);

                        //Discard keyword
                        coords = coords[1].split(";");

                        int[] convertedCoords = new int[2];
                        for(int coordIndex = 0; coordIndex < coords.length; coordIndex++){
                            convertedCoords[coordIndex] = Integer.parseInt(coords[coordIndex]);
                        }
                        ServerWriter.coords[playerindex] = convertedCoords;
                    }

                    //other types...
                }
            }
        }
    }
}
