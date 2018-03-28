package Main;


import Constants.ScreenConstants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * This thread handles all game logic updates (positions, collisions, interactions, etc)
 */
public class UpdateThread extends Thread{

    //physics Stepping variables
    static final float STEP_TIME = 1f / 120f;
    static final int VELOCITY_ITERATIONS = 6;
    static final int POSITION_ITERATIONS = 2;
    float accumulator = 0;
    double frameTime;

    //Box2Dtest Variables
    static World theWorld ;
    private static final int xAcceleration = 0;
    private static final int yAcceleration = -10;

    boolean running;

    public UpdateThread(){
        running = true;
        setupPhysics();
        frameTime = System.currentTimeMillis();
    }

    @Override
    public void run() {
        while(running){

            stepWorld();

        }
    }


    public void close(){
        running = false;
    }

    private void stepWorld() {
        float delta =(float) ((System.currentTimeMillis() - frameTime) / 1000f);
        frameTime = System.currentTimeMillis();

        accumulator += Math.min(delta, 0.25f);

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;

            theWorld.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }
    }

    private void setupPhysics(){
        Box2D.init();
        theWorld = new World(new Vector2(xAcceleration, yAcceleration), true);

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
        groundBody.setType(BodyDef.BodyType.StaticBody);

        PolygonShape groundBox = new PolygonShape();

        groundBox.setAsBox(ScreenConstants.CAM_WIDTH, 10.0f);
        groundBody.createFixture(groundBox, 0.0f);
        groundBox.dispose();

        circle.dispose();
    }
}
