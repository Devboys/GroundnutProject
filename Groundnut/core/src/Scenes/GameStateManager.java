package Scenes;

import java.lang.reflect.InvocationTargetException;

public class GameStateManager {

    //enum of possible states
    public enum Scenes {
        TEST(new TestScene()),
        MENU(new MenuScene());

        private final Scene sceneObject;
        Scenes(Scene sc){
            sceneObject = sc;
        }
        private Scene createNewScene(){

            try {
                return sceneObject.getClass().getConstructor().newInstance();
            }catch (IllegalAccessException | InstantiationException |
                    NoSuchMethodException  | InvocationTargetException e){
                e.printStackTrace();
                return null;
            }
        }
    }

    //Scene Management
    private Scene[] sceneArray;
    private int currentSceneIndex = -99;

    public GameStateManager(){
        sceneArray = new Scene[Scenes.values().length];

        //setup testScene as initial scene.
        sceneArray[Scenes.TEST.ordinal()] = Scenes.TEST.createNewScene();
        currentSceneIndex = Scenes.TEST.ordinal();
    }

    /** Tells the GameStateManager to distribute all init(), update() and render() calls to the Scene. This method will
     * also init the scene.
     * @param scene An element in the enum GameStateManager.Scenes.
     */
    public void switchScene(Scenes scene){
        sceneArray[currentSceneIndex].destroy();

        sceneArray[scene.ordinal()] = scene.createNewScene();
        sceneArray[scene.ordinal()].init();
        currentSceneIndex = scene.ordinal();
    }

    /** Calls init() on whichever scene is the current scene.
     * @throws SceneNotLoadedException
     */
    public void init() throws SceneNotLoadedException {
        if(sceneArray[currentSceneIndex] != null ) sceneArray[currentSceneIndex].init();

        else {
            throw new SceneNotLoadedException("No Scene loaded at current Scene index in GameStateManger)");
        }
    }

    /** Calls update() on whichever scene is the current scene.
     * @throws SceneNotLoadedException
     */
    public void update() throws SceneNotLoadedException {
        if(sceneArray[currentSceneIndex] != null ) sceneArray[currentSceneIndex].update(this);

        else {
            throw new SceneNotLoadedException("No Scene loaded at current Scene index in GameStateManger");
        }
    }

    /** Calls render() on whichever scene is the current scene.
     * @throws SceneNotLoadedException
     */
    public void render() throws SceneNotLoadedException {
        if(sceneArray[currentSceneIndex] != null ) sceneArray[currentSceneIndex].render();

        else {
            throw new SceneNotLoadedException("No Scene loaded at current Scene index in GameStateManger");
        }
    }
}
