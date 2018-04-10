package Entity;

import Scenes.GameStateManager;

public interface Entity {
    void init();
    void update(GameStateManager gsm);
    void render();
    void destroy();
}
