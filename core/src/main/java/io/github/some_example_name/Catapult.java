package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.*;

import java.util.ArrayList;
import java.util.Iterator;

public class Catapult extends Objects implements AddProcessor{
    private Bird bird;
    public CatapultRightHand rightHand;
    private SerializableWorld world;
    public boolean isDragged = false;
    private transient MouseJoint mouseJoint;
    private transient RopeJoint ropeJoint;
    private SerializableRenderer camera;
    public float bird_density;
    public float bird_inertia;
    private SerializableWorld.SerializableBody launch_body;
    public boolean isbirdSet;
    public int bird_index;
    private ArrayList<Bird> birds;
    private SerializableRenderComponent slingropeleft;
    private SerializableRenderComponent slingroperight;
    public float worldwidth, worldheight;
    private SerializableInputMultiplexer multiplexer;
    public CustomInputAdapter inputAdapter;

    public Catapult(String object, SerializableWorld world, Vector2[] dimensions1, Vector2[] dimensions2, float worldx, float worldy, Bird bird, SerializableRenderer camera, ArrayList<Bird> birds, SerializableInputMultiplexer multiplexer) {
        super(new CatapultTextureManager(object), world, CatapultEnum.NORMAL);
        this.world = world;
        this.camera = camera;
        body = world.new SerializableBody(worldx, worldy, dimensions1, BodyDef.BodyType.StaticBody, 0.2f, 0.2f, 0.2f, "left hand");
        this.sprite.setSize(body.getSize().x, body.getSize().y);
        this.sprite.setOrigin(this.sprite.width/2, this.sprite.height/2);
        this.sprite.setPosition(body.body.getPosition().x - this.sprite.width/2, body.body.getPosition().y - this.sprite.height/2);
        worldwidth = sprite.width;
        worldheight = sprite.height;
        Structure.rendering_objects.add(this);
        this.rightHand = new CatapultRightHand("RIGHTHAND", world, dimensions2, worldx - 8f, worldy + 10f);
        isbirdSet = false;
        this.birds = birds;
        this.multiplexer = multiplexer;
        if(bird != null) {
            this.bird = bird;
            bird_density = this.bird.body.body.getFixtureList().get(0).getDensity();
            bird_inertia = this.bird.body.body.getInertia();
            this.bird.isSetOnCatapult = true;
            this.bird.body.body.setType(BodyDef.BodyType.StaticBody);
            this.bird.body.body.getFixtureList().get(0).setDensity(0);
            Structure.rendering_objects.remove(this.bird);
            setBirdPosition((this.body.body.getPosition().x + this.rightHand.body.body.getPosition().x) / 2, this.body.body.getPosition().y + this.sprite.height / 3);
            isbirdSet = true;
        }
    }

    @Override
    public void draw(SerializableRenderer renderer) {
        setPosition(this.body.body.getPosition().x, this.body.body.getPosition().y);
        this.sprite.draw(renderer, sprite.sprite.getX(), sprite.sprite.getY(), sprite.width, sprite.height, sprite.rotation);
        if(slingropeleft != null){
            slingropeleft.setSize(bird.body.body.getPosition().dst(new Vector2(body.body.getPosition().x + worldwidth/4, body.body.getPosition().y + sprite.height/3)), 3);
            slingropeleft.setOrigin(slingropeleft.width, slingropeleft.height/2);
            slingropeleft.setOriginBasedPosition(body.body.getPosition().x + worldwidth/4, body.body.getPosition().y + sprite.height/3);
            float angle = (float) (Math.atan2((bird.body.body.getPosition().y - (body.body.getPosition().y + sprite.height/3)), (bird.body.body.getPosition().x - (body.body.getPosition().x + worldwidth/4)))*MathUtils.radiansToDegrees);
            if(angle < 0){
                angle += 180;
            } else if(angle < 180){
                angle += 180;
            }
            slingropeleft.setRotation(angle);
            slingropeleft.draw(renderer, slingropeleft.x, slingropeleft.y, slingropeleft.width, slingropeleft.height, slingropeleft.rotation);
        }
        if(bird != null){
            setBirdPosition(bird.body.body.getPosition().x, bird.body.body.getPosition().y);
            this.bird.draw(renderer);
        }
        this.rightHand.draw(renderer);
        if(slingroperight != null){
            slingroperight.setSize(bird.body.body.getPosition().dst(new Vector2(rightHand.body.body.getPosition().x - worldwidth/4, body.body.getPosition().y + sprite.height/3)), 3);
            slingroperight.setOrigin(slingroperight.width, slingroperight.height/2);
            slingroperight.setOriginBasedPosition(rightHand.body.body.getPosition().x - worldwidth/4, body.body.getPosition().y + sprite.height/3);
            float angle = (float) (Math.atan2((bird.body.body.getPosition().y - (body.body.getPosition().y + sprite.height/3)), (bird.body.body.getPosition().x - (rightHand.body.body.getPosition().x - worldwidth/4)))*MathUtils.radiansToDegrees);
            if(angle < 0){
                angle += 180;
            } else if(angle < 180){
                angle += 180;
            }
            slingroperight.setRotation(angle);
            slingroperight.draw(renderer, slingroperight.x, slingroperight.y, slingroperight.width, slingroperight.height, slingroperight.rotation);
        }
    }

    @Override
    public void setPosition(float x, float y) {
        this.sprite.setPosition(x - sprite.width/2, y - sprite.height/2);
        this.rightHand.setPosition(x - 8, y + 10);
    }

    @Override
    public void saveData() {
        body.saveData();
        if(bird != null){
            launch_body.saveData();
            bird.saveData();
        } else if(launch_body != null){
            launch_body.saveData();
        }
    }

    @Override
    protected void loadData() {
        sprite.loadData();
        if(bird != null){
            bird.loadData();
            catapult_action();
        }
    }

    private void setBirdPosition(float x, float y){
        this.bird.body.body.setTransform(x, y, this.bird.body.body.getAngle());
    }

    public void setBird(Bird bird){
        this.bird = bird;
        bird_index = Structure.rendering_objects.indexOf(this.bird);
        Structure.rendering_objects.remove(this.bird);
        bird_density = this.bird.body.body.getFixtureList().get(0).getDensity();
        bird_inertia = this.bird.body.body.getInertia();
        this.bird.isSetOnCatapult = true;
        this.bird.body.body.setType(BodyDef.BodyType.StaticBody);
        this.bird.body.body.getFixtureList().get(0).setDensity(0);
        setBirdPosition((this.body.body.getPosition().x + this.rightHand.body.body.getPosition().x) / 2, this.body.body.getPosition().y + this.sprite.height / 3);
        isbirdSet = true;
        catapult_action();
    }


    public Bird getBird(){
        return this.bird;
    }

    public void catapult_action(){
        launch_body = world.new SerializableBody(bird.body.body.getPosition().x, bird.body.body.getPosition().y, new Vector2[]{new Vector2(6, 6)}, BodyDef.BodyType.StaticBody, 0, 0, 0, "launch body");
        if(bird != null) {
            RopeJointDef ropeJointDef = new RopeJointDef();
            ropeJointDef.bodyA = bird.body.body;
            ropeJointDef.bodyB = launch_body.body;
            ropeJointDef.localAnchorA.set(0, 0);
            ropeJointDef.localAnchorB.set(0, 0);
            ropeJointDef.maxLength = 30f;
            ropeJoint = (RopeJoint) world.createJoint(ropeJointDef);

            addProcessor(new CustomInputAdapter("Catapult", null) {
                @Override
                public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                    Vector3 worldcoordinates = camera.camera.unproject(new Vector3(screenX, screenY, 0));
                    isDragged = false;
                    float bird_size = bird.body.getSize().len();
                    if(bird.birdTextureManager.getType() == TextureState.BirdTextureEnum.YELLOW){
                        bird_size = 5;
                    }
                    if (bird.body.body.getWorldCenter().dst(new Vector2(worldcoordinates.x, worldcoordinates.y)) <= bird_size && !bird.islaunched) {
                        MouseJointDef mouseJointDef = new MouseJointDef();
                        mouseJointDef.bodyA = launch_body.body;
                        mouseJointDef.bodyB = bird.body.body;
                        mouseJointDef.collideConnected = false;
                        mouseJointDef.target.set(new Vector2(worldcoordinates.x, worldcoordinates.y));
                        mouseJointDef.maxForce = 10000f;
                        mouseJoint = (MouseJoint) world.createJoint(mouseJointDef);

                        slingroperight = new SerializableRenderComponent("sling rope.png", 378, 262, 1235, 228);
                        slingropeleft = new SerializableRenderComponent("sling rope.png", 378, 262, 1235, 228);
                        slingroperight.setOrigin(slingroperight.width, slingroperight.height/2);
                        slingropeleft.setOrigin(slingropeleft.width, slingropeleft.height/2);
                    }
                    return true;
                }

                @Override
                public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                    isDragged = false;
                    if (bird.body.body.getWorldCenter().dst(launch_body.body.getWorldCenter()) < 2) {
                        if (mouseJoint != null) {
                            world.destroyJoint(mouseJoint);
                        }
                        if (world.world.getJointCount() == 1) {
                            setBirdPosition(launch_body.body.getPosition().x, launch_body.body.getPosition().y);
                            bird.body.body.setType(BodyDef.BodyType.StaticBody);
                        }
                        mouseJoint = null;
                        slingroperight = null;
                        slingropeleft = null;
                    } else if (mouseJoint != null) {
                        bird.body.body.setType(BodyDef.BodyType.DynamicBody);
                        bird.body.body.getFixtureList().get(0).setDensity(bird_density);
                        MassData massData = new MassData();
                        massData.I = bird_inertia;
                        bird.body.body.setMassData(massData);
                        Vector2 mouseReaction = mouseJoint.getReactionForce(1 / Gdx.graphics.getDeltaTime()).scl(-1);

                        bird.body.body.applyForceToCenter(mouseReaction, true);

                        // Destroy joints after applying forces
                        world.destroyJoint(ropeJoint);
                        world.destroyJoint(mouseJoint);
                        mouseJoint = null;


                        slingropeleft.dispose();
                        slingroperight.dispose();
                        slingropeleft = null;
                        slingroperight = null;
                        Structure.rendering_objects.add(bird_index, bird);
                        bird.islaunched = true;
                        bird.isSetOnCatapult = false;
                        bird = null;
                        isbirdSet = false;
                        multiplexer.removeProcessor(this);
                    }

                    return true;
                }

                @Override
                public boolean touchDragged(int screenX, int screenY, int pointer) {
                    if (mouseJoint != null) {
                        isDragged = true;
                        bird.body.body.setType(BodyDef.BodyType.DynamicBody);
                        Vector3 worldcoordinates = camera.camera.unproject(new Vector3(screenX, screenY, 0));
                        mouseJoint.setTarget(new Vector2(worldcoordinates.x, worldcoordinates.y));
                        //path_draw();
                    }
                    return true;
                }
            });
        }
    }

    public CatapultRightHand getRightHand(){
        return this.rightHand;
    }

    @Override
    public void addProcessor(InputAdapter inputAdapter) {
        multiplexer.addProcessor(inputAdapter);
    }

    public enum CatapultEnum{
        NORMAL;
    }
}
