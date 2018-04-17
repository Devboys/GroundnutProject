package ServerNetworking.GameServer;

import java.io.Serializable;

public class ServerOutput implements Serializable{

    private String testString = "ServerOutput";
    private int one = 1;
    private int sizeOfCommandList;
    private String[] commandList;

    public void commands(String[] commandsForClient){
        for(int i = 0; i <= commandsForClient.length; i++){
            commandList[sizeOfCommandList] = commandsForClient[i];
            sizeOfCommandList++;
            System.out.println(commandList);
        }
    }

    public String getTestString(){
        return testString;
    }
}