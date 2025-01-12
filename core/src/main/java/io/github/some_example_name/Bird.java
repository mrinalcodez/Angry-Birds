package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Null;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.github.some_example_name.AddProcessor.inputMultiplexer;

public class Bird extends Objects implements blink, Damageable, Serializable {
    public boolean isSetOnCatapult = false;
    public float blink_time_elapsed = 0;
    public float wait_blink = 0;
    public SerializableRenderComponent blink_sprite;
    private SerializableRenderComponent normal_sprite;
    public SerializableRenderComponent hurt_sprite;
    public BirdTextureManager birdTextureManager;
    public boolean islaunched = false;
    public float time_elapsed = 0;
    public int random_time = (int) (Math.random()*(15-5+1) + 5);
    public boolean isturn = false;
    public Vector2 currentPosition;
    public Vector2 previousPosition;
    public int health = 100;
    private SerializableRenderer camera;
    public Map<SerializableRenderComponent, Vector2> textures = new HashMap<>();
    public boolean hasAttacked = false;
    public boolean hasfallen = false;
    public float time_to_die = 0;
    public PowerUp<Bird> powerUp;
    public boolean isActivated = false;
    private SerializableInputMultiplexer multiplexer;
    public CustomInputAdapter inputAdapter;
    public String object;

    public Bird(String object, Vector2[] dimensions, SerializableWorld world, float worldx, float worldy, SerializableRenderer camera, SerializableInputMultiplexer multiplexer){
        super(new BirdTextureManager(object), world, BirdEnum.NORMAL);
        this.object = object;
        birdTextureManager = (BirdTextureManager) super.getTextureManager();

        body = world.new SerializableBody(worldx, worldy, dimensions, BodyDef.BodyType.DynamicBody, (float) (3 / (((double) 4 / 3) * Math.PI * 125)), 0.4f, 0.5f, "bird");
        body.setAngularDamping(0.2f);

        sprite.setSize(body.getSize().x, body.getSize().y);
        if(object.equals("YELLOW")){
            sprite.setSize(sprite.width + 2, sprite.height + 2);
        }
        sprite.setPosition(body.body.getPosition().x - sprite.width/2, body.body.getPosition().y - sprite.height/2);
        sprite.setOrigin(sprite.width/2, sprite.height/2);
        this.normal_sprite = super.createSprite(birdTextureManager.getTextureRegion(BirdEnum.NORMAL));
        this.blink_sprite = super.createSprite(birdTextureManager.getTextureRegion(BirdEnum.BLINKING));
        this.hurt_sprite = super.createSprite(birdTextureManager.getTextureRegion(BirdEnum.HURT));
        this.camera = camera;
        this.multiplexer = multiplexer;
        PowerUpType.setBird(this);
        powerUp = PowerUpType.valueOf(birdTextureManager.getType().getPowerUp()).create();
        inputAdapter = new CustomInputAdapter("Bird", null){
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 worldcoordinates = camera.camera.unproject(new Vector3(screenX, screenY, 0));

                if(worldcoordinates.x >= camera.positionx - camera.viewportWidth/4 && worldcoordinates.x <= camera.positiony + camera.viewportWidth/4 && powerUp != null && powerUp.getE().islaunched){
                    powerUp.activate(powerUp.getE());
                    isActivated = true;
                    multiplexer.removeProcessor(this);
                    return true;
                }
                return false;
            }
        };
        multiplexer.addProcessor(inputAdapter);
        Structure.rendering_objects.add(this);
    }

    @Override
    public void draw(SerializableRenderer renderer) {
        if(isSetOnCatapult && !islaunched){
            previousPosition = body.body.getPosition().cpy();
        }
        blink(Gdx.graphics.getDeltaTime());
        setPosition(this.body.body.getPosition().x - sprite.width/2, this.body.body.getPosition().y - sprite.height/2);
        this.sprite.draw(renderer, sprite.x, sprite.y, sprite.width, sprite.height, sprite.rotation);
        trajectory();
        for(SerializableRenderComponent t : textures.keySet()){
            t.draw(renderer, textures.get(t).x, textures.get(t).y, 4, 4, 0);
        }
        if(!isSetOnCatapult){
            update(Gdx.graphics.getDeltaTime());
        }
        updateSpriteonHealth();
        isPaused();
    }

    @Override
    public void setPosition(float x, float y) {
        this.sprite.setPosition(x, y);
        this.sprite.setRotation(MathUtils.radiansToDegrees*body.body.getAngle());
        if(birdTextureManager.getType() == TextureState.BirdTextureEnum.YELLOW){
            sprite.setPosition(sprite.x, sprite.y + 4);
//            sprite.setRotation(body.body.getAngle()*MathUtils.radiansToDegrees - 90);
        }
    }

    @Override
    public void saveData() {
        body.saveData();
    }

    @Override
    protected void loadData() {
        sprite.loadData();
        normal_sprite.loadData();
        blink_sprite.loadData();
        hurt_sprite.loadData();
        previousPosition = body.body.getPosition().cpy();
        for(SerializableRenderComponent textures : textures.keySet()){
            textures.loadData();
        }
        if(powerUp != null){
            powerUp.loadData();
        }
    }

    @Override
    public void blink(float deltatime) {
        if(Level.isPlaying && !Level.isGameWon && !Level.isGameLost && state != BirdEnum.HURT) {
            if (blink_time_elapsed > 5) {
                wait_blink += deltatime;
                this.state = BirdEnum.BLINKING;
                this.sprite = this.blink_sprite;
                if (wait_blink > 0.2) {
                    blink_time_elapsed = 0;
                    wait_blink = 0;
                }
                return;
            }
            this.state = BirdEnum.NORMAL;
            this.sprite = this.normal_sprite;
            blink_time_elapsed += deltatime;
        }
    }

    private void update(float deltatime) {
        if (time_elapsed > random_time && !isturn && Level.isPlaying) {
            float gravity = body.body.getWorld().getGravity().len();
            float jump_height = 4f;
            float bird_mass = body.body.getMass();
            float jump_velocity = (float) Math.sqrt(2*gravity*jump_height);
            this.body.body.applyLinearImpulse(new Vector2(0, bird_mass*jump_velocity), body.body.getWorldCenter(), true);
            time_elapsed = 0;
        }
        time_elapsed += deltatime;
    }

    private void trajectory(){
        if(islaunched){
            currentPosition = body.body.getPosition();
            if(currentPosition.dst(previousPosition) > 6){
                SerializableRenderComponent dot = new SerializableRenderComponent("trail.png", true, 4, 4);
                textures.put(dot, previousPosition.cpy());
                previousPosition.set(currentPosition.cpy());
            }
        }
    }

    private void updateSpriteonHealth(){
        if(state == BirdEnum.HURT){
            this.sprite = hurt_sprite;
        }
        if(time_to_die > 10 && state == BirdEnum.HURT && body.body.getAngularVelocity() <= 1 && body.body.getLinearVelocity().len() <= 1){
            dispose();
            updateHealth(getHealth() + 1);
            time_to_die = 0;
        }
        time_to_die += Gdx.graphics.getDeltaTime();
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

    public enum BirdEnum{
        NORMAL,
        BLINKING,
        HURT;
    }
}
