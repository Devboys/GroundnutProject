package ServerNetworking.GameServer;

import ClientNetworking.GameClient.ClientOutput;
import Core.RenderThread;
import com.sun.security.ntlm.Server;

import java.io.Serializable;

import static Core.RenderThread.*;

public class ServerGameState {

    private static int totalGameStateUpdates;
    private static int[][] playerPositions;
    private static Boolean[] commandList;

    public static void updateServerState(int playerNumber, ClientOutput clientOutput){
        commandList = clientOutput.getCommandList();
     //   updatePlayerPositions(playerNumber, commandList);
        totalGameStateUpdates++;
        RenderThread.setServerInfo(Integer.toString(totalGameStateUpdates));
    }

    private static void updatePlayerPositions(int playerNumber, Boolean[] commands){
        if(commands[0]){
            
        }

        // MAKE NEW
    }

    public static ServerGameState getServerState(){
        ServerGameState gameState = new ServerGameState();
        return gameState;
    }

    public static Boolean[] getCommandList(){
        return commandList;
    }
}
