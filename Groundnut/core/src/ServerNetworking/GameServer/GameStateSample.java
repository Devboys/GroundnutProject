package ServerNetworking.GameServer;

import Core.GameState;
import Core.SimulationHandler;
import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;

public class GameStateSample implements Serializable, GameState{

    private Vector2[] playerPos;


    /**Samples and stores all player-positions in the local simulation.
     * A start method must have been called on SimulationHandler before this is useful*/
    public GameStateSample(){
        playerPos = new Vector2[ServerHandler.maxPlayerCount];

        for (int i = 0; i < ServerHandler.maxPlayerCount; i++) {
            playerPos[i] = SimulationHandler.getInstance().getPlayers().getPlayer(i).getUnitPos();
        }
    }


    /** @return All stored player-positions */
    public Vector2[] getPositions(){
        return playerPos;
    }
}