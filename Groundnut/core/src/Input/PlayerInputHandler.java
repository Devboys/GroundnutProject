package Input;

import ClientNetworking.GameClient.ClientGameState;
import ClientNetworking.GameClient.ClientOutput;
import Entity.Player;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import java.util.ArrayList;

public class PlayerInputHandler extends InputAdapter {

    Player player;

    private static Boolean[] commands = new Boolean[4];

    public PlayerInputHandler(Player p){
        player = p;
    }

    @Override
    public  boolean keyDown(int keyCode){
        switch (keyCode){
            case Input.Keys.W:
                player.setMovingNorth(true);
                commands[0] = true;
                break;
            case Input.Keys.S:
                player.setMovingSouth(true);
                commands[1] = true;
                break;
            case Input.Keys.D:
                player.setMovingEast(true);
                commands[2] = true;
                break;
            case Input.Keys.A:
                player.setMovingWest(true);
                commands[3] = true;
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keyCode){
        switch (keyCode){
            case Input.Keys.W:
                player.setMovingNorth(false);
                commands[0] = false;
                break;
            case Input.Keys.S:
                player.setMovingSouth(false);
                commands[1] = false;
                break;
            case Input.Keys.D:
                player.setMovingEast(false);
                commands[2] = false;
                break;
            case Input.Keys.A:
                player.setMovingWest(false);
                commands[3] = false;
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character){
        return false;
    }
    public static Boolean[] getCommands() {
        return commands;
    }
}
