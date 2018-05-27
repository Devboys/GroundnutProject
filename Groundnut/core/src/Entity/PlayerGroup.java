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

    /**A grouping of all Player-entities in the game. These server as player-representations and the order of the list
     * should always reflect the order of the client-list on the server.
     * The group is filled with the maximum amount of players on creation,
     * but these are inactive and are placed at coordinates outside the window.*/
    public PlayerGroup(){
        players = new Player[MAX_PLAYERS];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(new Vector2(initX, initY));
        }
    }

    /**@param index the index of the player.
     * @return the player at the given index.*/
    public Player getPlayer(int index){
        return players[index];
    }

    /**Converts and returns the list of all player-entitie in the group as a List.*/
    public List toList(){
        return Arrays.asList(players);
    }

    /**Moves the player at the given index onto the screen.
     * @param index the index of the player to insert.
     * @throws IndexOutOfBoundsException
     */
    public void insertPlayer(int index) throws IndexOutOfBoundsException{
        playersInserted++;
        Vector2 initPos = new Vector2(50*playersInserted, 50*playersInserted);
        players[index].setPos(initPos);
    }

    /**Simple index-switch in the playergroup. This is usually done to allign
     * clientside playergroup with serverside playergroup.
     * @param switchFrom the index to switch from.
     * @param switchTo the index to switch to.*/
    public void switchPlayerIndexes(int switchFrom, int switchTo){
        Player tempPlayer = players[switchFrom];
        players[switchFrom] = players[switchTo];
        players[switchTo] = tempPlayer;
    }
}