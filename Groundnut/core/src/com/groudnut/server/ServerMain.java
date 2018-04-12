package com.groudnut.server;

import com.groudnut.client.ClientInfo;
import com.groudnut.client.ClientInputThread;
import com.groudnut.client.ClientOutputThread;

import java.io.IOException;
import java.net.UnknownHostException;

public class ServerMain {

    public static void main(String[] args){
        try {
            ClientInfo clientInfo = new ClientInfo();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        ServerHandler serverHandler = new ServerHandler();

        ServerInputThread serverInput = new ServerInputThread();
        ServerOutputThread serverOutput = null;
        try {
            serverOutput = new ServerOutputThread();
        } catch (IOException e) {
            e.printStackTrace();
        }

        serverInput.start();
        serverOutput.start();

    }
}