package Scenes;

import Constants.EntityConstants;
import Constants.ScreenConstants;
import Entities.Entity;
import Entities.Player;
import Entities.Wall;

public class TestScene extends Scene {

    int wallSize = 10;

    public TestScene(){
        super();

        entities.add(new Player(EntityConstants.PLAYER_INIT_X, EntityConstants.PLAYER_INIT_Y));
        entities.add(new Wall(0, wallSize, ScreenConstants.CAM_WIDTH, wallSize));
        entities.add(new Wall(wallSize, 0, wallSize, ScreenConstants.CAM_HEIGHT));
        entities.add(new Wall((ScreenConstants.CAM_WIDTH - wallSize), 0, wallSize, ScreenConstants.CAM_HEIGHT));
        entities.add(new Wall(0, ScreenConstants.CAM_HEIGHT - wallSize, ScreenConstants.CAM_WIDTH, wallSize));
    }

    @Override
    public void init() {
        for(Entity e : entities){
            e.init();
        }
    }

    @Override
    public void update() {
        for(Entity e : entities){
            e.update();
        }
    }

    @Override
    public void render() {
        for(Entity e : entities){
            e.render();
        }
    }
}
