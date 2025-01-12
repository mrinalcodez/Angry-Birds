package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;

import java.io.Serializable;

public abstract class Objects implements SetPosition, Serializable {
    protected SerializableWorld.SerializableBody body;
    protected SerializableRenderComponent sprite;
    private TextureManager textureManager;
    protected Enum<?> state;
    protected SerializableWorld world;

    public Objects(TextureManager textureManager, SerializableWorld world, Enum<?> e){
        this.textureManager = textureManager;
        this.world = world;
        this.sprite = this.textureManager.getTextureRegion(e);
        this.state = e;
    }

    protected void isPaused(){
        if(Level.isPlaying){
            this.sprite.setColor(1, 1, 1, 1);
        } else {
            this.sprite.setColor(0.5f, 0.5f, 0.5f, 1);
        }
    }

    protected final SerializableRenderComponent createSprite(SerializableRenderComponent renderComponent){
        renderComponent.setSize(sprite.width, sprite.height);
        renderComponent.setPosition(sprite.x, sprite.y);
        renderComponent.setOrigin(sprite.width/2, sprite.height/2);
        renderComponent.setRotation(MathUtils.radiansToDegrees*body.body.getAngle());
        return renderComponent;
    }

    public abstract void draw(SerializableRenderer renderer);
    public abstract void setPosition(float x, float y);

    public TextureManager getTextureManager(){
        return this.textureManager;
    }
    public abstract void saveData();
    protected abstract void loadData();
}

