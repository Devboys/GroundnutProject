package ServerNetworking.LobbyServer;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
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

    public static ArrayList<String> servernames = new ArrayList<>();
    public static ArrayList<ArrayList<InetAddress>> games = new ArrayList<>();
    public static ArrayList<ArrayList<PrintWriter>> gamesPW = new ArrayList<>();

    public int usernumber = 0;
    public int gameNumber = 0;
    public Boolean NameNotTaken;

    public void run(){
        System.out.println("ServerMain running");
        while(true){
            //System.out.println(usernumber);
            try {
                this.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(int i = 0; i < usernumber; i++){
                if(newsarray.get(i).equals(oldsarray.get(i))){
                }
                else if(newsarray.get(i).startsWith("Host ")){
                    String temp = newsarray.get(i).substring(5);
                    NameNotTaken = true;
                    for(int j = 0; j <servernames.size(); j++){
                        if(temp.equals(servernames.get(j))){
                            NameNotTaken = false;
                        }
                    }
                    if(NameNotTaken == true){
                        servernames.add(temp);
                        System.out.println("socket size: "+ sockets.size() +" gameNumber: "+gameNumber+" Address: "+sockets.get(i).getInetAddress());
                        games.add(new ArrayList<InetAddress>());
                        games.get(gameNumber).add(sockets.get(i).getInetAddress());
                        gamesPW.add(new ArrayList<PrintWriter>());
                        gamesPW.get(gameNumber).add(pw.get(i));
                        gameNumber++;
                        pw.get(i).println("You've Hosted "+temp);
                        pw.get(i).flush();
                    }
                }
                else if(newsarray.get(i).startsWith("Start")){
                    System.out.println("Start requested");
                    for(int j = 0; j < games.size(); j++){
                        if(sockets.get(i).getInetAddress().equals(games.get(j).get(0))){
                            System.out.println("Users Game Found");
                            for(int k = 0; k < games.get(j).size(); k++){
                                System.out.println("Sending IP");
                                pw.get(i).print("#S "+games.get(j).get(k).toString() + " ");
                            }
                            pw.get(i).println();
                            pw.get(i).flush();
                            for(int k = 0; k < gamesPW.get(j).size(); k++){
                                gamesPW.get(j).get(k).println("#C "+games.get(j).get(0)+ " " +k);
                                gamesPW.get(j).get(k).flush();
                            }
                        }
                    }
                }
                else if(newsarray.get(i).startsWith("Join ")){
                    System.out.println("Join requested");
                    String temp = newsarray.get(i).substring(5);
                    for(int j = 0; j < servernames.size(); j++){
                        if(temp.equals(servernames.get(j))){
                            games.get(j).add(sockets.get(i).getInetAddress());
                            gamesPW.get(j).add(pw.get(i));
                            pw.get(i).println("You've joined "+servernames.get(j));
                            pw.get(i).flush();
                        }
                    }
                }
                else if(newsarray.get(i).equals("getServers")){
                    for(int j = 0; j < servernames.size();j++){
                        pw.get(i).println(servernames.get(j));
                        pw.get(i).flush();
                    }
                }
                else{
                    for(int j = 0; j < usernumber; j++){
                        pw.get(j).println(usernames.get(i)+": "+newsarray.get(i));
                        pw.get(j).flush();
                    }
                }
                oldsarray.set(i, newsarray.get(i));
            }
        }
    }
}
