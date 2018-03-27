package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MyGdxGame extends ApplicationAdapter {

    //physicsVariables
    World theWorld;
    Box2DDebugRenderer testRend;
    float accumulator = 0;
    float TIME_STEP = 1/60f;
    OrthographicCamera cam;

    double startTime;
    
    int initX = 300;
    int initY = 100;
    int circleR = 6;

    //body objects
    BodyDef bodyDef = new BodyDef();


    ShapeRenderer renderer;
    
    PlayerObject pObject;

	
	@Override
	public void create () {
        pObject = new PlayerObject();
        pObject.locY = 100;
        pObject.locX = 300;
	    
	    //rendering
        renderer = new ShapeRenderer();

	    //box2D
	    theWorld = new World(new Vector2(0, -10), true);
	    testRend = new Box2DDebugRenderer();
	    cam = new OrthographicCamera();

	    bodyDef.type = BodyDef.BodyType.DynamicBody;
	    bodyDef.position.set(initX, initY);

        Body body = theWorld.createBody(bodyDef);
        body.setUserData(pObject);

        CircleShape circle = new CircleShape();
        circle.setRadius((float) circleR);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        Fixture fixture = body.createFixture(fixtureDef);

        circle.dispose();

	}

	@Override
	public void render () {

        double dt = System.currentTimeMillis() - startTime;
	    startTime = System.currentTimeMillis();
	    doPhysicsStep(dt);
	    
	    renderer.begin( ShapeRenderer.ShapeType.Filled);
	    renderer.setColor(Color.RED);
	    renderer.circle(initX, initY, circleR);
	    renderer.end();
	}
	
	@Override
	public void dispose () {

	}


    private void doPhysicsStep(double deltaTime) {
        // fixed <></>ime step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min((float)deltaTime, 250f);
        accumulator += frameTime;

        while (accumulator >= TIME_STEP) {
            theWorld.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }
    }
}
