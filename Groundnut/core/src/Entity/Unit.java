package Entity;

import Core.UpdateThread;
import Scenes.GameStateManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public abstract class Unit implements Entity{

    private static final int MOVE_SPEED = 100;

    private int xLoc;
    private int yLoc;

    private boolean movingNorth;
    private boolean movingSouth;
    private boolean movingWest;
    private boolean movingEast;
    private Vector2 unitPos;

    private Body unitCollider;
    private BodyDef bodyDef;
    private CircleShape circle;
    private float circleSize;
    private FixtureDef fixDef;

    public Unit(){
    }

    public Unit(int initX, int initY){
        this.xLoc = initX;
        this.yLoc = initY;
    }

    public Unit(int initX, int initY, float size){
        this.xLoc = initX;
        this.yLoc = initY;
        this.circleSize = size;
    }

    public void init(){ this.setupPhysics(); }

    @Override
    public void update(GameStateManager gsm){
        this.unitCollider.setLinearVelocity(new Vector2(0,0));
        move();
    }

    @Override
    public void render(){}

    public void destroy(){ UpdateThread.theWorld.destroyBody(this.unitCollider);}

    private void setupPhysics() {
        //Body definition
        this.bodyDef = new BodyDef();
        this.bodyDef.type = BodyDef.BodyType.DynamicBody;
        this.bodyDef.position.set(this.xLoc, this.yLoc);
        this.unitCollider = UpdateThread.theWorld.createBody(this.bodyDef);

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
        if(movingNorth){
            this.unitCollider.applyLinearImpulse(new Vector2(0, MOVE_SPEED), this.unitPos, true);
        }
        //SOUTH
        if(movingSouth){
            this.unitCollider.applyLinearImpulse(new Vector2(0, -MOVE_SPEED), this.unitPos, true);
        }
        //EAST
        if(movingEast) {
            this.unitCollider.applyLinearImpulse(new Vector2(MOVE_SPEED, 0), this.unitPos, true);
        }
        //WEST
        if(movingWest) {
            this.unitCollider.applyLinearImpulse(new Vector2(-MOVE_SPEED, 0), this.unitPos, true);
        }
    }

    public Vector2 getUnitPosition(){ return this.unitCollider.getPosition(); }
    public void setMovingEast(boolean movingEast)   { this.movingEast = movingEast; }
    public void setMovingWest(boolean movingWest)   { this.movingWest = movingWest; }
    public void setMovingNorth(boolean movingNorth) { this.movingNorth = movingNorth; }
    public void setMovingSouth(boolean movingSouth) { this.movingSouth = movingSouth; }
}
