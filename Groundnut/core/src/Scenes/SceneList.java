package Scenes;

import java.lang.reflect.InvocationTargetException;

//enum of possible states
public enum SceneList {
    //NEW SCENES HERE BELOW HERE
    TEST(TestScene.class);

    private final Class<? extends Scene> sceneClass;
    SceneList(Class<? extends Scene> sc){
        sceneClass = sc;
    }
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
