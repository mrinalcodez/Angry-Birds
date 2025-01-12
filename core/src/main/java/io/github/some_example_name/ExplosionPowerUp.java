package io.github.some_example_name;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;

import java.io.Serializable;

public class ExplosionPowerUp extends PowerUp<Bird> implements Serializable {
    private SerializableTimer timer = new SerializableTimer();

    public ExplosionPowerUp(Bird bird, String name) {
        super(bird, name);
    }

    @Override
    protected void applyEffect(Bird target) {
        if(!isactivated) {
            target.state = Bird.BirdEnum.HURT;
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    for (Objects o : Structure.rendering_objects) {
                        Vector2 bodyPosition = o.body.body.getPosition();
                        float distance = o.body.body.getPosition().dst(bodyPosition);

                        // Check if the body is within the explosion radius
                        if (distance <= 7) {
                            // Calculate the direction from the explosion center to the body
                            Vector2 explosionDirection = new Vector2(bodyPosition).sub(target.body.body.getPosition()).nor();

                            // Scale the force based on the distance (closer bodies get more force)
                            float distanceFactor = 1 - (distance / 7); // Linear falloff
                            float appliedForce = 20f * distanceFactor;

                            // Apply the impulse to the body
                            Vector2 impulse = explosionDirection.scl(appliedForce);
                            o.body.body.applyLinearImpulse(impulse, bodyPosition, true);
                        }
                    }
                    target.updateHealth(target.getHealth() + 1);
                    target.dispose();
                }
            }, 2);
        }
    }

    public void loadData(){
        timer.loadData();
    }
}
