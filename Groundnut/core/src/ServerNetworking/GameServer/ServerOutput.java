package ServerNetworking.GameServer;

import java.io.Serializable;

public class ServerOutput implements Serializable{

    Boolean[] commands;

    public ServerOutput(){}

    public Boolean[] getCommands(){
        return commands;
    }
}