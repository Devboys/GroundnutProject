package Input;

import java.io.Serializable;

public class PlayerInput implements Serializable {

    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;

    //make singleton BAD IDEA, NEED MORE THAN ONE ON SERVERSIDE
    private static PlayerInput instance;
    private PlayerInput(){}
    public static PlayerInput getInstance() {
        if(instance == null){
            instance = new PlayerInput();
        }
        return instance;
    }

    public boolean isUp() { return up; }
    public void setUp(boolean up) { this.up = up; }

    public boolean isDown() { return down; }
    public void setDown(boolean down) { this.down = down; }

    public boolean isLeft() { return left; }
    public void setLeft(boolean left) { this.left = left; }

    public boolean isRight() { return right; }
    public void setRight(boolean right) { this.right = right; }


}
