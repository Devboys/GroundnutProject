package Desktop;

import Core.GameLauncher;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GameMain {

    public static void main(String[] args){
        try {
            GameLauncher.launch(InetAddress.getByName("localhost"), 1);
        }catch (UnknownHostException e){e.printStackTrace();}
    }

}
