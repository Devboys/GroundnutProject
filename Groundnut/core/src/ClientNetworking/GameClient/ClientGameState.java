package ClientNetworking.GameClient;

import Input.PlayerInputHandler;
import ServerNetworking.GameServer.ServerOutput;

import java.util.ArrayList;

public class ClientGameState{

    private static int totalGameStateUpdates;
    private static Boolean[] commands = PlayerInputHandler.getCommands();

    public static void updateClientState(ServerOutput serverOutput){
        totalGameStateUpdates++;
        System.out.println("CLIENT game state: " + totalGameStateUpdates);
    }

    public static Boolean getCommand(int i) {
        return commands[i];
    }
}
