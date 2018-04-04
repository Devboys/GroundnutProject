package Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class MenuScene extends Scene {

    private int count = 0;

    public MenuScene() {
    }

    @Override
    public void init() {
        System.out.println(count);
    }

    @Override
    public void update(GameStateManager gsm) {
        count++;

        if(Gdx.input.isKeyJustPressed(Input.Keys.W)){
            gsm.switchScene(GameStateManager.Scenes.TEST);
        }
    }

    @Override
    public void render() {

    }

    @Override
    public void destroy() {

    }
}
