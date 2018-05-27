package Entity;

import Input.InputSource;
import Scenes.SceneManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import static Core.GameThread.theWorld;

public class Player implements Entity{

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

    /**@param initialPosition the initial position of the players collider.*/
    public Player(Vector2 initialPosition) {
        initPos = initialPosition;
        unitPos = initPos;
    }

    /**Queues the players position to be changed in the next Update() call. Overrides any previous queued position.
     * @param position the position to move the player to.*/
    public void setPos(Vector2 position){
        if(unitCollider != null) {
            queuedPos = position;
            transformInQueue = true;
        }
        else initPos = position;
    }

    /** @return The current position of the player*/
    public Vector2 getUnitPos() {
        return unitPos;
    }

    /**Sets the input-source of the player-object. The input-source is asked for a PlayerInput object in
     * each Update() call to move the player.
     * @param source the given input-source.*/
    public void setInputSource(InputSource source){
        inputSource = source;
    }

    /**Sets up and inserts the collider-representation of the player into the box2D physics-simulation.*/
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

    /**Updates all values relevant to the player. This includes moving the player.
     * @param sm the SceneManager that this entity's scene is tied to.*/
    @Override
    public void update(SceneManager sm) {
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

    /**Renders the players current sprite to the screen (not yet implemented)*/
    @Override public void render() {}

    /**Removes the players collider in the box2D physics-simulation.*/
    @Override public void destroy(){ theWorld.destroyBody(unitCollider);}

    /**Moves the player based on this players InputSource. Also updates the unitPos-field to match the
     * colliders position in the physics-simulation.*/
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
}
