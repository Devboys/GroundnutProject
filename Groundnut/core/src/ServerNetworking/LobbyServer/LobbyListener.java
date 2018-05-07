package ServerNetworking.LobbyServer;

import java.io.IOException;

public class LobbyListener extends Thread{
    private LobbyWriter parent;
    private int threadnumber;

    private boolean isRunning;

    public LobbyListener(LobbyWriter p, int threadnumber){
        parent = p;
        this.threadnumber = threadnumber;
    }

    public void run(){
        System.out.println("Server chat thread "+threadnumber+" is running");

        isRunning = true;
        while(isRunning){
            System.out.println(parent.newsarray.get(threadnumber-1));

            try {
                parent.newsarray.set(threadnumber-1, parent.bir.get(threadnumber-1).readLine());

                Thread.sleep(500);
            } catch (IOException | InterruptedException e) {
                close();
                e.printStackTrace();
            }
        }
    }

    public void close(){
        isRunning = false;
    }
}
