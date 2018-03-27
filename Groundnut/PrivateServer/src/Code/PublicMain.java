package Code;

public class PublicMain {

    public static final int serverPort = 24000;
    public static final int clientPort = 24001;

    public static void main(String[] args){
        try {
            PublicServerThread theThread = new PublicServerThread();
            theThread.start();
        }catch(Exception e){
            System.out.println("REEEE");
        }
    }



}
