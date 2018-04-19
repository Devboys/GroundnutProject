package ClientNetworking.GameClient;

import Core.RenderThread;
import Entity.UnitHandler;
import Input.PlayerInputHandler;
import Scenes.TestScene;
import ServerNetworking.GameServer.ServerGameState;
import ServerNetworking.GameServer.ServerOutput;

import java.util.ArrayList;

public class ClientGameState{

    private static int totalGameStateUpdates;
    private static Boolean[] commandList;
    private static ServerGameState sgs;

    public static void updateClientState(ServerOutput serverOutput){
        commandList = serverOutput.getCommands();
        System.out.println(commandList);
        totalGameStateUpdates++;
        updateGame(commandList);
        RenderThread.setClientInfo(Integer.toString(totalGameStateUpdates));
    }

    public static void updateGame(Boolean[] commands){
        //LOL
        UnitHandler uh = TestScene.getUh();
        uh.updateUnitPositions(commands);
    }

    public static Boolean[] getCommandList(){
        return commandList;
    }

    public static void setCommandList(Boolean[] cl){
        commandList = cl;
    }
}
