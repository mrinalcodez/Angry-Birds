package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.io.Serializable;

public class Glass extends Objects implements Damageable, Serializable {
    public SerializableRenderComponent damaged1;
    private GlassTextureManager glassTextureManager;
    public int health = 15;

    public Glass(String object, SerializableWorld world, Vector2[] dimensions, float worldx, float worldy, boolean vertical) {
        super(new GlassTextureManager(object), world, GlassEnum.NORMAL);
        glassTextureManager = (GlassTextureManager) super.getTextureManager();
        body = world.new SerializableBody(worldx, worldy, dimensions, BodyDef.BodyType.DynamicBody, 0.01f, 0.7f, 0.2f, null);
        this.sprite.setSize(body.getSize().x, body.getSize().y);
        this.sprite.setOrigin(this.sprite.width/2, this.sprite.height/2);
        this.sprite.setPosition(body.body.getPosition().x - this.sprite.width/2, body.body.getPosition().y - this.sprite.height/2);
        body.setAngularDamping(0.2f);
        if(vertical){
            body.body.setTransform(body.body.getPosition(), MathUtils.degreesToRadians*90);
            this.sprite.setRotation(MathUtils.radiansToDegrees*body.body.getAngle());
        }
        this.damaged1 = super.createSprite(glassTextureManager.getTextureRegion(GlassEnum.DAMAGED1));
        Structure.rendering_objects.add(this);
    }

    @Override
    public void draw(SerializableRenderer renderer) {
        setPosition(body.body.getPosition().x - this.sprite.width/2, body.body.getPosition().y - this.sprite.height/2);
        this.sprite.draw(renderer, sprite.x, sprite.y, sprite.width, sprite.height, sprite.rotation);
        updateSpriteonHealth();
        isPaused();
    }

    @Override
    public void setPosition(float x, float y) {
        this.sprite.setPosition(x, y);
        this.sprite.setRotation(MathUtils.radiansToDegrees*body.body.getAngle());
    }

    @Override
    public void saveData() {
        body.saveData();
    }

    @Override
    protected void loadData() {
        sprite.loadData();
        damaged1.loadData();
    }

    @Override
    public void updateHealth(int health) {
        this.health -= health;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void dispose() {
        Structure.destroy_objects.add(this);
    }

    private void updateSpriteonHealth(){
        if(health < 0){
            dispose();
        }
        if(health < 7){
            this.state = GlassEnum.DAMAGED1;
            this.sprite = damaged1;
        }
    }

    public enum GlassEnum{
        NORMAL,
        DAMAGED1;
    }
}
