package Entity;

import Input.PlayerInput;
import Scenes.SceneManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import static Core.GameThread.theWorld;

public abstract class Unit implements Entity{

    private static final int MOVE_SPEED = 100;

    private int xLoc;
    private int yLoc;

    private Vector2 unitPos;

    private Body unitCollider;
    private BodyDef bodyDef;
    private CircleShape circle;
    private float circleSize;
    private FixtureDef fixDef;

    private PlayerInput inputSource;

    public Unit(int initX, int initY){
        this.xLoc = initX;
        this.yLoc = initY;
    }

    public Unit(int initX, int initY, float size){
        this.xLoc = initX;
        this.yLoc = initY;
        this.circleSize = size;
    }

    @Override public void init(){ this.setupPhysics(); }

    @Override public void update(SceneManager gsm){
        unitCollider.setLinearVelocity(new Vector2(0,0));
        if(inputSource!= null) {
            move();
        }
    }

    @Override public void render(){}

    @Override public void destroy(){ theWorld.destroyBody(unitCollider);}

    private void setupPhysics() {
        //Body definition
        this.bodyDef = new BodyDef();
        this.bodyDef.type = BodyDef.BodyType.DynamicBody;
        this.bodyDef.position.set(this.xLoc, this.yLoc);
        this.unitCollider = theWorld.createBody(this.bodyDef);

        //Shape
        this.circle = new CircleShape();
        this.circle.setRadius(this.circleSize);

        //Fixture definition
        this.fixDef = new FixtureDef();
        this.fixDef.shape = this.circle;
        this.fixDef.density = 0;
        this.fixDef.friction = 0;
        this.fixDef.restitution = 0f;

        //Create fixture
        this.unitCollider.createFixture(this.fixDef);
        this.circle.dispose();
    }

    private void move(){
        this.unitPos = this.unitCollider.getPosition();
        //NORTH
        if(inputSource.isUp()){
            this.unitCollider.applyLinearImpulse(new Vector2(0, MOVE_SPEED), this.unitPos, true);
        }
        //SOUTH
        if(inputSource.isDown()){
            this.unitCollider.applyLinearImpulse(new Vector2(0, -MOVE_SPEED), this.unitPos, true);
        }
        //EAST
        if(inputSource.isRight()) {
            this.unitCollider.applyLinearImpulse(new Vector2(MOVE_SPEED, 0), this.unitPos, true);
        }
        //WEST
        if(inputSource.isLeft()) {
            this.unitCollider.applyLinearImpulse(new Vector2(-MOVE_SPEED, 0), this.unitPos, true);
        }
    }

    public Vector2 getUnitPosition(){ return this.unitCollider.getPosition(); }

    public void setInputSource(PlayerInput p){
        inputSource = p;
    }
}
