package Code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class LobbyServerWriter extends Thread {

    public static ArrayList<Socket> sockets = new ArrayList<Socket>();
    public static ArrayList<OutputStream> os = new ArrayList<OutputStream>();
    public static ArrayList<PrintWriter> pw = new ArrayList<PrintWriter>();
    public static ArrayList<BufferedReader> bir = new ArrayList<BufferedReader>();
    public static ArrayList<String> newsarray = new ArrayList<String>();
    public static ArrayList<String> oldsarray = new ArrayList<String>();
    public static ArrayList<LobbyServerListener> chatthreads = new ArrayList<LobbyServerListener>();
    public static ArrayList<String> usernames = new ArrayList<String>();

    public int usernumber = 0;

    public void run(){
        System.out.println("ServerMain running");
        while(true){
            System.out.println(usernumber);
            try {
                this.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(int i = 0; i < usernumber; i++){
                if(newsarray.get(i).equals(oldsarray.get(i))){
                }
                else{
                    for(int j = 0; j < usernumber; j++){
                        pw.get(j).println(usernames.get(i)+": "+newsarray.get(i));
                        pw.get(j).flush();
                        oldsarray.set(i, newsarray.get(i));
                    }
                }
            }
        }
    }
}
