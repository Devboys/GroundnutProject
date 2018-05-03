package ClientNetworking.LobbyClient;

import Desktop.DesktopLauncher2;
import com.sun.corba.se.impl.activation.ServerMain;

import java.io.IOException;
import java.net.InetAddress;

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
                if(newtext.startsWith("#")){
                    String SubstringOfCommand = newtext.substring(1);
                    if(SubstringOfCommand.startsWith("S")){
                        ServerMain newserver = new ServerMain();
                    }
                    else if(SubstringOfCommand.startsWith("C")){
                        String[] SubstringBreakdown = SubstringOfCommand.split(" ");
                        DesktopLauncher2 newClient = new DesktopLauncher2(InetAddress.getByName(SubstringBreakdown[1]),Integer.parseInt(SubstringBreakdown[2]));
                    }
                }
                else{
                    System.out.println(newtext);
                }
            } catch (IOException e) {
                System.out.println("killing Thread");
                isrunning = false;
                this.stop();
                e.printStackTrace();
            }
        }
    }
}
