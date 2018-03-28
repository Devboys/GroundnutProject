package Entities;

import Constants.ScreenConstants;
import Groundnut.UpdateThread;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Wall implements Entity {

    @Override
    public void init() {
        setupPhysics();
    }

    @Override
    public void update() {}

    @Override
    public void render() {}

    private void setupPhysics(){
        //define floor(static)
        BodyDef groundDef = new BodyDef();
        groundDef.position.set(100, 200);

        Body groundBody = UpdateThread.theWorld.createBody(groundDef);
        groundBody.setType(BodyDef.BodyType.StaticBody);

        PolygonShape groundBox = new PolygonShape();

        groundBox.setAsBox(ScreenConstants.CAM_WIDTH, 10.0f);
        groundBody.createFixture(groundBox, 0.0f);
        groundBox.dispose();
    }
}
