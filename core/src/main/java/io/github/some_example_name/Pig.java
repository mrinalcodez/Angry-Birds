package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.io.Serializable;

public class Pig extends Objects implements blink, Damageable, Serializable {
    private PigTextureManager pigTextureManager;
    private SerializableRenderComponent normal;
    public SerializableRenderComponent blink_sprite;
    public SerializableRenderComponent hurt_sprite;
    public float blink_time_elapsed = 0;
    public float wait_blink = 0;
    public int health;
    public int max_health;
    public Pig(String object, SerializableWorld world, Vector2[] dimensions, float worldx, float worldy, int health) {
        super(new PigTextureManager(object), world, PigEnum.NORMAL);
        pigTextureManager = (PigTextureManager) super.getTextureManager();
        body = world.new SerializableBody(worldx, worldy, dimensions, BodyDef.BodyType.DynamicBody, 0.005f, 0.4f, 0.5f, null);
        this.sprite.setSize(body.getSize().x, body.getSize().y);
        this.sprite.setPosition(body.body.getPosition().x - this.sprite.width/2, body.body.getPosition().y - this.sprite.height/2);
        this.sprite.setOrigin(this.sprite.width/2, this.sprite.height/2);
        body.setAngularDamping(0.2f);
        this.blink_sprite = super.createSprite(pigTextureManager.getTextureRegion(PigEnum.BLINKING));
        this.normal = super.createSprite(pigTextureManager.getTextureRegion(PigEnum.NORMAL));
        this.hurt_sprite = super.createSprite(pigTextureManager.getTextureRegion(PigEnum.HURT));
        this.health = health;
        this.max_health = health;
        Structure.rendering_objects.add(this);
    }

    @Override
    public void draw(SerializableRenderer renderer) {
        blink(Gdx.graphics.getDeltaTime());
        setPosition(this.body.body.getPosition().x - sprite.width/2, this.body.body.getPosition().y - sprite.height/2);
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
        normal.loadData();
        blink_sprite.loadData();
        hurt_sprite.loadData();
    }

    @Override
    public void blink(float deltatime) {
        if(Level.isPlaying && !Level.isGameWon && !Level.isGameLost && state != PigEnum.HURT) {
            if (blink_time_elapsed > 5) {
                wait_blink += deltatime;
                this.state = PigEnum.BLINKING;
                this.sprite = blink_sprite;
                if (wait_blink > 0.2) {
                    blink_time_elapsed = 0;
                    wait_blink = 0;
                }
                return;
            }
            this.state = PigEnum.NORMAL;
            this.sprite = normal;
            blink_time_elapsed += deltatime;
        }
    }

    private void updateSpriteonHealth(){
        if(health < 0){
            dispose();
        }
        else if(health < max_health*0.75){
            this.state = PigEnum.HURT;
            this.sprite = hurt_sprite;
        }
    }

    @Override
    public void updateHealth(int health) {
        this.health -= health;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public void dispose() {
        Structure.destroy_objects.add(this);
    }

    public enum PigEnum{
        NORMAL,
        BLINKING,
        HURT;
    }
}
