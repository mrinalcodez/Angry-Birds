package io.github.some_example_name;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends InputAdapter implements ApplicationListener {
    private Body body;
    private Body body1;
    private Body body2;
    private OrthographicCamera camera;
    private World world;
    private SpriteBatch batch;
    private Box2DDebugRenderer debugRenderer;
    private MouseJoint mouseJoint;
    public static final short CATEGORY_BODY1 = 0x0001;
    public static final short CATEGORY_BODY2 = 0x0002;
    public static final short CATEGORY_GROUND = 0x0004;
    private boolean inflight = false;
    private ArrayList<Sprite> trajectory = new ArrayList<>();
    private boolean isDragged = false;

    private AngryBirds angryBirds;
    public static ArrayList<Screen> loaded_screens = new ArrayList<>();

    @Override
    public void create() {
        batch = new SpriteBatch();
        angryBirds = new AngryBirds();
        File file = new File("game_data.ser");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        angryBirds.create();
    }

    @Override
    public void resize(int width, int height) {
        angryBirds.resize(width, height);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        angryBirds.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }


//    @Override
//    public void create() {
//        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        world = new World(new Vector2(0, -9.8f), false);
//        batch = new SpriteBatch();
//        debugRenderer = new Box2DDebugRenderer();
//
//
//        BodyDef bodyDef = new BodyDef();
//        bodyDef.position.set((float) (-0.75*camera.viewportWidth/2), -camera.viewportHeight/2 + 47.25f);
//        bodyDef.type = BodyDef.BodyType.StaticBody;
//        body = world.createBody(bodyDef);
//        CircleShape circleShape = new CircleShape();
//        circleShape.setRadius(5f);
//        FixtureDef fixtureDef1 = new FixtureDef();
//        fixtureDef1.shape = circleShape;
//        Fixture fixture1 = body.createFixture(fixtureDef1);
//        circleShape.dispose();
//
//
//        BodyDef bodyDef1 = new BodyDef();
//        bodyDef1.position.set((float) (-0.75*camera.viewportWidth/2), -camera.viewportHeight/2 + 47.25f);
//        bodyDef1.type = BodyDef.BodyType.StaticBody;
//        body1 = world.createBody(bodyDef1);
//        circleShape = new CircleShape();
//        circleShape.setRadius(5f);
//        FixtureDef fixtureDef2 = new FixtureDef();
//        fixtureDef2.shape = circleShape;
//        fixtureDef2.friction = 0.5f;
//        fixtureDef2.restitution = 0.3f;
//        Fixture fixture2 = body1.createFixture(fixtureDef2);
//        body1.setAngularDamping(0.7f);
//        body1.setFixedRotation(false);
//        circleShape.dispose();
//
//        Filter filter1 = fixture1.getFilterData();
//        Filter filter2 = fixture2.getFilterData();
//
//        filter1.categoryBits = CATEGORY_BODY1;
//        filter1.maskBits = ~CATEGORY_BODY2;
//
//        filter2.categoryBits = CATEGORY_BODY2;
//        filter2.maskBits = ~CATEGORY_BODY1;
//
//        fixture1.setFilterData(filter1);
//        fixture2.setFilterData(filter2);
//
//        BodyDef bodyDef2 = new BodyDef();
//        bodyDef2.position.set(0, -camera.viewportHeight/2);
//        bodyDef2.type = BodyDef.BodyType.StaticBody;
//        body2 = world.createBody(bodyDef2);
//        PolygonShape polygonShape = new PolygonShape();
//        polygonShape.setAsBox(camera.viewportWidth/2, 17.25f);
//        FixtureDef fixtureDef = new FixtureDef();
//        fixtureDef.shape = polygonShape;
//        fixtureDef.friction = 0.5f;
//        body2.createFixture(fixtureDef);
//        polygonShape.dispose();
//
//        filter2 = fixture2.getFilterData();
//        filter2.categoryBits = CATEGORY_BODY2;
//        filter2.maskBits = CATEGORY_GROUND;
//
//        fixture2.setFilterData(filter2);
//
//        Filter filter3 = new Filter();
//        filter3.categoryBits = CATEGORY_GROUND;
//        filter3.maskBits = CATEGORY_BODY2;
//        body2.getFixtureList().get(0).setFilterData(filter3);
//
//        RopeJointDef ropeJointDef = new RopeJointDef();
//        ropeJointDef.bodyA = body;
//        ropeJointDef.bodyB = body1;
//        ropeJointDef.localAnchorA.set(0, 0);
//        ropeJointDef.localAnchorB.set(0, 0);
//        ropeJointDef.maxLength = 30f;
//        RopeJoint ropeJoint = (RopeJoint) world.createJoint(ropeJointDef);
//
//        for(int i = 0; i < 10; i++){
//            trajectory.add(new Sprite(new TextureRegion(new Texture("angry-icon.png"), 29, 936, 9, 9)));
//        }
//
//        Gdx.input.setInputProcessor(new InputAdapter(){
//            @Override
//            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//                Vector3 worldcoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
//                isDragged = false;
//                if(body1.getWorldCenter().dst(new Vector2(worldcoordinates.x, worldcoordinates.y)) < 5 && !inflight){
//                    MouseJointDef mouseJointDef = new MouseJointDef();
//                    mouseJointDef.bodyA = body;
//                    mouseJointDef.bodyB = body1;
//                    mouseJointDef.target.set(new Vector2(worldcoordinates.x, worldcoordinates.y));
//                    mouseJointDef.maxForce = 5000f;
//
//                    mouseJoint = (MouseJoint) world.createJoint(mouseJointDef);
//
//                }
//                return true;
//            }
//
//            @Override
//            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//                isDragged = false;
//                if(body1.getWorldCenter().dst(body.getWorldCenter()) < 2){
//                    if(mouseJoint != null) {
//                        world.destroyJoint(mouseJoint);
//                    }
//                    if(world.getJointCount() == 1) {
//                        body1.getPosition().set(body.getPosition().x, body.getPosition().y);
//                        body1.setType(BodyDef.BodyType.StaticBody);
//                    }
//                    mouseJoint = null;
//                }
//                else if(mouseJoint != null) {
//                    body1.setType(BodyDef.BodyType.DynamicBody);
//                    Vector2 mouseReaction = mouseJoint.getReactionForce(1/Gdx.graphics.getDeltaTime()).scl(-1);
//
//                    body1.applyForceToCenter(mouseReaction, true);
//
//                    // Destroy joints after applying forces
//                    world.destroyJoint(ropeJoint);
//                    world.destroyJoint(mouseJoint);
//                    mouseJoint = null;
//                    inflight = true;
//
//
//                    // Adjust density after ensuring forces are applied
//                    body1.getFixtureList().get(0).setDensity(1.0f);
//                    MassData massData = new MassData();
//                    massData.mass = body1.getMass();
//                    massData.I = (float) (0.4f*body1.getMass()*Math.pow(body1.getFixtureList().get(0).getShape().getRadius(), 2));
//                    body1.setMassData(massData);
//                }
//
//                return true;
//            }
//
//            @Override
//            public boolean touchDragged(int screenX, int screenY, int pointer) {
//                if(mouseJoint != null){
//                    isDragged = true;
//                    body1.setType(BodyDef.BodyType.DynamicBody);
//                    Vector3 worldcoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
//                    mouseJoint.setTarget(new Vector2(worldcoordinates.x, worldcoordinates.y));
//                    path_draw();
//                }
//                return true;
//            }
//        });
//
//        ContactListener contactListener = new ContactListener();
//        world.setContactListener(contactListener);
//    }
//
//    private void path_draw(){
//        if(mouseJoint != null) {
//            Body bodyA = mouseJoint.getBodyA();
//            Body bodyB = mouseJoint.getBodyB();
//
//
//            Vector2 mouseReaction = mouseJoint.getReactionForce(1 / Gdx.graphics.getDeltaTime()).scl(-1);
//            Vector2 impulse = mouseReaction.scl(Gdx.graphics.getDeltaTime());
//            Vector2 velocity = impulse.scl(1 / bodyB.getMass());
//            float angle = bodyA.getPosition().sub(bodyB.getPosition()).angleRad();
//
//
//            for (float t = 0; t < 5; t += 0.5f) {
//                float x = (float) (bodyB.getPosition().x + (velocity.len() * Math.cos(angle) * t));
//                float y = (float) (bodyB.getPosition().y + (velocity.len() * Math.sin(angle) * t + 0.5 * world.getGravity().y * Math.pow(t, 2)));
//                trajectory.get((int) (t*2)).setPosition(x - trajectory.get((int) (t*2)).getWidth()/2, y - trajectory.get((int) (t*2)).getHeight()/2);
//            }
//        }
//    }
//
//    @Override
//    public void resize(int width, int height) {
//
//    }
//
//    @Override
//    public void render() {
//        Gdx.gl.glClearColor(0, 0, 0, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//
//        batch.setProjectionMatrix(camera.combined);
//        batch.begin();
//        if(isDragged) {
//            for (Sprite sprite : trajectory) {
//                sprite.draw(batch);
//            }
//        }
//
//        if(inflight){
//            System.out.println(body1.getLinearVelocity().x);
//            for(Sprite sprite : trajectory){
//                if(body1.getLinearVelocity().x > 0){
//                    if(sprite.getX() + sprite.getWidth()/2 <= body1.getPosition().x){
//                        sprite.draw(batch);
//                    }
//                } else if (body1.getLinearVelocity().x < 0) {
//                    if(sprite.getX() + sprite.getWidth()/2 >= body1.getPosition().x){
//                        sprite.draw(batch);
//                    }
//                } else {
//                    if(body1.getLinearVelocity().x == 0 && body1.getLinearVelocity().y > 0){
//                        if(sprite.getY() + sprite.getHeight()/2 <= body1.getPosition().y){
//                            sprite.draw(batch);
//                        }
//                    } else if(body1.getLinearVelocity().x == 0 && body1.getLinearVelocity().y < 0){
//                        if(sprite.getY() + sprite.getHeight()/2 >= body1.getPosition().y){
//                            sprite.draw(batch);
//                        }
//                    }
//                }
//            }
//        }
//        batch.end();
//
//        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
//
//        debugRenderer.render(world, camera.combined);
//    }
//
//
//    @Override
//    public void pause() {
//
//    }
//
//    @Override
//    public void resume() {
//
//    }
//
//    @Override
//    public void dispose() {
//        world.dispose();
//        debugRenderer.dispose();
//    }


}
