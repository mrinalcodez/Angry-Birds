package io.github.some_example_name;

import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;

public class SpeedBoostPowerUp extends PowerUp<Bird> implements Serializable {
    public SpeedBoostPowerUp(Bird bird, String name) {
        super(bird, name);
    }

    @Override
    protected void applyEffect(Bird target) {
        if(!isactivated) {
            Vector2 velocity = target.body.body.getLinearVelocity().cpy().scl(2);
            target.body.body.setLinearVelocity(velocity);
        }
    }

    @Override
    protected void loadData() {

    }
}
