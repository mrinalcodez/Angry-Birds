package io.github.some_example_name;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.io.Serializable;

public class Wood extends Objects implements Damageable, Serializable {
    public SerializableRenderComponent damaged1;
    private SerializableRenderComponent damaged2;
    private WoodTextureManager woodtextureManager;
    public int health = 20;
    public Wood(String object, SerializableWorld world, Vector2[] dimensions, float worldx, float worldy, boolean vertical){
        super(new WoodTextureManager(object), world, WoodEnum.NORMAL);
        woodtextureManager = (WoodTextureManager) super.getTextureManager();
        body = world.new SerializableBody(worldx, worldy, dimensions, BodyDef.BodyType.DynamicBody, 0.01f, 0.7f, 0.3f, null);
        this.sprite.setSize(body.getSize().x, body.getSize().y);
        this.sprite.setOrigin(this.sprite.width/2, this.sprite.height/2);
        this.sprite.setPosition(body.body.getPosition().x - this.sprite.width/2, body.body.getPosition().y - this.sprite.height/2);
        if(vertical){
            body.body.setTransform(body.body.getPosition(), MathUtils.degreesToRadians*90);
            this.sprite.setRotation(MathUtils.radiansToDegrees*body.body.getAngle());
        }
        this.damaged1 = super.createSprite(woodtextureManager.getTextureRegion(WoodEnum.DAMAGED1));
        this.damaged2 = super.createSprite(woodtextureManager.getTextureRegion(WoodEnum.DAMAGED2));
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

    private void updateSpriteonHealth(){
        if(health < 0){
            dispose();
        }
        if(health < 10){
            this.state = WoodEnum.DAMAGED1;
            this.sprite = damaged1;
        }
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

    public enum WoodEnum{
        NORMAL,
        DAMAGED1,
        DAMAGED2;
    }
}
