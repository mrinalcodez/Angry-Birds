package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

import java.io.Serializable;

public interface Damageable extends Serializable {
    public void updateHealth(int health);
    public int getHealth();
    public void dispose();
}
