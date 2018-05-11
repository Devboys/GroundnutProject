package ServerNetworking.GameServer;

import Core.SimulationHandler;
import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;

public class GameStateSample implements Serializable{

    private Vector2[] playerPos;

    public GameStateSample(){
        playerPos = new Vector2[ServerHandler.maxPlayerCount];

        for (int i = 0; i < ServerHandler.maxPlayerCount; i++) {
            playerPos[i] = SimulationHandler.getInstance().getPlayers().getPlayer(i).getUnitPos();
        }
    }

    public Vector2[] getPositions(){
        return playerPos;
    }

}