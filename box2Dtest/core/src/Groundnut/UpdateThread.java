package Groundnut;


import Constants.ScreenConstants;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * This thread handles all game logic updates (positions, collisions, interactions, etc)
 */
public class UpdateThread extends Thread{

    //physics Stepping variables
    static final float STEP_TIME = 1f / 60f;
    static final int VELOCITY_ITERATIONS = 6;
    static final int POSITION_ITERATIONS = 2;
    float accumulator = 0;
    double frameTime;

    //Box2Dtest Variables
    public static World theWorld ;
    private static final int xAcceleration = 0;
    private static final int yAcceleration = -10;

    GameStateManager gameStateManager;

    boolean running;

    public UpdateThread(GameStateManager gm){
        Box2D.init();
        theWorld = new World(new Vector2(xAcceleration, yAcceleration), true);
        gameStateManager = gm;
        frameTime = System.currentTimeMillis();
        running = true;
    }

    @Override
    public void run() {
        while(running){
            gameStateManager.update();

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
}
