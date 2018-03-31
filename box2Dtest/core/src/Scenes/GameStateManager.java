package Scenes;

import Scenes.Scene;
import Scenes.TestScene;

public class GameStateManager {

    TestScene mScene;

    //Scene Management
    Scene currentScene;

    public GameStateManager(){
        mScene = new TestScene();
    }

    public void init(){
        mScene.init();
    }

    public void update(){
        mScene.update();
    }

    public void render() {
        mScene.render();
    }
}
