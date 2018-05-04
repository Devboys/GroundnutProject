package ClientNetworking.LobbyClient;

import java.io.PrintWriter;
import java.util.Scanner;

public class UserInputWriter extends Thread {

    private Scanner scanner; //reads user-input from command line
    private PrintWriter outputWriter; //prints commands to server.

    private boolean isRunning;

    public UserInputWriter(PrintWriter outputWriter){
        scanner = new Scanner(System.in);
        this.outputWriter = outputWriter;
    }

    public void run(){
        isRunning = true;

        while(isRunning){
            //read user input from console.
            String line = scanner.nextLine();

            //send console-input over TCP connection.
            outputWriter.println(line);
            outputWriter.flush();
        }
    }

    public void close(){
        isRunning = false;
    }


}
