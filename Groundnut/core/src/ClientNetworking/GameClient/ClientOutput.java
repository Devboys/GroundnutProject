package ClientNetworking.GameClient;

import Input.PlayerInputHandler;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;

public class ClientOutput implements Serializable {

    private String testString = "ClientOutput";
    private static Boolean[] commandList;

    public ClientOutput(){
        //commandList = PlayerInputHandler.getCommands();
    }

    public static Boolean[] getCommandList() {
        return commandList;
    }
}
