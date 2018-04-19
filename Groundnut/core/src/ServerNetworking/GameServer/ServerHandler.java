package ServerNetworking.GameServer;

import java.net.InetAddress;
import java.util.ArrayList;

public class ServerHandler {

    private static final int gamePort = 24000;
    private static final int multicastPort = 24001;
    private static final int maxPlayerCount = 4;
    private static int connectedPlayers = 0;
    private static final int clientTickRate = 2000; // 1 = 1ms
    private static final int serverTickRate = 1000;
    private static final String serverIp = "127.0.0.1";
    private static final String group = "230.0.0.0";
    private static ArrayList<InetAddress> clientIPs = new ArrayList<InetAddress>(maxPlayerCount);

    public static int getMaxPlayerCount() { return maxPlayerCount; }
    public static int getConnectedPlayers() { return connectedPlayers; }
    public static ArrayList<InetAddress> getClientIPs() {
        return clientIPs;
    }
    public static void addIP(int index, InetAddress IP) { clientIPs.add(index, IP);}
    public static String getGroup() {
        return group;
    }
    //public static int getPlayerCount() { return playerCount; }
    public static int getGamePort() { return gamePort; }
    public static void incrementConnectPlayers() {
        connectedPlayers++;
    }
    public static int getMulticastPort() { return multicastPort; }
    public static int getClientTickRate(){return clientTickRate; }
    public static int getServerTickRate(){return clientTickRate; }
    public static String getServerIp() { return serverIp; }
}