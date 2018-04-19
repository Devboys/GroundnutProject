package Entity;

<<<<<<< HEAD
=======
import Core.GameThread;
import Core.UpdateThread;
>>>>>>> 1fafd57bb3d6b3cd8a960d1a25cef22bc0f69480
import Input.PlayerInputHandler;
import com.badlogic.gdx.Gdx;

public class Player extends Unit{

    private String name = "Player";

<<<<<<< HEAD
    public Player(int xLoc, int yLoc, float size) {
        super(xLoc, yLoc, size);
    }

    public String getName() {
        return name;
    }
=======
    private boolean movingEast;
    private boolean movingWest;
    private boolean movingNorth;
    private boolean movingSouth;

    private static final int MOVE_SPEED = 100;

    //box2D Physics collision
    private  Body playerCollider;

    public Player(int initX, int initY) {
        xLoc = initX;
        yLoc = initY;
    }

    public void init() {
        setupPhysics();
        Gdx.input.setInputProcessor(new PlayerInputHandler(this));
    }

    @Override
    public void update(GameStateManager gsm) {
        //handle input (apply forces)
        playerCollider.setLinearVelocity(new Vector2(0, 0));
        move();

        //get positions from collider

        //change sprite-drawing position to match new collider positions
    }

    @Override
    public void render() {}

    @Override
    public void destroy() {
        UpdateThread.theWorld.destroyBody(playerCollider);
    }

    private void setupPhysics() {
        //Define player body
        BodyDef pBodyDef = new BodyDef();
        pBodyDef.type = BodyDef.BodyType.DynamicBody;
        pBodyDef.position.set(xLoc, yLoc);
        playerCollider = GameThread.theWorld.createBody(pBodyDef);

        //define fixture Shape
        CircleShape circle = new CircleShape();
        circle.setRadius(12f);

        //define player fixture
        FixtureDef pFixDef = new FixtureDef();
        pFixDef.shape = circle;
        pFixDef.density = 0;
        pFixDef.friction = 0;
        pFixDef.restitution = 0f;

        //create fixture
        playerCollider.createFixture(pFixDef);
        circle.dispose();
    }

    private void move(){
        Vector2 playerPos = playerCollider.getPosition();
        //NORTH
        if(movingNorth){
            playerCollider.applyLinearImpulse(new Vector2(0, MOVE_SPEED), playerPos, true);
        }
        //SOUTH
        if(movingSouth){
            playerCollider.applyLinearImpulse(new Vector2(0, -MOVE_SPEED), playerPos, true);
        }
        //EAST
        if(movingEast) {
            playerCollider.applyLinearImpulse(new Vector2(MOVE_SPEED, 0), playerPos, true);
        }
        //WEST
        if(movingWest) {
            playerCollider.applyLinearImpulse(new Vector2(-MOVE_SPEED, 0), playerPos, true);
        }
    }

    public void setMovingEast(boolean movingEast)   { this.movingEast = movingEast; }
    public void setMovingWest(boolean movingWest)   { this.movingWest = movingWest; }
    public void setMovingNorth(boolean movingNorth) { this.movingNorth = movingNorth; }
    public void setMovingSouth(boolean movingSouth) { this.movingSouth = movingSouth; }
>>>>>>> 1fafd57bb3d6b3cd8a960d1a25cef22bc0f69480
}
