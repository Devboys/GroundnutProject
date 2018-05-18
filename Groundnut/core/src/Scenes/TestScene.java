package Scenes;

import Constants.ScreenConstants;
import Core.SimulationHandler;
import Entity.*;

public class TestScene extends Scene {

    private int wallSize = 10;

    public TestScene(){
        super();
        Wall northBorder = new Wall(0, ScreenConstants.CAM_HEIGHT - wallSize, ScreenConstants.CAM_WIDTH, wallSize);
        Wall southBorder = new Wall(0, wallSize, ScreenConstants.CAM_WIDTH, wallSize);
        Wall eastBorder = new Wall((ScreenConstants.CAM_WIDTH - wallSize), 0, wallSize, ScreenConstants.CAM_HEIGHT);
        Wall westBorder = new Wall(wallSize, 0, wallSize, ScreenConstants.CAM_HEIGHT);

        entities.add(northBorder);
        entities.add(southBorder);
        entities.add(eastBorder);
        entities.add(westBorder);

        //add all players
        entities.addAll(SimulationHandler.getInstance().getPlayers().toList());
    }

    @Override public void init() {
        for(Entity e : entities){
            e.init();
        }
    }
    @Override public void update(SceneManager gsm) {
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
