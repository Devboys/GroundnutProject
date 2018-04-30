package Input;

import java.io.Serializable;

public class PlayerInput implements Serializable {

    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;

    public boolean isUp() { return up; }
    public void setUp(boolean up) { this.up = up; }

    public boolean isDown() { return down; }
    public void setDown(boolean down) { this.down = down; }

    public boolean isLeft() { return left; }
    public void setLeft(boolean left) { this.left = left; }

    public boolean isRight() { return right; }
    public void setRight(boolean right) { this.right = right; }


}
