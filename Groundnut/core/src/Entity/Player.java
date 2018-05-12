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

    //TODO: RE-STRUCTURE PLAYERCLASS TO BETTER ALLOW FOR INSERTION INTO PHYSICS SIMULATION

    public int playerNum;

    private static final int MOVE_SPEED = 100;

    private Vector2 initPos;
    private Vector2 unitPos;

    private Vector2 queuedPos;
    private boolean transformInQueue = false;

    private Body unitCollider;

    private BodyDef bodyDef;
    private CircleShape circle;
    private float circleSize = 10;
    private FixtureDef fixDef;
    private InputSource inputSource;

    public Player(Vector2 initialPosition) {
        initPos = initialPosition;
        unitPos = initPos;
    }

    public void setPos(Vector2 position){
        if(unitCollider != null) {
            queuedPos = position;
            transformInQueue = true;
        }
        else initPos = position;
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
        bodyDef.position.set(initPos);
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
        //queue transforms to avoid calling .setTransform when world is simulating. Update() finishes before world
        // starts, so world should never be locked in here.
        if(transformInQueue){
            unitCollider.setTransform(queuedPos, unitCollider.getAngle());
            transformInQueue = false;
            queuedPos = null;
        }
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
