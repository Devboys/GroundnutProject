package ServerNetworking.LobbyServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class LobbyServerMain {

    private static final int serverPort = 1102;
    private static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        Socket newPlayerSocket;

        LobbyServerWriter serverWriter = new LobbyServerWriter();
        serverWriter.start();

        try {
            serverSocket = new ServerSocket(serverPort);

        }catch (IOException e) {
            System.out.println("Error creating lobby");
            System.exit(0);
            e.printStackTrace();
        }

        while (true) {
            try {
                newPlayerSocket = serverSocket.accept();
                serverWriter.addNewPlayer(newPlayerSocket);

                System.out.println("socket accepted");

            } catch (IOException e) {
                System.out.println("Error accepting socket");
                System.exit(0);
                e.printStackTrace();
            }
//            //add player socket
//            serverWriter.sockets.add(newPlayerSocket);
//
//            //add outputstream of playersocket
//            serverWriter.os.add(serverWriter.sockets.get(serverWriter.usernumber).getOutputStream());
//
//            //add inputstream of playersocket
//            serverWriter.pw.add(new PrintWriter(serverWriter.os.get(serverWriter.usernumber)));
//
//            //add buffered-reader of playersocket
//            serverWriter.bir.add(new BufferedReader(new InputStreamReader(serverWriter.sockets.get(serverWriter.usernumber).getInputStream())));
//
//            //dunno why this
//            serverWriter.newsarray.add("derp");
//            serverWriter.oldsarray.add("derp");
//
//            //increment userNumber after the fact - gotta find out why.
//            serverWriter.usernumber++;
//
//            //add corresponding username to usernames list
//            serverWriter.usernames.add("user" + serverWriter.usernumber);
//
//            //create new listener for player and start it
//            LobbyServerListener serverListener = new LobbyServerListener(serverWriter, serverWriter.usernumber);
//            serverListener.start();
//
//            //test println
//            System.out.println("user " + serverWriter.usernumber);
        }
    }
}