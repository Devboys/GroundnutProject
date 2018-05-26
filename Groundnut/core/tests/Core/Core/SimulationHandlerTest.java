package Core;

import Entity.Player;
import ServerNetworking.GameServer.GameStateSample;
import ServerNetworking.GameServer.ServerHandler;
import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimulationHandlerTest {

    SimulationHandler sh = SimulationHandler.getInstance();
    GameStateSample gss;

    class TestGameState implements GameState {

        Vector2[] v;


        public TestGameState() {
            v = new Vector2[ServerHandler.maxPlayerCount];
            for (int i = 0; i < ServerHandler.maxPlayerCount; i++) {
                v[i].set(i, i);
            }
        }

        public Vector2[] getPositions() {
            return v;
        }
    }

    @Test
    public void synchronizeSimulation() {
        sh.synchronizeSimulation(new TestGameState());

        for (int i = 0; i < 4; i++) {
            assertEquals(sh.getPlayers().getPlayer(i).getUnitPos(), new Vector2(i,i));
        }
    }

    @Test
    public void startServerSim() {
        sh.startServerSim();
        assertTrue(sh.isServerSide());
    }

    @Test
    public void startClientSim() {
        sh.startClientSim();
        assertFalse(sh.isServerSide());
    }
}