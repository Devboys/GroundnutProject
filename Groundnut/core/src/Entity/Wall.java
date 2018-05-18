package Entity;

import Core.GameThread;
import Scenes.SceneManager;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Wall implements Entity {

    private int xLoc;
    private int yLoc;
    private int width;
    private int height;

    private Body groundBody;

    public Wall(int x, int y, int w, int h){
        xLoc = x;
        yLoc = y;
        width = w;
        height = h;
    }

    @Override
    public void init() {
        setupPhysics();
    }

    @Override
    public void update(SceneManager gsm) {}

    @Override
    public void render() {}

    @Override
    public void destroy() {
        GameThread.theWorld.destroyBody(groundBody);
    }

    private void setupPhysics(){
        //define floor(static)
        BodyDef groundDef = new BodyDef();
        groundDef.position.set(xLoc, yLoc);

        groundBody = GameThread.theWorld.createBody(groundDef);
        groundBody.setType(BodyDef.BodyType.StaticBody);

        PolygonShape groundBox = new PolygonShape();

        groundBox.setAsBox(width, height);
        groundBody.createFixture(groundBox, 0.0f);
        groundBox.dispose();
    }
}
