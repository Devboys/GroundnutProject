package com.mygdx.game;

import Constants.ScreenConstants;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class MyGdxGame extends ApplicationAdapter {

    OrthographicCamera camera;
    ExtendViewport viewPort;
    SpriteBatch batch;

    Texture banana;
    Texture cherry;

    World theWorld ;

    //debug rendering
    Box2DDebugRenderer testRender;

    //physics Stepping variables
    static final float STEP_TIME = 1f / 30f;
    static final int VELOCITY_ITERATIONS = 6;
    static final int POSITION_ITERATIONS = 2;
    float accumulator = 0;


    @Override
	public void create () {
        Box2D.init();

        theWorld = new World(new Vector2(0, -10), true);

        camera = new OrthographicCamera();
        viewPort = new ExtendViewport(ScreenConstants.CAM_WIDTH, ScreenConstants.CAM_HEIGHT, camera);

        batch = new SpriteBatch();
        banana = new Texture("banana.png");
        cherry = new Texture("cherries.png");

        testRender = new Box2DDebugRenderer();

        //define ball(dynamic)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(100, 300);

        Body body = theWorld.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(6f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.3f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.8f;

        Fixture fixture = body.createFixture(fixtureDef);

        //define floor(static)
        BodyDef groundDef = new BodyDef();
        groundDef.position.set(100, 200);

        Body groundBody = theWorld.createBody(groundDef);

        PolygonShape groundBox = new PolygonShape();

        groundBox.setAsBox(ScreenConstants.CAM_WIDTH, 10.0f);
        groundBody.createFixture(groundBox, 0.0f);
        groundBox.dispose();

        circle.dispose();
	}

	@Override
	public void render () {
        stepWorld();

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        testRender.render(theWorld, camera.combined);


//        Gdx.gl.glClearColor(0.57f, 0.77f, 0.85f, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        batch.begin();
//        batch.draw(banana, 0,0);
//        batch.draw(cherry, 100, 100);
//        batch.end();

	}

	@Override
	public void dispose () {
        batch.dispose();
        banana.dispose();
        theWorld.dispose();
        testRender.dispose();
	}

	@Override
	public void resize(int width, int height){
        viewPort.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    private void stepWorld() {
        float delta = Gdx.graphics.getDeltaTime();

        accumulator += Math.min(delta, 0.25f);

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;

            theWorld.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }
    }

}
