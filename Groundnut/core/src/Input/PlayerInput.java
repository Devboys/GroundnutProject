package Input;

import java.io.Serializable;

/**Simple data-collection object that keeps track of the player-controls.
 * Manipulated by PlayerInputHandler and sent over the network when needed.
 * Used in the movement of player-objects in the game*/
public class PlayerInput implements Serializable, InputSource{

    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;

    @Override public PlayerInput getInput() {
        return this;
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
