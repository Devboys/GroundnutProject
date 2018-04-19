package Entity;

import Input.PlayerInputHandler;
import com.badlogic.gdx.Gdx;

public class Player extends Unit{

    private String name = "Player";

    public Player(int xLoc, int yLoc, float size) {
        super(xLoc, yLoc, size);
    }

    public String getName() {
        return name;
    }
}
