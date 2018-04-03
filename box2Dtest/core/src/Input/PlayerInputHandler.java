package Input;

import Entities.ControllablePlayer;
import Entities.Player;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class PlayerInputHandler extends InputAdapter {

    ControllablePlayer player;

    public PlayerInputHandler(ControllablePlayer p){
        player = p;
    }

    @Override
    public  boolean keyDown(int keyCode){

        switch (keyCode){
            case Input.Keys.W:
                player.moveNorth(true);
                break;
            case Input.Keys.S:
                player.moveSouth(true);
                break;
            case Input.Keys.D:
                player.moveEast(true);
                break;
            case Input.Keys.A:
                player.moveWest(true);
                break;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keyCode){

        switch (keyCode){
            case Input.Keys.W:
                player.moveNorth(false);
                break;
            case Input.Keys.S:
                player.moveSouth(false);
                break;
            case Input.Keys.D:
                player.moveEast(false);
                break;
            case Input.Keys.A:
                player.moveWest(false);
                break;
        }

        return true;
    }

    @Override
    public boolean keyTyped(char character){
        return true;
    }
}
