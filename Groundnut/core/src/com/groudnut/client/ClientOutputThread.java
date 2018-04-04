package com.groudnut.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.groudnut.server.NetworkHandler;
import com.badlogic.gdx.input.*;

public class ClientOutputThread extends Thread {

    private int serverPort = NetworkHandler.getServerPort();
    private DatagramSocket socket =  NetworkHandler.getSocket();
    private byte[] buffer = new byte[256];
    private InetAddress serverAddress;


    int locY = 100;
    int locX = 50;

    public ClientOutputThread(){
        try {
            serverAddress = InetAddress.getByName("127.0.0.1");         //SERVER / HOST IP
        }catch(IOException e){}
    }

    public void run() {
        try {
            translate();
            String message = "@" +locX + "@" + locY ;
            buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);
            socket.send(packet);
            System.out.println("Message sent to server: " + packet.getSocketAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //OUTPUTS
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
    }
}
