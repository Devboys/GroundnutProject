package Main;

import Constants.ScreenConstants;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class RenderThread extends ApplicationAdapter {

    UpdateThread updateThread;

    OrthographicCamera camera;
    ExtendViewport viewPort;

    //debug rendering
    Box2DDebugRenderer testRender;

    GameManager gameManager;

    @Override
	public void create () {
        gameManager = new GameManager();

        //create and start new thread to handle game logic updates;
        updateThread = new UpdateThread();
        updateThread.start();

        setupCamera();

        //setup test rendering
        testRender = new Box2DDebugRenderer();
	}

	@Override
	public void render () {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        testRender.render(UpdateThread.theWorld, camera.combined);
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
}
