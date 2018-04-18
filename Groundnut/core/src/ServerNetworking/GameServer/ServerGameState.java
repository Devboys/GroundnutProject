package ServerNetworking.GameServer;

import ClientNetworking.GameClient.ClientOutput;
import Core.GameThread;

public class ServerGameState {

    private static int totalGameStateUpdates;
    private static Boolean[] commandList;

    public ServerGameState(ClientOutput clientOutput){}

    public static void updateServerState(ClientOutput clientOutput){
        GameThread.setServerInfo(commandList[0] + " + " + commandList[1] + " + " + commandList[2] + " + " + commandList[3]);
        commandList = clientOutput.getCommandList();
        totalGameStateUpdates++;
        System.out.println("SERVER game state: " + totalGameStateUpdates);
    }
}
