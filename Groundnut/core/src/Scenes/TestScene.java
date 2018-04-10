package Scenes;

import Constants.EntityConstants;
import Constants.ScreenConstants;
import Entity.Entity;
import Entity.Player;
import Entity.Wall;

public class TestScene extends Scene {

    private int wallSize = 10;

    private Wall northBorder;
    private Wall southBorder;
    private Wall eastBorder;
    private Wall westBorder;

    private Player player;

    public TestScene(){
        super();

        northBorder = new Wall(0, ScreenConstants.CAM_HEIGHT - wallSize, ScreenConstants.CAM_WIDTH, wallSize);
        southBorder = new Wall(0, wallSize, ScreenConstants.CAM_WIDTH, wallSize);
        eastBorder = new Wall((ScreenConstants.CAM_WIDTH - wallSize), 0, wallSize, ScreenConstants.CAM_HEIGHT);
        westBorder = new Wall(wallSize, 0, wallSize, ScreenConstants.CAM_HEIGHT);
        player = new Player(EntityConstants.PLAYER_INIT_X, EntityConstants.PLAYER_INIT_Y);

        entities.add(northBorder);
        entities.add(southBorder);
        entities.add(eastBorder);
        entities.add(westBorder);
        entities.add(player);
    }

    @Override
    public void init() {
        for(Entity e : entities){
            e.init();
        }
    }

    @Override
    public void update(GameStateManager gsm) {
        for(Entity e : entities){
            e.update(gsm);
        }
    }

    @Override
    public void render() {
        for(Entity e : entities){
            e.render();
        }
    }

    @Override
    public void destroy() {
        for(Entity e : entities){
            e.destroy();
        }
    }
}
