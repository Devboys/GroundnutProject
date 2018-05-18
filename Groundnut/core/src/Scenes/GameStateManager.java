package Scenes;

public class GameStateManager {

    //Scene Management
    private Scene[] sceneArray;
    private int currentSceneIndex;

    public GameStateManager(){
        sceneArray = new Scene[SceneList.values().length];

        //setup testScene as initial scene.
        sceneArray[SceneList.TEST.ordinal()] = SceneList.TEST.createNewScene();
        currentSceneIndex = SceneList.TEST.ordinal();
    }

    /** Tells the GameStateManager to distribute all init(), update() and render() calls to the Scene. This method will
     * also init the scene.
     * @param scene An element in the enum GameStateManager.SceneList.*/
    public void switchScene(SceneList scene){

        sceneArray[scene.ordinal()] = scene.createNewScene();

        if(currentSceneIndex != -99) {
            sceneArray[currentSceneIndex].destroy();
            sceneArray[scene.ordinal()].init();
        }

        currentSceneIndex = scene.ordinal();
    }

    /** Calls init() on whichever scene is the current scene.*/
    public void init(){
        if(sceneArray[currentSceneIndex] != null ) sceneArray[currentSceneIndex].init();
    }

    /** Calls update() on whichever scene is the current scene.*/
    public void update(){
        if(sceneArray[currentSceneIndex] != null ) sceneArray[currentSceneIndex].update(this);
    }

    /** Calls render() on whichever scene is the current scene.*/
    public void render() {
        if(sceneArray[currentSceneIndex] != null ) sceneArray[currentSceneIndex].render();
    }

}
