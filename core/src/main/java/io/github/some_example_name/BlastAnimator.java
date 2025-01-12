package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;

public class BlastAnimator implements Serializable {
    public transient Animation<SerializableRenderComponent> animation;
    public Vector2 position;
    private float timer = 0f;

    public BlastAnimator(Animation<SerializableRenderComponent> animation, float x, float y){
        this.animation = animation;
        this.position = new Vector2(x, y);
    }

    public boolean isFinished(){
        return animation.isAnimationFinished(timer);
    }

    public void updateTimer(float timer){
        this.timer += timer;
    }

    public Sprite getCurrentFrame(){
        return animation.getKeyFrame(timer).sprite;
    }

    public void loadData(){
        SerializableRenderComponent sprite1 = new SerializableRenderComponent("angry-icon.png", 447, 157, 115, 108);
        SerializableRenderComponent sprite2 = new SerializableRenderComponent("angry-icon.png", 41, 716, 124, 115);
        SerializableRenderComponent sprite3 = new SerializableRenderComponent("angry-icon.png", 40, 469, 125, 119);
        sprite1.setSize(50, 50);
        sprite2.setSize(50, 50);
        sprite3.setSize(50, 50);
        animation = new Animation<>(0.2f, sprite1, sprite2, sprite3);
    }
}
