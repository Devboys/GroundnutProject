package ServerNetworking.GameServer;

import Core.SimulationHandler;

import java.net.InetAddress;

//TODO: TICKRATES ARE USED AS WAIT-TIMES, NOT TICKRATES, RENAME.
//TODO: UNIFY PORTS THROUGHOUT PROGRAM

public class ServerHandler {

    //server constants
    public static final int serverPort = 24000;
    public static final int clientPort = 24002;
    public static final int multicastPort = 24003;
    public static final int serverTickRate = 20;
    public static final int clientTickRate = 20;
    public static final String groupIP = "230.0.0.0";

    public static final int maxPlayerCount = 4;

    private PlayerSocket[] clientList = new PlayerSocket[maxPlayerCount];

    private ServerInputThread serverIn;
    private ServerOutputThread serverOut;

    public ServerHandler(){
        for(int i = 0; i < maxPlayerCount; i++){
            clientList[i] = new PlayerSocket();
        }

        //setup input/output threads
        serverOut = new ServerOutputThread();
        serverIn = new ServerInputThread(this);

        serverOut.start();
        serverIn.start();
    }


    /**Attaches a client-ip to the PlayerSocket at the given index.
     * @param ip the client's IP-address
     * @param index the index that the client should be connected to.
     * @throws Exception Whenever a connection-attempt is made on a filled PlayerSocket.
     */
    public void connectPlayer(InetAddress ip, int index) throws Exception{
        if(isClientKnown(ip) || clientList[index].isConnected()) throw new Exception();
        else{
            clientList[index].setConnected(true);
            clientList[index].setPlayerIP(ip);
            SimulationHandler.getInstance().getPlayers().insertPlayer(index);
            SimulationHandler.getInstance().getPlayers().getPlayer(index).setInputSource(clientList[index]);
        }
    }


    /**@return The number of connected players in the clientlist*/
    public int getNumConnectedPlayers() {
        int connectCount = 0;
        for (PlayerSocket pSocket: clientList) {
            if(pSocket.isConnected()){
                connectCount++;
            }
        }
        return connectCount;
    }


    /**@return The index of the first disconnected PlayerSocket encountered in the client list.*/
    public int findFreeClientIndex(){
        for(int i = 0; i < clientList.length; i++){
            PlayerSocket pSocket = clientList[i];
            if(!pSocket.isConnected()) return i;
        }
        return -1;
    }

    /**@param index The index to ask for connection status.
     * @return whether the socket at the given index in the clientlist is connected.*/
    public boolean isIndexFree(int index){
        return !clientList[index].isConnected();
    }


    /** Returns the index of the Client with the given IP-address. If no such players exists, returns -1.
     * @param clientIP The IP of the client.
     * @return the index of the client with the given IP-address*/
    public int findExistingClientIndex(InetAddress clientIP){
        for(int i = 0; i < clientList.length; i++){
            PlayerSocket pSocket = clientList[i];
            if(clientIP.equals(pSocket.getPlayerIP())) return i;
        }
        return -1;
    }

    /**@param clientIP The client IP-address to check.
     * @return Whether the client with the given IP-address has already been connected.*/
    public boolean isClientKnown(InetAddress clientIP){
        for(PlayerSocket pSocket : clientList){
            if(clientIP.equals(pSocket.getPlayerIP())) return true;
        }
        return false;
    }

    /** Returns the client at the given index.*/
    public PlayerSocket getClient(int clientIndex){
        return clientList[clientIndex];
    }


    /**Closes all resources in the handler. Currently untested*/
    public void close(){
        serverIn.close();
        serverOut.close();
    }

}