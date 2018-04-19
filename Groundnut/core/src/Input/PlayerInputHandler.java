package Input;

import ClientNetworking.GameClient.ClientGameState;
import Entity.Player;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class PlayerInputHandler extends InputAdapter {

    private Player player;

    private Boolean[] commands;

    public PlayerInputHandler(){
        commands = new Boolean[4];
        for (int i = 4; i < 4; i++) {
            commands[i] = false;
        }
    }

    public PlayerInputHandler(Player p){
        player = p;
        commands = new Boolean[4];
        for (int i = 4; i < 4; i++) {
            commands[i] = false;
        }
    }

    @Override
    public  boolean keyDown(int keyCode){

        switch (keyCode){
            case Input.Keys.W:
                player.setMovingNorth(true);
                commands[0] = true;
                ClientGameState.setCommandList(commands);
                break;
            case Input.Keys.S:
                player.setMovingSouth(true);
                commands[1] = true;
                ClientGameState.setCommandList(commands);
                break;
            case Input.Keys.D:
                player.setMovingEast(true);
                commands[2] = true;
                ClientGameState.setCommandList(commands);
                break;
            case Input.Keys.A:
                player.setMovingWest(true);
                commands[3] = true;
                ClientGameState.setCommandList(commands);
                break;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keyCode){

        switch (keyCode){
            case Input.Keys.W:
                player.setMovingNorth(false);
                commands[0] = false;
                break;
            case Input.Keys.S:
                player.setMovingSouth(false);
                commands[1] = false;
                ClientGameState.setCommandList(commands);
                break;
            case Input.Keys.D:
                player.setMovingEast(false);
                commands[2] = false;
                ClientGameState.setCommandList(commands);
                break;
            case Input.Keys.A:
                player.setMovingWest(false);
                commands[3] = false;
                ClientGameState.setCommandList(commands);
                break;
        }

        return true;
    }

    @Override
    public boolean keyTyped(char character){
        return false;
    }
    public  Boolean[] getCommands() {
        return commands;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
}
