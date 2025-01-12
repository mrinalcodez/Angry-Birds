package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class CatapultRightHand extends Objects{
    private CatapultTextureManager textureManager;

    public CatapultRightHand(String object, SerializableWorld world, Vector2[] dimensions, float worldx, float worldy) {
        super(new CatapultTextureManager(object), world, Catapult.CatapultEnum.NORMAL);
        textureManager = (CatapultTextureManager) super.getTextureManager();
        body = world.new SerializableBody(worldx, worldy, dimensions, BodyDef.BodyType.StaticBody, 1.0f, 0.7f, 0.2f, "right hand");
        this.sprite.setSize(body.getSize().x, body.getSize().y);
        this.sprite.setOrigin(this.sprite.width/2, this.sprite.height/2);
        this.sprite.setPosition(body.body.getPosition().x - this.sprite.width/2, body.body.getPosition().y - this.sprite.height/2);
        Structure.rendering_objects.add(this);
    }

    @Override
    public void draw(SerializableRenderer renderer) {
        this.sprite.draw(renderer, sprite.x, sprite.y, sprite.width, sprite.height, sprite.rotation);
    }

    @Override
    public void setPosition(float x, float y) {
        this.body.body.setTransform(x, y, body.body.getAngle());
        this.sprite.setPosition(body.body.getPosition().x - sprite.width/2, body.body.getPosition().y - sprite.height/2);
    }

    @Override
    public void saveData() {
        body.saveData();
    }

    @Override
    protected void loadData() {
        sprite.loadData();
    }
}
