package ServerNetworking.GameServer;

import Core.SimulationHandler;
import java.net.InetAddress;

//TODO: TICKRATES ARE USED AS WAIT-TIMES, NOT TICKRATES, RENAME.
//TODO: MAKE SERVERHANDLER LIKE OTHER HANDLERS(Non-static, and starts input-and-output threads for server.)

public class ServerHandler {

    //server constants
    public static final int serverPort = 24000;
    public static final int clientPort = 24002;
    public static final int multicastPort = 24003;
    public static final int maxPlayerCount = 4;
    public static final int serverTickRate = 1000;
    public static final int clientTickRate = 1000;
    public static final String groupIP = "230.0.0.0";

    private PlayerSocket[] clientList = new PlayerSocket[maxPlayerCount];

    //make singleton - probably wrong, probably parent relationship better
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

    public void connectPlayer(InetAddress ip, int index) throws Exception{
        if(isClientKnown(ip) || clientList[index].isConnected()) throw new Exception();
        else{
            clientList[index].setConnected(true);
            clientList[index].setPlayerIP(ip);
            SimulationHandler.getInstance().getPlayers().insertPlayer(index);
            SimulationHandler.getInstance().getPlayers().getPlayer(index).setInputSource(clientList[index]);
        }
    }

    public int getNumConnectedPlayers() {
        int connectCount = 0;
        for (PlayerSocket pSocket: clientList) {
            if(pSocket.isConnected()){
                connectCount++;
            }
        }
        return connectCount;
    }

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

    public PlayerSocket getClient(int clientIndex){
        return clientList[clientIndex];
    }

}