package Scenes;

import java.lang.reflect.InvocationTargetException;

/**An enum that represents all possible Scenes. SceneList implements a factory-pattern that generates
 * new scene-instances based on the Scene-classes*/
public enum SceneList {
    //NEW SCENES HERE BELOW HERE
    TEST(TestScene.class),
    EMPTY(EmptyScene.class),
    DEFAULT();

    private Class<? extends Scene> sceneClass;
    SceneList(Class<? extends Scene> sc){
        sceneClass = sc;
    }
    SceneList(){}

    /**@return A new Scene-instance based on the class-object attached to the element.*/
    Scene createNewScene(){
        try {
            return sceneClass.getConstructor().newInstance();
        }catch (IllegalAccessException | InstantiationException |
                NoSuchMethodException  | InvocationTargetException e){
            e.printStackTrace();
            return null;
        }
    }
}
