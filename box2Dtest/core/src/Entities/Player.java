package Entities;

import Groundnut.UpdateThread;
import Input.PlayerInputHandler;
import Scenes.GameStateManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Player implements Entity {
    int xLoc;
    int yLoc;

    //box2D Physics collision
    protected Body playerCollider;
    private Fixture playerFixture;

    public Player(int initX, int initY) {
        xLoc = initX;
        yLoc = initY;
    }

    public void init() {
        setupPhysics();
    }

    @Override
    public void update(GameStateManager gsm) {
        //handle input (change collider position)

        //get positions from collider

        //change sprite-drawing position to match new collider positions
    }

    @Override
    public void render() {}

    @Override
    public void destroy() {
        UpdateThread.theWorld.destroyBody(playerCollider);
    }

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
        pFixDef.density = 0;
        pFixDef.friction = 0;
        pFixDef.restitution = 1f;
        playerFixture = playerCollider.createFixture(pFixDef);
        circle.dispose();
    }

}
