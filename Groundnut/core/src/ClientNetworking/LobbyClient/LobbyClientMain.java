package ClientNetworking.LobbyClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class LobbyClientMain {

    BufferedReader bufferedreader;
    PrintWriter printwriter;

    public LobbyClientMain(){
        try {
            Socket socket = new Socket("localhost", 1102);
            printwriter = new PrintWriter(socket.getOutputStream());
            bufferedreader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch(UnknownHostException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        ChatAndCommandThread thread1 = new ChatAndCommandThread(this);
        thread1.start();
        CommandPromptListener thread2 = new CommandPromptListener(this);
        thread2.start();





    }

}
