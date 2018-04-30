package Input;

import Entity.Player;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class PlayerInputHandler extends InputAdapter {

    private PlayerInput pInput;

    public PlayerInputHandler(PlayerInput pInput){
        this.pInput = pInput;
    }

    @Override public  boolean keyDown(int keyCode){
        switch (keyCode){
            case Input.Keys.W:
                pInput.setUp(true);
                break;
            case Input.Keys.S:
                pInput.setDown(true);
                break;
            case Input.Keys.D:
                pInput.setRight(true);
                break;
            case Input.Keys.A:
                pInput.setLeft(true);
                break;
        }
        return true;
    }

    @Override public boolean keyUp(int keyCode){
        switch (keyCode){
            case Input.Keys.W:
                pInput.setUp(false);
                break;
            case Input.Keys.S:
                pInput.setDown(false);
                break;
            case Input.Keys.D:
                pInput.setRight(false);
                break;
            case Input.Keys.A:
                pInput.setLeft(false);
                break;
        }
        return true;
    }

    public void setpInput(PlayerInput pInput) {
        this.pInput = pInput;
    }
}
