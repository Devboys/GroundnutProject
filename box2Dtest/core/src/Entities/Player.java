package Entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

public class Player implements Entity {
    int x;
    int y;

    Body playerCollider;
    Fixture playerFixture;

    public Player() {}

    @Override
    public void update(){}

    @Override
    public void render() {}

    @Override
    public void setupPhysics() {

    }
}
