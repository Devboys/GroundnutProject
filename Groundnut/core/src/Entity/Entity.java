package Entity;

import Scenes.SceneManager;

public interface Entity {
    void init();
    void update(SceneManager sm);
    void render();
    void destroy();
}
