package Code;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class PublicServerThread extends Thread {

    private DatagramSocket socket;
    private byte[] buf = new byte[256];
    private ArrayList<InetAddress> IPaddressArray = new ArrayList<InetAddress>(4);
    private InetAddress group;
    private ArrayList<Integer> ports = new ArrayList<>();

    private String compundedMessage;
    private String[] userMessages = new String[4];

    private int serverPort = 24000;
    private int clientPort = 24001;

    public PublicServerThread() throws IOException {
        socket = new DatagramSocket(serverPort);
        group = InetAddress.getByName("230.0.0.0");

        for(int i = 0; i < userMessages.length; i++){
            userMessages[i] = "@0@0";
        }
    }

    public void run() {
        while (true) {
            try {
                //read message
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                System.out.println("package received from ID:" + packet.getAddress());

                //categorize IP & read messages
                boolean found = false;

                if(!IPaddressArray.isEmpty()) {
                    for (int i = 0; i < IPaddressArray.size(); i++) {
                        if (packet.getAddress().equals(IPaddressArray.get(i))) {
                            System.out.println("REEE");
                            found = true;
                            userMessages[i] = new String(packet.getData(), 0, packet.getLength());
                            //userMessages[i] = userMessages[i] + "@";
                        }
                    }
                }
                if (!found) {
                    IPaddressArray.add(packet.getAddress());
                }
                    //compund message
                compundedMessage = null;
                    for (int i = 0; i < 4; i++) {
                        compundedMessage = compundedMessage + userMessages[i];
                    }
                    compundedMessage = compundedMessage + "@";

                    //send message
                    byte[] buf = compundedMessage.getBytes();

                    DatagramPacket newpacket = new DatagramPacket(buf, buf.length, group, clientPort);
                    socket.send(newpacket);

                    System.out.println("package sent");
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
