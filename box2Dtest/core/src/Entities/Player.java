package Entities;

import Groundnut.UpdateThread;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
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
        handleInput();

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
        pFixDef.density = 0;
        pFixDef.friction = 0;
        pFixDef.restitution = 0f;
        playerFixture = playerCollider.createFixture(pFixDef);
        circle.dispose();
    }

    private void handleInput(){
        //THESE ARE ALL TEMPORARY
        if(Gdx.input.isKeyJustPressed(Input.Keys.W)){
            playerCollider.applyLinearImpulse(new Vector2(0, 10000), new Vector2(xLoc, yLoc), true);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.S)){
            playerCollider.applyLinearImpulse(new Vector2(0, -10000), new Vector2(xLoc, yLoc), true);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.D)){
            playerCollider.applyLinearImpulse(new Vector2( 10000, 0), new Vector2(xLoc, yLoc), true);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.A)){
            playerCollider.applyLinearImpulse(new Vector2( -10000, 0), new Vector2(xLoc, yLoc), true);
        }
    }
}
