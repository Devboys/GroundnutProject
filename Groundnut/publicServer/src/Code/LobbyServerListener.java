package Code;

import java.io.IOException;

public class LobbyServerListener extends Thread{
    public LobbyServerWriter parent;
    public int threadnumber;

    public LobbyServerListener(LobbyServerWriter p, int threadnumber){
        parent = p;
        this.threadnumber = threadnumber;
    }

    public void run(){
        System.out.println("Server chat thread "+threadnumber+" is running");
        while(true){
            //System.out.println(parent.newsarray.get(threadnumber-1));
            try {
                parent.newsarray.set(threadnumber-1, parent.bir.get(threadnumber-1).readLine());
                this.sleep(500);
            } catch (IOException e) {
                this.stop();
                e.printStackTrace();
            } catch (InterruptedException e) {
                this.stop();
                e.printStackTrace();
            }
        }
    }
}
