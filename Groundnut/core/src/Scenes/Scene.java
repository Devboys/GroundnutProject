package Scenes;

import Entity.Entity;

import java.util.ArrayList;

public abstract class Scene {

    ArrayList<Entity> entities;


    protected Scene(){
        entities = new ArrayList<Entity>();
    }
    public abstract void init();
    public abstract void update(SceneManager gsm);
    public abstract void render();
    public abstract void destroy();
}
