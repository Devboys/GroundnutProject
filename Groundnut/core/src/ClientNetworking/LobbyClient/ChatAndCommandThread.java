package ClientNetworking.LobbyClient;

import java.io.IOException;

public class ChatAndCommandThread extends Thread {

    public boolean isrunning = true;
    public LobbyClientMain parent;
    public String newtext;



    public ChatAndCommandThread(LobbyClientMain parent){
        this.parent = parent;
    }

    public void run(){
        System.out.println("client thread running");
        while(isrunning == true){
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
