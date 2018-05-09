package Settings;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class SettingsReader {

    private static final String SETTINGS_PATH = "Settings.txt";

    //Settings
    private static InetAddress LobbyHostIP;
    private static final String hostIPIdentifier = "LOBBY_IP";

    private SettingsReader(){} //private constructor to disable extension & instantiation.

    public static void initValues() throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader( new FileReader(SETTINGS_PATH));

        String readInput;
        //read LobbyHostIP
        try {
            while ((readInput = bufferedReader.readLine()) != null) { //read until no more lines in file

                String[] splitInput = readInput.split(":"); //split input into identifier and value.
                if (splitInput.length == 2) {
                    String variableIdentifier = splitInput[0];
                    String variableValue = splitInput[1];

                    //remove all spaces before value
                    while (variableValue.startsWith(" ")) {
                        variableValue = variableValue.substring(1);
                    }
                    //remove all spaces after value.
                    while (variableValue.endsWith(" ")){
                        variableValue = variableValue.substring(0, variableValue.length()-2);
                    }

                    //use identifier  to check what kind of variable we got, and treat accordingly.
                    switch (variableIdentifier){
                        case hostIPIdentifier:
                            LobbyHostIP = InetAddress.getByName(variableValue);
                            break;
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                bufferedReader.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static InetAddress getLobbyHostIP(){ return LobbyHostIP; }
}
