package Groundnut;


import Constants.RunConstants;
import Scenes.GameStateManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * This thread handles all game logic updates (positions, collisions, interactions, etc)
 */
public class UpdateThread extends Thread{

    //physics Stepping variables
    static final int VELOCITY_ITERATIONS = 6;
    static final int POSITION_ITERATIONS = 2;
    float accumulator = 0;
    double frameTime;

    //Box2Dtest Variables
    public static World theWorld ;
    private static final int xAcceleration = 0;
    private static final int yAcceleration = 0;


    //time-step variables
    private static double optimalFrameDuration  = Math.pow(10,9) / RunConstants.UPS;
    static final float STEP_TIME = (float) (optimalFrameDuration / Math.pow(10, 9));
    private static final int MAX_FRAMESKIPS  = 10;

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

        double currentTime = System.nanoTime();
        double newTime;
        double frameTime;
        double timeSinceLastUpdate = 0.0;

        while(running){
            //Time previous frame duration
            newTime = System.nanoTime();
            frameTime = newTime - currentTime;
            currentTime = newTime;

            //keep track of how much time has passed since the last update was performed.
            timeSinceLastUpdate += frameTime;

            //When enough time has passed since the last update, update again. Cap the number of ticks pr. frame
            //to avoid spiraling to death.
            int iterations = 0;
            while(timeSinceLastUpdate >= optimalFrameDuration && iterations < MAX_FRAMESKIPS){
                update();

                //when updated, do a 'soft-reset' on time since last update, but leave remainder of un-simulated time.
                timeSinceLastUpdate -= optimalFrameDuration;
                iterations++;
            }
        }
    }

    public void close(){
        running = false;
    }

    private void update() {
        gameStateManager.update();
        theWorld.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

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
