package Code;

public class PublicMain {

    public static final int serverPort = 24000;
    public static final int clientPort = 24001;

    public static void main(String[] args){

            ServerListener listener = new ServerListener();
            ServerWriter writer = new ServerWriter();

            listener.start();
            writer.start();
    }



}