package ServerNetworking.GameServer;

import java.io.Serializable;

public class GameStateSample implements Serializable{

    Boolean[] commands;

    public GameStateSample(){}

    public Boolean[] getCommands(){
        return commands;
    }
}