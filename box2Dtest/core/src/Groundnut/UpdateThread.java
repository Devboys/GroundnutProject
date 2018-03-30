package Groundnut;


import Constants.RunConstants;
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
    private static final int yAcceleration = 0;

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

        double frameStartTime = System.currentTimeMillis();
        double frameDuration;
        double remainder;
        while(running){
            try {
                frameStartTime = System.currentTimeMillis();

                update();

                frameDuration = System.currentTimeMillis() - frameStartTime;
                remainder = (1f/RunConstants.MAX_UPS - (frameDuration / 1000f)) * 1000f;
                if (remainder > 0) {
                    Thread.sleep((long) remainder);
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public void close(){
        running = false;
    }

    private void update() {
        gameStateManager.update();
        stepWorld();

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
