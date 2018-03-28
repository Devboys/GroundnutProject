package Groundnut;

import Constants.EntityConstants;
import Entities.*;

public class GameStateManager {

    Player player;
    Wall floor;

    public GameStateManager(){
        player = new Player(EntityConstants.PLAYER_INIT_X, EntityConstants.PLAYER_INIT_Y);
        floor = new Wall();
    }

    public void init(){
        player.init();
        floor.init();
    }

    public void update(){
        player.update();
        floor.update();
    }

    public void render() {
        player.render();
        floor.render();
    }
}
