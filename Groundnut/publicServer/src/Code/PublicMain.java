package Code;

import java.io.IOException;

public class PublicMain {

    public static void main(String[] args){
        try {
            PublicServerThread serverThread = new PublicServerThread();
            serverThread.start();
        }catch (IOException e) {
            e.printStackTrace();
        }


    }



}
