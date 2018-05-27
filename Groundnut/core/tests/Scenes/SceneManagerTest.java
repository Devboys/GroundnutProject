package Scenes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SceneManagerTest {

    SceneManager sM;

    @Before
    public void setUp() throws Exception {
       sM = new SceneManager();
    }

    @Test
    public void switchSceneTest() {
        sM.switchScene(SceneList.EMPTY);
        Assert.assertEquals(sM.getCurrentScene().getClass() , EmptyScene.class);
    }
}