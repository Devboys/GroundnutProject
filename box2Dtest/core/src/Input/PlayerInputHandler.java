package Input;

import Entities.Player;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class PlayerInputHandler extends InputAdapter {

    Player player;

    public PlayerInputHandler(Player p){
        player = p;
    }

    @Override
    public  boolean keyDown(int keyCode){

        switch (keyCode){
            case Input.Keys.W:
                player.setMovingNorth(true);
                break;
            case Input.Keys.S:
                player.setMovingSouth(true);
                break;
            case Input.Keys.D:
                player.setMovingEast(true);
                break;
            case Input.Keys.A:
                player.setMovingWest(true);
                break;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keyCode){

        switch (keyCode){
            case Input.Keys.W:
                player.setMovingNorth(false);
                break;
            case Input.Keys.S:
                player.setMovingSouth(false);
                break;
            case Input.Keys.D:
                player.setMovingEast(false);
                break;
            case Input.Keys.A:
                player.setMovingWest(false);
                break;
        }

        return true;
    }

    @Override
    public boolean keyTyped(char character){
        return true;
    }
}
