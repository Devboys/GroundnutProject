package Entities;

import Groundnut.UpdateThread;
import com.badlogic.gdx.physics.box2d.*;

public class Player implements Entity {
    int xLoc;
    int yLoc;

    //box2D Physics collision
    Body playerCollider;
    Fixture playerFixture;

    public Player(int initX, int initY) {
        xLoc = initX;
        yLoc = initY;
    }

    public void init() {
        setupPhysics();
    }

    @Override
    public void update() {
        //handle input (change collider position)

        //get positions from collider

        //change sprite-drawing position to match new collider positions
    }

    @Override
    public void render() {}

    private void setupPhysics() {
        //Define player body
        BodyDef pBodyDef = new BodyDef();
        pBodyDef.type = BodyDef.BodyType.DynamicBody;
        pBodyDef.position.set(xLoc, yLoc);
        playerCollider = UpdateThread.theWorld.createBody(pBodyDef);

        //define player fixture
        FixtureDef pFixDef = new FixtureDef();
        CircleShape circle = new CircleShape();
        circle.setRadius(12f);
        pFixDef.shape = circle;
        pFixDef.density = 1f;
        pFixDef.friction = 0.4f;
        pFixDef.restitution = 0.8f;
        playerFixture = playerCollider.createFixture(pFixDef);
        circle.dispose();
    }
}
