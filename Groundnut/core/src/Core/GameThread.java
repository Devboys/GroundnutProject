package Core;

import Constants.RunConstants;
import Constants.ScreenConstants;
import Scenes.GameStateManager;
import Scenes.SceneNotLoadedException;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/** LibGDX main thread. Handles rendering and is also the start-point for the update thread.*/
public class GameThread extends ApplicationAdapter {

    OrthographicCamera camera;
    ExtendViewport viewPort;

    //debug rendering
    String serverInfo = "SERVER INFO";
    String clientInfo = "CLIENT INFO";
    SpriteBatch batch;
    BitmapFont font;
    Box2DDebugRenderer testRender;

    GameStateManager gameStateManager;

    //time-step variables
    private static double optimalFrameDuration  = Math.pow(10,9) / RunConstants.UPS;
    static final float STEP_TIME = (float) (optimalFrameDuration / Math.pow(10, 9));
    private static final int MAX_FRAMESKIPS  = 10;
    double currentTime;
    double newTime;
    double frameTime;
    double timeSinceLastUpdate;

    //physics Stepping variables
    static final int VELOCITY_ITERATIONS = 6;
    static final int POSITION_ITERATIONS = 2;

    //Box2Dtest Variables
    public static World theWorld ;
    private static final int xAcceleration = 0;
    private static final int yAcceleration = 0;

    public GameThread(GameStateManager gsm){
        gameStateManager = gsm;
    }

    @Override
	public void create () {
        //update timing variables
        currentTime = System.nanoTime();
        timeSinceLastUpdate = 0.0;

        setupRendering();
        setupUpdate();
	}

	private void setupRendering(){
        //rendering stuff
        batch = new SpriteBatch();
        font = new BitmapFont();
        setupCamera();

        //setup test rendering
        testRender = new Box2DDebugRenderer();
    }
    private void setupUpdate(){
        //initialize independent values
        Box2D.init();
        theWorld = new World(new Vector2(xAcceleration, yAcceleration), true);

        //initialize dependent values.
        try {
            gameStateManager.init();
        } catch (SceneNotLoadedException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void render () {
        //update game in steps:
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
        renderGame();
	}

	public void update(){
        try {
            gameStateManager.update();
        } catch (SceneNotLoadedException e) {
            e.printStackTrace();
        }
        theWorld.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
    }

    public void renderGame(){
        //do the rendering
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        font.draw(batch, clientInfo, 40,50);
        font.draw(batch, serverInfo, 40,70);
        batch.end();

        try {
            testRender.render(theWorld, camera.combined);
            gameStateManager.render();
        }catch (SceneNotLoadedException e){
            e.printStackTrace();
        }
    }

	@Override
	public void dispose () {
        testRender.dispose();
	}

	@Override
	public void resize(int width, int height){
        viewPort.update(width, height, true);
    }

    private void setupCamera(){
        camera = new OrthographicCamera();
        viewPort = new ExtendViewport(ScreenConstants.CAM_WIDTH, ScreenConstants.CAM_HEIGHT, camera);
    }

    public static void setServerInfo(String serverInfo) {
        serverInfo = serverInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }

}
