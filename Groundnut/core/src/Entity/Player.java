package Entity;

import Input.InputSource;
import Input.PlayerInput;
import Scenes.GameStateManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import static Core.GameThread.theWorld;

public class Player implements Entity{

    public int playerNum;
    public Player(int xLoc, int yLoc) {}

    private static final int MOVE_SPEED = 100;

    private int xLoc;
    private int yLoc;

    private Vector2 unitPos;

    private Body unitCollider;

    private BodyDef bodyDef;
    private CircleShape circle;
    private float circleSize = 10;
    private FixtureDef fixDef;
    private InputSource inputSource;

    public void setPos(Vector2 position){
        unitPos = position;
    }

    public Vector2 getUnitPos() {
        return unitPos;
    }

    public void setInputSource(InputSource source){
        inputSource = source;
    }

    private void setupPhysics() {
        //Body definition
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(xLoc, yLoc);
        unitCollider = theWorld.createBody(bodyDef);

        //Shape
        circle = new CircleShape();
        circle.setRadius(circleSize);

        //Fixture definition
        fixDef = new FixtureDef();
        fixDef.shape = circle;
        fixDef.density = 0;
        fixDef.friction = 0;
        fixDef.restitution = 0f;

        //Create fixture
        unitCollider.createFixture(fixDef);
        circle.dispose();
    }

    @Override
    public void init() {
        setupPhysics();
    }

    @Override
    public void update(GameStateManager gsm) {
        unitCollider.setLinearVelocity(0, 0);

        if(inputSource!= null) {
            move();
        }
    }

    @Override
    public void render() {}

    @Override
    public void destroy(){ theWorld.destroyBody(unitCollider);}

    private void move(){
        this.unitPos = this.unitCollider.getPosition();
        //NORTH
        if(inputSource.getInput().isUp()){
            this.unitCollider.applyLinearImpulse(new Vector2(0, MOVE_SPEED), this.unitPos, true);
        }
        //SOUTH
        if(inputSource.getInput().isDown()){
            this.unitCollider.applyLinearImpulse(new Vector2(0, -MOVE_SPEED), this.unitPos, true);
        }
        //EAST
        if(inputSource.getInput().isRight()) {
            this.unitCollider.applyLinearImpulse(new Vector2(MOVE_SPEED, 0), this.unitPos, true);
        }
        //WEST
        if(inputSource.getInput().isLeft()) {
            this.unitCollider.applyLinearImpulse(new Vector2(-MOVE_SPEED, 0), this.unitPos, true);
        }
    }
    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }
}
