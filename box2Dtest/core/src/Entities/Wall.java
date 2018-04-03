package Entities;

import Constants.ScreenConstants;
import Groundnut.UpdateThread;
import Scenes.GameStateManager;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Wall implements Entity {

    private int xLoc;
    private int yLoc;
    private int width;
    private int height;

    public Wall(int x, int y, int w, int h){
        xLoc = x;
        yLoc = y;
        width = w;
        height = h;
    }

    @Override
    public void init() {
        setupPhysics();
    }

    @Override
    public void update(GameStateManager gsm) {}

    @Override
    public void render() {}

    private void setupPhysics(){
        //define floor(static)
        BodyDef groundDef = new BodyDef();
        groundDef.position.set(xLoc, yLoc);

        Body groundBody = UpdateThread.theWorld.createBody(groundDef);
        groundBody.setType(BodyDef.BodyType.StaticBody);

        PolygonShape groundBox = new PolygonShape();

        groundBox.setAsBox(width, height);
        groundBody.createFixture(groundBox, 0.0f);
        groundBox.dispose();
    }
}
