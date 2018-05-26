package Core;

import ServerNetworking.GameServer.GameStateSample;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimulationHandlerTest {

    SimulationHandler sh = SimulationHandler.getInstance();
    GameStateSample gss;

    @Before
    public void setUp() {
        gss = new GameStateSample();
    }

    @Test
    public void startSimulation() {

    }

    @Test
    public void synchronizeSimulation() {

    }
}