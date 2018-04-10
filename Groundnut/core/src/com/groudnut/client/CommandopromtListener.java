package com.groudnut.client;

import java.util.Scanner;

public class CommandopromtListener extends Thread {

    Scanner scanner;
    LobbyClientMain parent;


    public CommandopromtListener(LobbyClientMain parent){
        scanner = new Scanner(System.in);
        this.parent = parent;
    }
    public void run(){
        while(true){
            String line = scanner.nextLine();
            parent.printwriter.println(line);
            parent.printwriter.flush();
            System.out.println(line);
        }
    }


}
