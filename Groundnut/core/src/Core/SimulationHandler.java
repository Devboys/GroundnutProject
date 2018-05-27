package Core;

import ClientNetworking.ClientNetworkingManager;
import Entity.PlayerGroup;
import Input.PlayerInput;
import Input.PlayerInputHandler;
import ServerNetworking.GameServer.ServerHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;

//TODO: MAKE ALL CLOSE() METHODS RELY ON A SIMULATION-HANDLER BOOLEAN (except lobby)

public class SimulationHandler {

    private static SimulationHandler instance;
    private SimulationHandler(){}
    private static boolean serverSide;
    private PlayerInput clientInput;
    private PlayerGroup playerGroup;
    private int clientID;

    private boolean isStarted;

    /**@return Returns the singleton-instance of the SimulationHandler.
     * Instantiates the SimulationHandler on first call*/
    public static SimulationHandler getInstance(){
        if(instance == null){
            instance = new SimulationHandler();
        }
        return instance;
    }

    /**Synchronizes the game-state with the provided authoritative state. Currently only synchronizes player-positions.
     * @param state the authoritative state to synchronize with*/
    public void synchronizeSimulation(GameState state) {
        if(!isStarted) {
            isStarted = true;
            Vector2[] newestPositions = state.getPositions();

            for (int i = 0; i < ServerHandler.maxPlayerCount; i++) {
                playerGroup.getPlayer(i).setPos(newestPositions[i]);
            }
        }
    }

    /**Starts a simulation and boots up the serverside networking.
     * Does nothing if another start-call has already been made. */
    public void startServerSim(){
        if(!isStarted) {
            isStarted = true;
            serverSide = true;

            playerGroup = new PlayerGroup();

            LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
            config.title = "SERVER SIMULATION";
            new LwjglApplication(new GameThread(), config);

            ServerHandler sh = new ServerHandler();
        }
    }

    /**Starts a simulation and a boots up the clientside networking.
     * Does nothing if another start-call has already been made. */
    public void startClientSim() {
        if (!isStarted) {
            isStarted = true;
            ClientNetworkingManager clientNetworkingManager = new ClientNetworkingManager();

            serverSide = false;

            playerGroup = new PlayerGroup();

            LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
            config.title = "CLIENT SIMULATION";
            new LwjglApplication(new GameThread(), config);

            clientInput = new PlayerInput();
            Gdx.input.setInputProcessor(new PlayerInputHandler(clientInput));

            playerGroup.insertPlayer(0);
            playerGroup.getPlayer(0).setInputSource(clientInput);
        }
    }

    /**
     * @return Returns whether or not the simulation belongs to a serverside application or a clientside one.*/
    public boolean isServerSide() { return serverSide; }

    /** @return the PlayerGroup for this simulation*/
    public PlayerGroup getPlayers(){
        return playerGroup;
    }

    /**Returns the PlayerInput object that the user controls. Returns nothing if the call is made on serverside.*/
    public PlayerInput getPlayerInput(){
        if(!serverSide) {
            return clientInput;
        }
        else return null;
    }

    /**Returns the clients ID, as set by the Lobby and mirrored on the serverside. Currently should match the
     * index of the local player in PlayerGroup. Relevant for Clientside only.*/
    public int getClientID() {
        return clientID;
    }


    /**Sets the client's ID, as set by the Lobby and mirrored on the serverside. Currently should match the
     * index of the local player in PlayerGroup. Relevant for Clientside only.*/
    public void setClientID(int id){
        System.out.println("PlayerID set to: " + id);
        clientID = id;

        //Switch local player index in playergroup (default is 0) for its serverside index (clientID).
        playerGroup.switchPlayerIndexes(0, clientID);
    }

}
