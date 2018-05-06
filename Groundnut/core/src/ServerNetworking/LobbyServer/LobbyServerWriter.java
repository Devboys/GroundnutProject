package ServerNetworking.LobbyServer;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class LobbyServerWriter extends Thread {

    //communication arrays
    public static ArrayList<Socket> sockets = new ArrayList<Socket>();
    public static ArrayList<OutputStream> os = new ArrayList<OutputStream>();
    public static ArrayList<PrintWriter> pw = new ArrayList<PrintWriter>();
    public static ArrayList<BufferedReader> bir = new ArrayList<BufferedReader>();

    //command arrays, holds last command recieved from every listener on the listeners given index.
    public static ArrayList<String> newsarray = new ArrayList<String>();
    public static ArrayList<String> oldsarray = new ArrayList<String>();

    public static ArrayList<String> usernames = new ArrayList<String>();

    public static ArrayList<String> servernames = new ArrayList<>();
    public static ArrayList<ArrayList<InetAddress>> games = new ArrayList<>();
    public static ArrayList<ArrayList<PrintWriter>> gamesPW = new ArrayList<>();

    public int usernumber = 0;
    public int gameNumber = 0;
    public Boolean NameNotTaken;

    private static final int sleepTime = 500;
    private static boolean isRunning;

    //identifiers
    private static final String hostRequestIden = "Host ";
    private static final String startRequestIden = "Start";
    private static final String joinRequestIden = "Join ";
    private static final String serverListIden = "getServers";

    public void run(){
        System.out.println("ServerMain running");

        isRunning = true;
        while(isRunning){

            for(int i = 0; i < usernumber; i++){

                System.out.println("N" + newsarray.get(i));
                System.out.println("O" + oldsarray.get(i));

                if(!newsarray.get(i).equals(oldsarray.get(i))) {

                    if (newsarray.get(i).startsWith(hostRequestIden)) {
                        handleHostRequest(i);
                    }

                    else if (newsarray.get(i).startsWith(startRequestIden)) {
                        handleStartRequest(i);
                    }

                    else if (newsarray.get(i).startsWith(joinRequestIden)) {
                        handleJoinRequest(i);
                    }

                    else if (newsarray.get(i).equals(serverListIden)) {
                        handleServerListRequest(i);
                    }

                    else { //if not a request, is chat.
                        for (int j = 0; j < usernumber; j++) {
                            pw.get(j).println(usernames.get(i) + ": " + newsarray.get(i));
                            pw.get(j).flush();
                        }
                    }

                    //finaly, update old commands to match processed list.
                    oldsarray.set(i, newsarray.get(i));
                }
            }

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleHostRequest(int index){
        String temp = newsarray.get(index).substring(5);
        NameNotTaken = true;

        for (String servername : servernames) {
            if (temp.equals(servername)) {
                NameNotTaken = false;
            }
        }

        if(NameNotTaken){
            servernames.add(temp);
            System.out.println("socket size: "+ sockets.size() +" gameNumber: "+gameNumber+" Address: "+sockets.get(index).getInetAddress());
            games.add(new ArrayList<InetAddress>());
            games.get(gameNumber).add(sockets.get(index).getInetAddress());
            gamesPW.add(new ArrayList<PrintWriter>());
            gamesPW.get(gameNumber).add(pw.get(index));
            gameNumber++;

            pw.get(index).println("You've Hosted "+temp);
            pw.get(index).flush();
        }
    }

    private void handleJoinRequest(int index){
        System.out.println("Join requested");
        String temp = newsarray.get(index).substring(5);

        for(int j = 0; j < servernames.size(); j++){
            if(temp.equals(servernames.get(j))){

                games.get(j).add(sockets.get(index).getInetAddress());
                gamesPW.get(j).add(pw.get(index));
                pw.get(index).println("You've joined "+servernames.get(j));
                pw.get(index).flush();
            }
        }
    }

    private void handleStartRequest(int index){
        System.out.println("Start requested");

        for(int j = 0; j < games.size(); j++){
            if(sockets.get(index).getInetAddress().equals(games.get(j).get(0))){

                System.out.println("Users Game Found");

                for(int k = 0; k < games.get(j).size(); k++){
                    System.out.println("Sending IP");
                    pw.get(index).print("#S "+games.get(j).get(k).getHostAddress() + " ");
                }

                pw.get(index).println();
                pw.get(index).flush();

                for(int k = 0; k < gamesPW.get(j).size(); k++){
                    gamesPW.get(j).get(k).println("#C "+ games.get(j).get(0).getHostAddress() + " " +k);
                    gamesPW.get(j).get(k).flush();
                }
            }
        }
    }

    private void handleServerListRequest(int index){
        for(int j = 0; j < servernames.size();j++){
            pw.get(index).println(servernames.get(j));
            pw.get(index).flush();
        }
    }

    public void addNewPlayer(Socket playerSocket){
        try {
            sockets.add(playerSocket);

            OutputStream playerOS = playerSocket.getOutputStream();
            os.add(playerOS);
            pw.add(new PrintWriter(playerOS));

            bir.add(new BufferedReader(new InputStreamReader(playerSocket.getInputStream())));

            newsarray.add("initialized");
            oldsarray.add("initialized");
            usernumber++;

            usernames.add("user" + usernumber);

            LobbyServerListener serverListener = new LobbyServerListener(this, usernumber);
            serverListener.start();

            //test println
            System.out.println("user " + usernumber);

        }catch (IOException e){e.printStackTrace();}
    }

    public void close(){
        isRunning = false;
    }
}
