package ServerNetworking.GameServer;

import ClientNetworking.GameClient.ClientOutput;

public class ServerGameState {

    private static int totalGameStateUpdates;
    private static int[][] playerPositions;
    private static Boolean[] commandList;

    public static void updateServerState(int playerNumber, ClientOutput clientOutput){
        commandList = clientOutput.getCommandList();
        updatePlayerPositions(playerNumber, commandList);
        totalGameStateUpdates++;
        System.out.println("SERVER game state: " + totalGameStateUpdates);
    }

    private static void updatePlayerPositions(int playerNumber, Boolean[] commands){
        if(commands[0]){
            
        }
    }

    public static ServerGameState getServerState(){
        ServerGameState gameState = new ServerGameState();
        return gameState;
    }

    public static Boolean[] getCommandList(){
        return commandList;
    }
}
