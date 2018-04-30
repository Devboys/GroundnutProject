package Entity;

import Input.PlayerInput;

public interface Controllable {
    void move();
    void setInputSource(PlayerInput p);
}
