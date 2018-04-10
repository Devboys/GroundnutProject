package com.groudnut.client;

import com.groudnut.client.ClientInfo;
import java.io.Serializable;
import java.net.InetAddress;

public class ClientOutput implements Serializable {

    private InetAddress clientIP = ClientInfo.getPlayerIP();
    private int playerNumber;

    public InetAddress getClientIP(){
        return clientIP;
    }

    public void setPlayer(int n){
        playerNumber = n;
    }

    public int getPlayerNumber(){
        return playerNumber;
    }
    //OUTPUTS
    /*
    public void translate(){
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            locY++;
            System.out.println("UP");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            locX--;
            System.out.println("DOWN");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            locX--;
            System.out.println("LEFT");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            locX++;
            System.out.println("RIGHT");
        }
    } */
}
