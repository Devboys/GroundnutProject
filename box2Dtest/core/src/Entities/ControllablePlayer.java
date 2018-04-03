package Entities;

import Input.PlayerInputHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class ControllablePlayer extends Player {
    public ControllablePlayer(int initX, int initY) {
        super(initX, initY);
        Gdx.input.setInputProcessor(new PlayerInputHandler(this));
    }


    public void moveEast(boolean t){
        Vector2 playerPos = new Vector2(xLoc, yLoc);

        if(t == true) {
            playerCollider.applyLinearImpulse(new Vector2(100, 0), playerPos, true);
        }
        else{
            playerCollider.setLinearVelocity(new Vector2(0, playerCollider.getLinearVelocity().y));
        }
    }
    public void moveWest(boolean t){

        Vector2 playerPos = new Vector2(xLoc, yLoc);
        if(t == true) {
            playerCollider.applyLinearImpulse(new Vector2(-100, 0), playerPos, true);
        }
        else{
            playerCollider.setLinearVelocity(new Vector2(0, playerCollider.getLinearVelocity().y));
        }
    }
    public void moveNorth(boolean t){
        Vector2 playerPos = new Vector2(xLoc, yLoc);
        if(t == true){
            playerCollider.applyLinearImpulse(new Vector2(0, 100), playerPos, true);
        }
        else{
            playerCollider.setLinearVelocity(new Vector2(playerCollider.getLinearVelocity().x, 0));
        }
    }
    public void moveSouth(boolean t){
        Vector2 playerPos = new Vector2(xLoc, yLoc);
        if(t == true){
            playerCollider.applyLinearImpulse(new Vector2(0, -100), playerPos, true);
        }
        else{
            playerCollider.setLinearVelocity(new Vector2(playerCollider.getLinearVelocity().x, 0));
        }
    }
}
