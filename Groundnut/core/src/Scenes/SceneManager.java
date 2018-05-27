package Scenes;

public class SceneManager {

    //Scene Management
    private Scene[] sceneArray;
    private int currentSceneIndex;

    /**Manager that controls the underlying finite-state-machine of the game-loop.
     * Uses SceneList-elements to generate new Scenes and redirects Update() and Render() calls to its current scene.*/
    public SceneManager(){
        sceneArray = new Scene[SceneList.values().length];

        currentSceneIndex = SceneList.DEFAULT.ordinal();
    }

    /** Tells the SceneManager to distribute all update() and render() calls to the Scene. This method will
     * also init the scene.
     * @param scene An element in the enum SceneManager.SceneList.*/
    public void switchScene(SceneList scene){

        if(scene != SceneList.DEFAULT) {
            sceneArray[scene.ordinal()] = scene.createNewScene();
        }

        //TODO: MAKE PRETTY
        if(currentSceneIndex !=  SceneList.DEFAULT.ordinal()) {
            sceneArray[currentSceneIndex].destroy();
            sceneArray[scene.ordinal()].init();
        }

        currentSceneIndex = scene.ordinal();
    }

    /**@return The currently loaded Scene-object.*/
    public Scene getCurrentScene(){
        return sceneArray[currentSceneIndex];
    }

    /**Calls init() on whichever scene is the current scene.*/
    public void init(){
        if(sceneArray[currentSceneIndex] != null ) sceneArray[currentSceneIndex].init();
    }

    /**Calls update() on whichever scene is the current scene.*/
    public void update(){
        if(sceneArray[currentSceneIndex] != null ) sceneArray[currentSceneIndex].update(this);
    }

    /**Calls render() on whichever scene is the current scene.*/
    public void render() {
        if(sceneArray[currentSceneIndex] != null ) sceneArray[currentSceneIndex].render();
    }

}
