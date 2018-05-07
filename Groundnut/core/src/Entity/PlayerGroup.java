package Entity;

import java.util.Arrays;
import java.util.List;

public class PlayerGroup {

    private final int MAX_PLAYERS = 4;
    private Player[] players;
    private int playersInserted;

    private final int initX = -50; //start player outside of screen.
    private final int initY = -50; //start player outside of screen.

    public PlayerGroup(){
        players = new Player[MAX_PLAYERS];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(initX, initY);
        }
    }

    public Player getPlayer(int index){
        return players[index];
    }

    public List toList(){
        return Arrays.asList(players);
    }

    public void insertPlayer(int index) throws IndexOutOfBoundsException{
        playersInserted++;
        players[index].setPos(50*playersInserted, 50*playersInserted);
    }


}
