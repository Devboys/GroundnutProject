package Core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.OrthographicCamera;

import static com.badlogic.gdx.Input.Keys.*;

public class ModelTest2D implements ApplicationListener{

    private OrthographicCamera cam;
    private static final int FRAME_COLS = 2, FRAME_ROWS = 4; // Something else??!
    private SpriteBatch spriteBatch;
    //private TextureAtlas textureAtlas;
    private Animation<TextureRegion> walkAnimation;
    private Texture atlas;
    private Texture background;
    private float state = 0; //frame time
    private float speed = 0.075f;
    private static int x;
    private static int y;
    private int[][] playerPos;
    private static String posString;

    @Override
    public void create() {

        //playerPos = new int[2][4];

        //playerPos[0][0] = 250;
        //textureAtlas = new TextureAtlas(Gdx.files.internal("dude.png"));
        //animation = new Animation(1 / 30f, textureAtlas.getRegions());
        atlas = new Texture(Gdx.files.internal("stolencat.png"));
        background = new Texture(Gdx.files.internal("background.jpg"));

        TextureRegion[][] temporary = TextureRegion.split(atlas, atlas.getHeight() / FRAME_COLS,
                atlas.getWidth() / FRAME_ROWS);

        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for(int i = 0; i < FRAME_ROWS; i++){
            for(int j = 0; j < FRAME_COLS; j++){
                walkFrames[index++] = temporary[i][j];
            }
        }

        cam = new OrthographicCamera(800,600);
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();

        walkAnimation = new Animation<TextureRegion>(speed, walkFrames);

        x = 0;
        y = 0;

        spriteBatch = new SpriteBatch();
        state = 0f;
    }

    public void renderBackground(){
        spriteBatch.draw(background,0,0);
    }

    @Override
    public void render(){
        //setPosition(playerPos);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Gdx.gl.glClearColor(0,0,0,1);
        translate();
        cam.update();
        spriteBatch.setProjectionMatrix(cam.combined); //WTF??!?!
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        state += Gdx.graphics.getDeltaTime();

        TextureRegion currentFrame = walkAnimation.getKeyFrame(state, true);
        spriteBatch.begin();
        renderBackground();
        //spriteBatch.draw(spriteBatch); eeeeh
        spriteBatch.draw(currentFrame, x, y); //gief animation animation.getKeyFrame(tid, loop)
        //spriteBatch.
        spriteBatch.end();
    }

    public void translate(){
        if(Gdx.input.isKeyPressed(DOWN)){
            //cam.translate(0,-5,0);
            y -= 5;
        }
        if(Gdx.input.isKeyPressed(UP)){
            //cam.translate(0,5,0);
            y += 5;
        }
        if(Gdx.input.isKeyPressed(RIGHT)){
            //cam.translate(5,0,0);
            x += 5;
        }
        if(Gdx.input.isKeyPressed(LEFT)){
            //cam.translate(-5,0,0);
            x -= 5;
        }

      /*  cam.position.x = MathUtils.clamp(cam.position.x, Gdx.graphics.getWidth() / 2f,
                Gdx.graphics.getHeight() / 2f);
        cam.position.y = MathUtils.clamp(cam.position.y, Gdx.graphics.getWidth() / 2f,
                Gdx.graphics.getHeight() / 2f); // WTF IS THIS SHIIIIIIIIIIIIIIIIIIIIT*/
    }

    public void setPosition(int[][] position){
        x = position[0][0];
        y = position[0][1];
    }

    public static String getPosition(){
        posString = "-coords|" + Integer.toString(x) + "," + Integer.toString(y) + "|";
        System.out.println(posString);
        return posString;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public void dispose(){
        spriteBatch.dispose();
        atlas.dispose();
    }

    @Override
    public void resize(int width, int height){

    }

    @Override
    public void pause(){

    }

    @Override
    public void resume(){

    }
}