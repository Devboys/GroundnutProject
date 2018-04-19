package ServerNetworking.GameServer;

import java.net.InetAddress;
import java.util.ArrayList;

public class ServerHandler {

    private static final int gamePort = 24000;
    private static final int multicastPort = 24002;
    private static final int maxPlayerCount = 4;
    private static int connectedPlayers = 0;
    private static final int clientTickRate = 2000; // 1 = 1ms
    private static final int serverTickRate = 1000;
    private static final String serverIp = "127.0.0.1";
    private static final String group = "230.0.0.0";
    private static ArrayList<InetAddress> clientIPs = new ArrayList<InetAddress>(maxPlayerCount);

//    public static int checkPlayer(InetAddress IP) {
//        if (!(clientIPs.size() >= maxPlayerCount)) {
//            if (clientIPs.isEmpty()) {
//                clientIPs.add(IP);
//                System.out.println(IP.toString() + " is player 0");
//                return 0; //Player #1
//            } else if (ServerHandler.getConnectedPlayers() < maxPlayerCount) {
//                for (int i = 0; i < ServerHandler.getConnectedPlayers(); i++) {
//                    if (!IP.equals(clientIPs.get(i))) {
//                        clientIPs.add(IP);
//                        System.out.println(IP.toString() + " is player " + i);
//                        return i;
//                    }
//                }
//            }
//        }
//        for (int i = 0; i < maxPlayerCount; i++){
//            if(IP.equals(clientIPs.get(i))){
//                return i;
//            }
//        }
//        System.out.println("This shouldn't happen.");
//        return -1;
//    }

    public static int getMaxPlayerCount() { return maxPlayerCount; }
    public static int getConnectedPlayers() { return connectedPlayers; }
    public static ArrayList<InetAddress> getClientIPs() {
        return clientIPs;
    }
    public static String getGroup() {
        return group;
    }
    //public static int getPlayerCount() { return playerCount; }
    public static int getGamePort() { return gamePort; }
    public static int getMulticastPort() { return multicastPort; }
    public static int getClientTickRate(){return clientTickRate; }
    public static int getServerTickRate(){return clientTickRate; }
    public static String getServerIp() { return serverIp; }
}