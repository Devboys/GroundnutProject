package Scenes;

public class GameStateManager {

    //enum of possible states
    public enum Scenes {
        TEST(new TestScene()),
        MENU(new MenuScene());

        private final Scene sceneObject;
        Scenes(Scene sc){
            sceneObject = sc;
        }
        private Scene newScene(){
            try {
                return sceneObject.getClass().newInstance();
            }catch (IllegalAccessException | InstantiationException e){
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
        switchScene(Scenes.TEST);
    }

//    /** Tells the GameStateManager to create and initialize a new Scene of the given type.
//     * Only one of each type of Scene can be loaded at a time.
//     * Calling loadScene on an already loaded Scene will re-initialize it.
//     * @param scene An element in the enum GameStateManager.Scenes.
//     */
//    public void loadScene(Scenes scene){
//        System.out.println("LOADING");
//
//        sceneArray[scene.ordinal()] = scene.newScene();
//        if(currentSceneIndex != -99){
//            sceneArray[scene.ordinal()].init();
//        }
////        switch (scene){
////            case TEST:
////                sceneIndex = Scenes.TEST.ordinal();
////                sceneArray[sceneIndex] = TEST.;
////                break;
////            default:
////                System.out.println("UNKNOWN SCENE TYPE");
////        }
////
////        if(currentSceneIndex != -99){
////            sceneArray[sceneIndex].init();
////        }
//    }

//    /** Tells the GameStateManager to drop all reference to the selected scene type. This will free up memory but also
//     * delete scene and drop all state-data related to that scene. The scene will have to be reloaded if needed again.
//     * @param scene An element in the enum GameStateManager.Scenes.
//     */
//    public void unloadScene(Scenes scene){
//        sceneArray[scene.ordinal()] = null;
////        int sceneIndex;
////        switch (scene){
////            case TEST:
////                sceneIndex = Scenes.TEST.ordinal();
////                sceneArray[sceneIndex] = null;
////                break;
////
////            default:
////                System.out.println("UNKNOWN SCENE TYPE");
////        }
//    }

//    /** Tells the GameStateManager to distribute all init(), update() and render() calls to the Scene. This will not
//     * method will not initialize anything, so make sure a scene is loaded before you try to change it.
//     * @param scene An element in the enum GameStateManager.Scenes.
//     */
//    public void changeScene(Scenes scene){
//        currentSceneIndex = scene.ordinal();
////        int sceneIndex;
////
////        switch(scene){
////            case TEST:
////                sceneIndex = Scenes.TEST.ordinal();
////                currentSceneIndex = sceneIndex;
////                break;
////
////            default:
////                System.out.println("UNKNOWN SCENE TYPE");
////        }
//    }

    /** Tells the GameStateManager to distribute all init(), update() and render() calls to the Scene. This method will
     * also init the scene.
     * @param scene An element in the enum GameStateManager.Scenes.
     */
    public void switchScene(Scenes scene){
        sceneArray[scene.ordinal()] = scene.newScene();

        if(currentSceneIndex != -99){
            sceneArray[currentSceneIndex].destroy();
            sceneArray[scene.ordinal()].init();
        }

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
