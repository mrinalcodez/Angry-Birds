package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.io.Serializable;

public class Rock extends Objects implements Damageable, Serializable {
    public SerializableRenderComponent damaged1;
    private RockTextureManager rockTextureManager;
    public int health = 50;

    public Rock(String object, SerializableWorld world, Vector2[] dimensions, float worldx, float worldy, boolean vertical){
        super(new RockTextureManager(object), world, RockEnum.NORMAL);
        rockTextureManager = (RockTextureManager) super.getTextureManager();
        body = world.new SerializableBody(worldx, worldy, dimensions, BodyDef.BodyType.DynamicBody, 0.01f, 0.7f, 0.5f, null);
        this.sprite.setSize(body.getSize().x, body.getSize().y);
        this.sprite.setOrigin(this.sprite.width/2, this.sprite.height/2);
        this.sprite.setPosition(body.body.getPosition().x - this.sprite.width/2, body.body.getPosition().y - this.sprite.height/2);
        body.setAngularDamping(0.2f);
        if(vertical){
            body.body.setTransform(body.body.getPosition(), MathUtils.degreesToRadians*90);
            this.sprite.setRotation(MathUtils.radiansToDegrees*body.body.getAngle());
        }
        this.damaged1 = super.createSprite(rockTextureManager.getTextureRegion(RockEnum.DAMAGED1));
        Structure.rendering_objects.add(this);
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

    @Override
    public void draw(SerializableRenderer renderer) {
        setPosition(body.body.getPosition().x - this.sprite.width/2, body.body.getPosition().y - this.sprite.height/2);
        this.sprite.draw(renderer, sprite.x, sprite.y, sprite.width, sprite.height, sprite.rotation);
        updateSpriteonHealth();
        isPaused();
    }

    private void updateSpriteonHealth() {
        if(health < 0){
            dispose();
        }
        if(health < 30){
            this.state = RockEnum.DAMAGED1;
            this.sprite = damaged1;
        }
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
        body.loadData();
        sprite.loadData();
        damaged1.loadData();
    }

    public enum RockEnum{
        NORMAL,
        DAMAGED1;
    }
}
