package Entities;

import Scenes.GameStateManager;

public interface Entity {
    void init();
    void update(GameStateManager gsm);
    void render();
}
