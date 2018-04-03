package Scenes;

import Entities.Entity;
import com.badlogic.gdx.Game;

import java.util.ArrayList;

public abstract class Scene {

    ArrayList<Entity> entities;


    protected Scene(){
        entities = new ArrayList<Entity>();
    }
    public abstract void init();
    public abstract void update(GameStateManager gsm);
    public abstract void render();
}
