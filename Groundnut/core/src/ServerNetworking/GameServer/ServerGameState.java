package ServerNetworking.GameServer;

import ClientNetworking.GameClient.ClientOutput;
<<<<<<< HEAD
import Core.RenderThread;
import com.sun.security.ntlm.Server;

import java.io.Serializable;

import static Core.RenderThread.*;
=======
>>>>>>> 1fafd57bb3d6b3cd8a960d1a25cef22bc0f69480

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
