package ServerNetworking.GameServer;

import java.io.Serializable;

public class ServerOutput implements Serializable{

    Boolean[] commands;

    public ServerOutput(){
        commands = ServerGameState.getCommandList();
    }

    public Boolean[] getCommands(){
        return commands;
    }
}