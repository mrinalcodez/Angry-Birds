package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Ground extends Objects{
    private GroundTextureManager groundTextureManager;
    public float worldwidth;
    public float worldheight;
    public float worldx;
    public float worldy;
    public Ground(String object, SerializableWorld world, Vector2[] dimensions, float worldx, float worldy) {
        super(new GroundTextureManager(object), world, GroundEnum.NORMAL);
        groundTextureManager = (GroundTextureManager) super.getTextureManager();
        body = world.new SerializableBody(worldx, worldy, dimensions, BodyDef.BodyType.StaticBody, 0.2f, 0.4f, 0.5f, "ground");
        this.sprite.setSize(body.getSize().x, body.getSize().y + 40*body.getSize().y/sprite.regionheight);
        this.sprite.setOrigin(this.sprite.width/2, this.sprite.height/2);
        this.sprite.setPosition(body.body.getPosition().x - body.getSize().x/2, body.body.getPosition().y - body.getSize().y/2);
        Structure.rendering_objects.add(this);
    }

    @Override
    public void draw(SerializableRenderer renderer) {
        setPosition(body.body.getPosition().x - body.getSize().x/2, body.body.getPosition().y - body.getSize().y/2);
        this.sprite.draw(renderer, sprite.x, sprite.y, sprite.width, sprite.height, sprite.rotation);
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
    }

    public float getWidth(){
        return this.worldwidth;
    }

    public float getHeight(){
        return this.worldheight;
    }

    public enum GroundEnum {
        NORMAL;
    }
}
