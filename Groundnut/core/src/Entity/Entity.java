package Entity;

import Scenes.SceneManager;

public interface Entity {
    void init();
    void update(SceneManager gsm);
    void render();
    void destroy();
}
