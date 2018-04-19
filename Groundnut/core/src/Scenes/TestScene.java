package Scenes;

import Constants.ScreenConstants;
import Entity.*;
import Input.PlayerInputHandler;

public class TestScene extends Scene {

    private int wallSize = 10;

    private Wall northBorder;
    private Wall southBorder;
    private Wall eastBorder;
    private Wall westBorder;

    private static Player[] players;
    private static Enemy[] enemies;
    private static UnitHandler uh;
    private PlayerInputHandler pih;

    public TestScene(){
        super();
        pih = new PlayerInputHandler();
        northBorder = new Wall(0, ScreenConstants.CAM_HEIGHT - wallSize, ScreenConstants.CAM_WIDTH, wallSize);
        southBorder = new Wall(0, wallSize, ScreenConstants.CAM_WIDTH, wallSize);
        eastBorder = new Wall((ScreenConstants.CAM_WIDTH - wallSize), 0, wallSize, ScreenConstants.CAM_HEIGHT);
        westBorder = new Wall(wallSize, 0, wallSize, ScreenConstants.CAM_HEIGHT);
        uh = new UnitHandler(4,2);
        pih.setPlayer(players[1]);
        players = uh.getPlayers();
        enemies = uh.getEnemies();

        entities.add(northBorder);
        entities.add(southBorder);
        entities.add(eastBorder);
        entities.add(westBorder);
        for(int i = 0; i < players.length; i++){
            entities.add(players[i]);
        }
        for(int i = 0; i < enemies.length; i++){
            entities.add(enemies[i]);
        }
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

    public static UnitHandler getUh() {
        return uh;
    }

    public PlayerInputHandler getPih() {
        return pih;
    }
}
