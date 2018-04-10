package ServerNetworking.LobbyServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class LobbyServerMain {
    public static ArrayList<LobbyServerListener> Threads;

    public static void main(String[] args) throws IOException {
        ServerSocket srv = null;
        Socket s = null;
        LobbyServerWriter server = new LobbyServerWriter();
        server.start();
        try {
            srv = new ServerSocket(1102);
        } catch (IOException e) {
            System.out.println("Error creating server");
            System.exit(0);
            e.printStackTrace();
        }
        while (true) {
            s = null;
            try {
                s = srv.accept();
                System.out.println("socket accepted");
            } catch (IOException e) {
                System.out.println("Error accepting socket");
                System.exit(0);
                e.printStackTrace();
            }
            server.sockets.add(s);
            server.os.add(server.sockets.get(server.usernumber).getOutputStream());
            server.pw.add(new PrintWriter(server.os.get(server.usernumber)));
            server.bir.add(new BufferedReader(new InputStreamReader(server.sockets.get(server.usernumber).getInputStream())));
            server.newsarray.add("derp");
            server.oldsarray.add("derp");
            server.usernumber++;
            server.usernames.add("user" + server.usernumber);
            LobbyServerListener g = new LobbyServerListener(server, server.usernumber);
            g.start();
            System.out.println("user " + server.usernumber);
        }
    }
}