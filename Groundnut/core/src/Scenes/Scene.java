package Scenes;

import Entity.Entity;

import java.util.ArrayList;

/**Abstract Scene object that adds an Entity-arraylist to all Scenes.
 * SceneManager will only accept objects that extends this*/
public abstract class Scene {

    protected ArrayList<Entity> entities;

    protected Scene(){
        entities = new ArrayList<Entity>();
    }
    public abstract void init();
    public abstract void update(SceneManager gsm);
    public abstract void render();
    public abstract void destroy();
}
