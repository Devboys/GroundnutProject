package ClientNetworking.GameClient;

import Input.PlayerInputHandler;
import ServerNetworking.GameServer.ServerOutput;

import java.util.ArrayList;

public class ClientGameState{

    private static int totalGameStateUpdates;
    private static Boolean[] commandList;

    public static void updateClientState(ServerOutput serverOutput){
        commandList = ClientGameState.getCommandList();
        totalGameStateUpdates++;
        System.out.println("CLIENT game state: " + totalGameStateUpdates);
    }

    public static Boolean[] getCommandList(){
        return commandList;
    }
}
