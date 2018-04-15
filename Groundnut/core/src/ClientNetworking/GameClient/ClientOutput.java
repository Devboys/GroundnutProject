package ClientNetworking.GameClient;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;

public class ClientOutput implements Serializable {

    private String testString = "ClientOutput";
    private int sizeOfCommandList;
    private Boolean[] commandList = new Boolean[3];

    public ClientOutput(){
        
    }

    public ClientOutput(ClientGameState cgs) {
        commandList[0] = cgs.getCommand(0);
        commandList[1] = cgs.getCommand(1);
        commandList[2] = cgs.getCommand(2);
        commandList[3] = cgs.getCommand(3);
    }
    public Boolean[] getCommandList() {
        return commandList;
    }
}
