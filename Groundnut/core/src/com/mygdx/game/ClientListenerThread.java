package com.mygdx.game;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ClientListenerThread extends Thread {

    private MulticastSocket multiSocket;
    private InetAddress address;

    public ClientListenerThread() {
        try {
            multiSocket = new MulticastSocket();
        }catch(IOException e){
            //handle this exception
        }
    }

    @Override
    public void run() {



    }

}
