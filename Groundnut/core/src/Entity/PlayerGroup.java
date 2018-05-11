package Entity;

import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;
import java.util.List;

public class PlayerGroup {

    private final int MAX_PLAYERS = 4;
    private Player[] players;
    private int playersInserted;

    private final int initX = -50; //Start player outside of screen.
    private final int initY = -50; //Start player outside of screen.

    public PlayerGroup(){
        players = new Player[MAX_PLAYERS];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(new Vector2(initX, initY));
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
        Vector2 initPos = new Vector2(50*playersInserted, 50*playersInserted);
        players[index].setPos(initPos);
    }

    public void switchPlayerIndexes(int switchFrom, int switchTo){
        Player tempPlayer = players[switchFrom];
        players[switchFrom] = players[switchTo];
        players[switchTo] = tempPlayer;

        //switch player positions
    }
}