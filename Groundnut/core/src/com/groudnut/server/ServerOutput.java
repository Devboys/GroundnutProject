package com.groudnut.server;

import java.io.Serializable;

public class ServerOutput implements Serializable{

    private int sizeOfCommandList;
    private String[] commandList;

    private void commands(String[] commandsForClient){
        for(int i = 0; i <= commandsForClient.length; i++){
            commandList[sizeOfCommandList] = commandsForClient[i];
            sizeOfCommandList++;
        }
    }
}