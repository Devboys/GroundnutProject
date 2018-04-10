package com.groudnut.client;

import java.io.IOException;

public class ChatAndCommandThread extends Thread {

    public boolean isrunning = true;
    public LobbyClientMain parent;
    public String newtext;



    public ChatAndCommandThread(LobbyClientMain parent){
        this.parent = parent;
    }

    public void run(){
        while(isrunning == true){
            System.out.println("client thread running");
            try {
                newtext = parent.bufferedreader.readLine();
                System.out.println(newtext);
            } catch (IOException e) {
                System.out.println("killing Thread");
                isrunning = false;
                this.stop();
                e.printStackTrace();
            }
        }
    }
}
