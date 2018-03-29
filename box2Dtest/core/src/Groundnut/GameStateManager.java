package Groundnut;

import Constants.EntityConstants;
import Constants.ScreenConstants;
import Entities.*;

import java.util.ArrayList;

public class GameStateManager {

    Player player;
    Wall floor;

    ArrayList<Entity> entities = new ArrayList<Entity>();

    int wallSize = 10;

    public GameStateManager(){
        entities.add(new Player(EntityConstants.PLAYER_INIT_X, EntityConstants.PLAYER_INIT_Y));
        entities.add(new Wall(0, wallSize, ScreenConstants.CAM_WIDTH, wallSize));
        entities.add(new Wall(wallSize, 0, wallSize, ScreenConstants.CAM_HEIGHT));
        entities.add(new Wall((ScreenConstants.CAM_WIDTH - wallSize), 0, wallSize, ScreenConstants.CAM_HEIGHT));
        entities.add(new Wall(0, ScreenConstants.CAM_HEIGHT - wallSize, ScreenConstants.CAM_WIDTH, wallSize));


    }

    public void init(){
        for(Entity e : entities){
            e.init();
        }
    }

    public void update(){
        for(Entity e : entities){
            e.update();
        }
    }

    public void render() {
        for(Entity e : entities){
            e.render();
        }
    }
}
