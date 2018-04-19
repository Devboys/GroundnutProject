package Core;

import Constants.ScreenConstants;
import Scenes.GameStateManager;
import Scenes.SceneNotLoadedException;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/** LibGDX main thread. Handles rendering and is also the start-point for the update thread.*/
public class RenderThread extends ApplicationAdapter {

    UpdateThread updateThread;

    OrthographicCamera camera;
    ExtendViewport viewPort;



    //debug rendering
    static String serverInfo = "SERVER INFO";
    static String clientInfo = "CLIENT INFO";
    SpriteBatch batch;
    BitmapFont font;
    Box2DDebugRenderer testRender;

    GameStateManager gameStateManager;

    @Override
	public void create () {
        batch = new SpriteBatch();
        font = new BitmapFont();
        gameStateManager = new GameStateManager();

        //create and start new thread to handle game logic updates;
        updateThread = new UpdateThread(gameStateManager);
        updateThread.start();
        setupCamera();

        //setup test rendering
        testRender = new Box2DDebugRenderer();
	}

	@Override
	public void render () {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        font.draw(batch, clientInfo, 40,50);
        font.draw(batch, serverInfo, 40,70);
        batch.end();
        try {
            testRender.render(UpdateThread.theWorld, camera.combined);
            gameStateManager.render();
        }catch (SceneNotLoadedException e){
            e.printStackTrace();
        }
	}

	@Override
	public void dispose () {
        updateThread.close();
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
    public static void setServerInfo(String si) {
        serverInfo = si;
    }

    public static void setClientInfo(String ci) {
        clientInfo = ci;
    }

}
