package Scenes;

import Entities.Entity;

import java.util.ArrayList;

public abstract class Scene {

    ArrayList<Entity> entities;

    protected Scene(){
        entities = new ArrayList<Entity>();
    }
    public abstract void init();
    public abstract void update();
    public abstract void render();
}
