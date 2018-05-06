package Scenes;

import Constants.ScreenConstants;
import Entity.*;
import Input.PlayerInputHandler;

import java.util.Arrays;

public class TestScene extends Scene {

    private int wallSize = 10;

    private Player[] players;
    private Enemy[] enemies;
    private UnitHandler uh;

    public TestScene(){
        super();
        Wall northBorder = new Wall(0, ScreenConstants.CAM_HEIGHT - wallSize, ScreenConstants.CAM_WIDTH, wallSize);
        Wall southBorder = new Wall(0, wallSize, ScreenConstants.CAM_WIDTH, wallSize);
        Wall eastBorder = new Wall((ScreenConstants.CAM_WIDTH - wallSize), 0, wallSize, ScreenConstants.CAM_HEIGHT);
        Wall westBorder = new Wall(wallSize, 0, wallSize, ScreenConstants.CAM_HEIGHT);

        uh = new UnitHandler(4,2);
        players = uh.getPlayers();
        enemies = uh.getEnemies();

        entities.add(northBorder);
        entities.add(southBorder);
        entities.add(eastBorder);
        entities.add(westBorder);

        entities.addAll(Arrays.asList(players));
        entities.addAll(Arrays.asList(enemies));
    }

    @Override public void init() {
        for(Entity e : entities){
            e.init();
        }
    }
    @Override public void update(GameStateManager gsm) {
        for(Entity e : entities){
            e.update(gsm);
        }
    }
    @Override public void render() {
        for(Entity e : entities){
            e.render();
        }
    }
    @Override public void destroy() {
        for(Entity e : entities){
            e.destroy();
        }
    }
}
