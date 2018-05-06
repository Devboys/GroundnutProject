package ServerNetworking.GameServer;

import java.net.InetAddress;

public class ServerHandler {

    //server constants
    public static final int serverPort = 24000;
    public static final int clientPort = 24002;
    public static final int multicastPort = 24003;
    public static final int maxPlayerCount = 4;
    public static final int serverTickRate = 1000;
    public static final int clientTickRate = 1000;
    public static final String groupIP = "230.0.0.0";
    public static final String serverIP = "localhost";

    private PlayerSocket[] clientList = new PlayerSocket[maxPlayerCount];

    //make singleton
    private static ServerHandler instance;
    private ServerHandler(){
        for(int i = 0; i < maxPlayerCount; i++){
            clientList[i] = new PlayerSocket(i);
        }
    }
    public static ServerHandler getInstance(){
        if(instance == null){
            instance = new ServerHandler();
        }
        return instance;
    }

    public int getNumConnectedPlayers() { return clientList.length; }

    public int findFreeClientIndex(){
        for(PlayerSocket pSocket : clientList){
            if(!pSocket.isConnected()) return pSocket.getPlayerIndex();
        }
        return -1;
    }

    public int findExistingClientIndex(InetAddress clientIP){
        for(PlayerSocket pSocket : clientList){
            if(clientIP == pSocket.getPlayerIP()) return pSocket.getPlayerIndex();
        }
        return -1;
    }

    public boolean isClientKnown(InetAddress clientIP){
        for(PlayerSocket pSocket : clientList){
            if(clientIP == pSocket.getPlayerIP()) return true;
        }
        return false;
    }

    public boolean isClientConnected(int clientIndex){
        return clientList[clientIndex].isConnected();
    }

    public PlayerSocket getClient(int clientIndex){
        return clientList[clientIndex];
    }

}