package io.github.some_example_name;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Structure implements Serializable {

    private Level level;
    public float worldwidth;
    public float worldheight;
    private ArrayList<Bird> birds;
    public static ArrayList<Objects> rendering_objects;
    private final SerializableRenderer renderer;
    private boolean jumped;
    private SerializableWorld world;
    public static boolean firstturn = true;
    private io.github.some_example_name.Timer timer;
    public float time_elapsed = 0;
    private int bird_count = 0;
    private transient Animation<SerializableRenderComponent> animation;
    private ArrayList<BlastAnimator> animators = new ArrayList<>();
    public static ArrayList<Damageable> destroy_objects = new ArrayList<>();
    public boolean score_counted = false;
    private SerializableInputMultiplexer inputMultiplexer;
    private SerializableTimer setBodies = new SerializableTimer();
    private ContactListener contactListener;
    private ArrayList<Objects> objects = new ArrayList<>();
    private ArrayList<Damageable> destroyables = new ArrayList<>();
    private boolean isFirstturn;
    SerializableRenderComponent sprite1 = new SerializableRenderComponent("angry-icon.png", 447, 157, 115, 108);
    SerializableRenderComponent sprite2 = new SerializableRenderComponent("angry-icon.png", 41, 716, 124, 115);
    SerializableRenderComponent sprite3 = new SerializableRenderComponent("angry-icon.png", 40, 469, 125, 119);

    public Structure(Level level, SerializableWorld world, float worldwidth, float worldheight, SerializableRenderer renderer, SerializableInputMultiplexer multiplexer){
        this.level = level;
        this.world = world;
        this.worldwidth = worldwidth;
        this.worldheight = worldheight;
        this.renderer = renderer;
        birds = new ArrayList<>();
        this.inputMultiplexer = multiplexer;
        firstturn = true;
        sprite1.setSize(50, 50);
        sprite2.setSize(50, 50);
        sprite3.setSize(50, 50);
        animation = new Animation<>(0.2f, sprite1, sprite2, sprite3);
        rendering_objects = new ArrayList<>();
        createStructure(worldwidth, worldheight);
        jumped = false;
    }

    private void createStructure(float worldwidth, float worldheight){
        if(level.level == 1){
            Ground ground = new Ground("GROUND", world, rectangleVertices(worldwidth, 4*17.25f), worldwidth/2, 2*17.25f);
            Bird bird1 = new Bird("RED", circleVertices(5), world, worldwidth/4 - 15, 4*17.25f + 5, renderer, inputMultiplexer);
            birds.add(bird1);
            bird_count++;
            Bird bird2 = new Bird("RED", circleVertices(5), world, worldwidth/4 - 30, 4*17.25f + 5, renderer, inputMultiplexer);
            birds.add(bird2);
            bird_count++;
            Bird bird3 = new Bird("BLACK", circleVertices(6), world, worldwidth/4 - 45, 4*17.25f + 6, renderer, inputMultiplexer);
            birds.add(bird3);
            bird_count++;
            Pig pig1 = new Pig("BIGPIG", world, circleVertices(10), worldwidth*0.75f + 25f, 4*17.25f + 10, 100);
            Wood wood1 = new Wood("WOOD", world, rectangleVertices(50, 5), worldwidth*0.75f, 4*17.25f + 25, true);
            Wood wood2 = new Wood("WOOD", world, rectangleVertices(50, 5), worldwidth*0.75f + 25 - 2.5f, 4*17.25f + 50 + 2.5f, false);
            Wood wood3 = new Wood("WOOD", world, rectangleVertices(50, 5), worldwidth*0.75f + 45, 4*17.25f + 25, true);
            //Bird bird1 = new Bird("RED", 5, world, worldwidth/4, 4*17.25f + 48 - 5);
            Catapult catapult = new Catapult("LEFTHAND", world, rectangleVertices(10, 50), rectangleVertices(10, 35), worldwidth / 4, 4 * 17.25f + 25, null, renderer, birds, inputMultiplexer);
            for(Objects o : rendering_objects){
                o.body.body.setType(BodyDef.BodyType.StaticBody);
            }
            world.setContactFilter((fixture, fixture1) -> {
                if(fixture.getBody().getUserData() == null || fixture1.getBody().getUserData() == null){
                    return true;
                }
                Bird obj1 = (Bird) getObject(fixture);
                Bird obj2 = (Bird) getObject(fixture1);
                if(obj1 != null){
                    return true;
                }
                if(obj2 != null){
                    return true;
                }
                if(fixture.getBody().getUserData().equals("bird") && fixture1.getBody().getUserData().equals("left hand") || fixture.getBody().getUserData().equals("left hand") && fixture.getBody().getUserData().equals("bird")){
                    return false;
                } else if(fixture.getBody().getUserData().equals("bird") && fixture1.getBody().getUserData().equals("right hand") || fixture.getBody().getUserData().equals("right hand") && fixture1.getBody().getUserData().equals("bird")){
                    return false;
                } else if(fixture.getBody().getUserData().equals("bird") && fixture1.getBody().getUserData().equals("launch body") || fixture.getBody().getUserData().equals("launch body") && fixture1.getBody().getUserData().equals("bird")){
                    return false;
                } else if(fixture.getBody().getUserData().equals("bird") && fixture1.getBody().getUserData().equals("bird")){
                    return false;
                }
                return true;
            });
            contactListener = new ContactListener(rendering_objects, level);
            world.setContactListener(contactListener);
            timer = new Timer(birds, catapult);
            setBodies.scheduleTask(new com.badlogic.gdx.utils.Timer.Task() {
                @Override
                public void run() {
                    for(Objects o : rendering_objects){
                        if(o instanceof Damageable){
                            o.body.body.setType(BodyDef.BodyType.DynamicBody);
                        }
                    }
                }
            }, 1);
        } else if(level.level == 2){
            Ground ground = new Ground("GROUND", world, rectangleVertices(worldwidth, 4*17.25f), worldwidth/2, 2*17.25f);
            Bird bird1 = new Bird("RED", circleVertices(5), world, worldwidth/4 - 15, 4*17.25f + 5, renderer, inputMultiplexer);
            birds.add(bird1);
            bird_count++;
            Bird bird2 = new Bird("RED", circleVertices(5), world, worldwidth/4 - 30, 4*17.25f + 5, renderer, inputMultiplexer);
            birds.add(bird2);
            bird_count++;
            Bird bird3 = new Bird("YELLOW", triangleVertices(5), world, worldwidth/4 - 45, 4*17.25f + 4.5464131996f, renderer, inputMultiplexer);
            birds.add(bird3);
            bird_count++;
            Pig pig1 = new Pig("MOUSTACHEPIG", world, circleVertices(10), worldwidth*0.75f + 42.5f, 4*17.25f + 60, 100);
            Pig pig2 = new Pig("SMALLPIG", world, circleVertices(7), worldwidth*0.75f + 12.5f, 4*17.25f + 7, 60);
            Pig pig3 = new Pig("SMALLPIG", world, circleVertices(7), worldwidth*0.75f + 72.5f, 4*17.25f + 7, 60);

            Wood wood1 = new Wood("WOOD_SMALL", world, rectangleVertices(10, 5), worldwidth*0.75f, 4*17.25f + 5, true);
            Wood wood2 = new Wood("WOOD_LONG", world, rectangleVertices(50, 5), worldwidth*0.75f + 25, 4*17.25f + 25f, true);
            Wood wood3 = new Wood("WOOD", world, rectangleVertices(40, 5), worldwidth*0.75f + 42.5f, 4*17.25f + 52.5f, false);
            Wood wood4 = new Wood("WOOD_LONG", world, rectangleVertices(50, 5), worldwidth*0.75f + 60f, 4*17.25f + 25f, true);
            Wood wood5 = new Wood("WOOD_SMALL", world, rectangleVertices(20, 5), worldwidth*0.75f + 42.5f, 4*17.25f + 2.5f, false);
            Wood wood6 = new Wood("WOOD_SMALL", world, rectangleVertices(20, 5), worldwidth*0.75f + 42.5f, 4*17.25f + 27.5f, false);
            Wood wood7 = new Wood("WOOD_SMALL", world, rectangleVertices(10, 5), worldwidth*0.75f + 85, 4*17.25f + 5, true);

            Glass glass1 = new Glass("GLASS_SMALL", world, rectangleVertices(20, 5), worldwidth*0.75f + 35, 4*17.25f + 15, true);
            Glass glass2 = new Glass("GLASS_SMALL", world, rectangleVertices(20, 5), worldwidth*0.75f + 50, 4*17.25f + 15, true);


            Catapult catapult = new Catapult("LEFTHAND", world, rectangleVertices(10, 50), rectangleVertices(10, 35), worldwidth / 4, 4 * 17.25f + 25, null, renderer, birds, inputMultiplexer);

            for(Objects objects : rendering_objects){
                objects.body.body.setType(BodyDef.BodyType.StaticBody);
            }
            world.setContactFilter((fixture, fixture1) -> {
                if(fixture.getBody().getUserData() == null || fixture1.getBody().getUserData() == null){
                    return true;
                }
                Bird obj1 = (Bird) getObject(fixture);
                Bird obj2 = (Bird) getObject(fixture1);
                if(obj1 != null){
                    return true;
                }
                if(obj2 != null){
                    return true;
                }
                if(fixture.getBody().getUserData().equals("bird") && fixture1.getBody().getUserData().equals("left hand") || fixture.getBody().getUserData().equals("left hand") && fixture.getBody().getUserData().equals("bird")){
                    return false;
                } else if(fixture.getBody().getUserData().equals("bird") && fixture1.getBody().getUserData().equals("right hand") || fixture.getBody().getUserData().equals("right hand") && fixture1.getBody().getUserData().equals("bird")){
                    return false;
                } else if(fixture.getBody().getUserData().equals("bird") && fixture1.getBody().getUserData().equals("launch body") || fixture.getBody().getUserData().equals("launch body") && fixture1.getBody().getUserData().equals("bird")){
                    return false;
                } else if(fixture.getBody().getUserData().equals("bird") && fixture1.getBody().getUserData().equals("bird")){
                    return false;
                }
                return true;
            });
            contactListener = new ContactListener(rendering_objects, level);
            world.setContactListener(contactListener);
            timer = new Timer(birds, catapult);
            setBodies.scheduleTask(new com.badlogic.gdx.utils.Timer.Task() {
                @Override
                public void run() {
                    for(Objects o : rendering_objects){
                        if(o instanceof Damageable){
                            o.body.body.setType(BodyDef.BodyType.DynamicBody);
                        }
                    }
                }
            }, 1);
        } else if(level.level == 3){
            Ground ground = new Ground("GROUND", world, rectangleVertices(worldwidth, 4*17.25f), worldwidth/2, 2*17.25f);

            Bird bird1 = new Bird("YELLOW",  triangleVertices(5), world, worldwidth/4 - 15, 4*17.25f + 5, renderer, inputMultiplexer);
            birds.add(bird1);
            Bird bird2 = new Bird("RED", circleVertices(5), world, worldwidth/4 - 30, 4*17.25f + 5, renderer, inputMultiplexer);
            birds.add(bird2);
            Bird bird3 = new Bird("BLACK", circleVertices(6), world, worldwidth/4 - 45, 4*17.25f + 5, renderer, inputMultiplexer);
            birds.add(bird3);

            Pig pig1 = new Pig("SMALLPIG", world, circleVertices(7), worldwidth*0.75f, 4*17.25f + 57, 60);
            Pig pig2 = new Pig("SMALLPIG", world, circleVertices(7), worldwidth*0.75f + 86.25f, 4*17.25f + 57, 60);
            Pig pig3 = new Pig("KINGPIG", world, circleVertices(12), worldwidth*0.75f + 37.5f, 4*17.25f + 107, 140);

            Wood wood1 = new Wood("WOOD_LONG", world, rectangleVertices(50, 5), worldwidth*0.75f + 15, 4*17.25f + 47.5f, false);
            Wood wood2 = new Wood("WOOD_STUB", world, rectangleVertices(20, 10), worldwidth*0.75f + 60, 4*17.25f + 10, true);
            Wood wood3 = new Wood("WOOD_STUB", world, rectangleVertices(20, 10), worldwidth*0.75f + 75, 4*17.25f + 10, true);
            Wood wood4 = new Wood("WOOD_STUB", world, rectangleVertices(20, 10), worldwidth*0.75f + 90, 4*17.25f + 10, true);
            Wood wood5 = new Wood("WOOD_SQUARE", world, rectangleVertices(20, 20), worldwidth*0.75f + 65, 4*17.25f + 30, false);
            Wood wood6 = new Wood("WOOD", world, rectangleVertices(40, 5), worldwidth*0.75f + 60f, 4*17.25f + 70, true);
            Wood wood7 = new Wood("WOOD", world, rectangleVertices(40, 5), worldwidth*0.75f + 15, 4*17.25f + 70, true);

            Rock rock1 = new Rock("ROCK_STUB", world, rectangleVertices(20, 10), worldwidth*0.75f + 15, 4*17.25f + 10f, true);
            Rock rock2 = new Rock("ROCK_STUB", world, rectangleVertices(20, 10), worldwidth*0.75f + 30, 4*17.25f + 10f, true);
            Rock rock3 = new Rock("ROCK_SQUARE", world, rectangleVertices(20, 20), worldwidth*0.75f + 25, 4*17.25f + 30, false);
            Rock rock4 = new Rock("ROCK", world, rectangleVertices(40, 5), worldwidth*0.75f + 15, 4*17.25f + 42.5f, false);
            Rock rock5 = new Rock("ROCK_LONG", world, rectangleVertices(50, 5), worldwidth*0.75f + 75, 4*17.25f + 47.5f, false);
            Rock rock6 = new Rock("ROCK_LONG", world, rectangleVertices(50, 5), worldwidth*0.75f + 37.5f, 4*17.25f + 92.5f, false);


            Glass glass1 = new Glass("GLASS_STUB", world, rectangleVertices(20, 10), worldwidth*0.75f, 4*17.25f + 10f, true);
            Glass glass2 = new Glass("GLASS_SQUARE", world, rectangleVertices(20, 20), worldwidth*0.75f + 5, 4*17.25f + 30, false);
            Glass glass3 = new Glass("GLASS_SQUARE", world, rectangleVertices(20, 20), worldwidth*0.75f + 85, 4*17.25f + 30, false);
            Glass glass4 = new Glass("GLASS", world, rectangleVertices(40, 5), worldwidth*0.75f + 75, 4*17.25f + 42.5f, false);


            Catapult catapult = new Catapult("LEFTHAND", world, rectangleVertices(10, 50), rectangleVertices(10, 35), worldwidth / 4, 4 * 17.25f + 25, null, renderer, birds, inputMultiplexer);

            for(Objects objects : rendering_objects){
                objects.body.body.setType(BodyDef.BodyType.StaticBody);
            }
            world.setContactFilter((fixture, fixture1) -> {
                if(fixture.getBody().getUserData() == null || fixture1.getBody().getUserData() == null){
                    return true;
                }
                Bird obj1 = (Bird) getObject(fixture);
                Bird obj2 = (Bird) getObject(fixture1);
                if(obj1 != null){
                    return true;
                }
                if(obj2 != null){
                    return true;
                }
                if(fixture.getBody().getUserData().equals("bird") && fixture1.getBody().getUserData().equals("left hand") || fixture.getBody().getUserData().equals("left hand") && fixture.getBody().getUserData().equals("bird")){
                    return false;
                } else if(fixture.getBody().getUserData().equals("bird") && fixture1.getBody().getUserData().equals("right hand") || fixture.getBody().getUserData().equals("right hand") && fixture1.getBody().getUserData().equals("bird")){
                    return false;
                } else if(fixture.getBody().getUserData().equals("bird") && fixture1.getBody().getUserData().equals("launch body") || fixture.getBody().getUserData().equals("launch body") && fixture1.getBody().getUserData().equals("bird")){
                    return false;
                } else if(fixture.getBody().getUserData().equals("bird") && fixture1.getBody().getUserData().equals("bird")){
                    return false;
                }
                return true;
            });
            contactListener = new ContactListener(rendering_objects, level);
            world.setContactListener(contactListener);
            timer = new Timer(birds, catapult);
            setBodies.scheduleTask(new com.badlogic.gdx.utils.Timer.Task() {
                @Override
                public void run() {
                    for(Objects o : rendering_objects){
                        if(o instanceof Damageable){
                            o.body.body.setType(BodyDef.BodyType.DynamicBody);
                        }
                    }
                }
            }, 1);
        }
    }

    public void render(float deltatime) {
//        if(time_elapsed > 10 && !jumped){
//            birds.get(0).body.applyLinearImpulse(new Vector2(0.5f, 15), birds.get(0).body.getWorldCenter(), true);
//            birds.get(0).isturn = true;
//            for(Objects o : birds){
//                if(o != birds.get(0)){
//                    o.body.setTransform(o.body.getPosition().x + 15, o.body.getPosition().y, o.body.getAngle());
//                }
//            }
//            jumped = true;
//        }
//        time_elapsed += deltatime;
        if(checkPigs() && checkBirds()){
            for (Objects o : rendering_objects){
                o.draw(renderer);
            }
            for(BlastAnimator animator : animators){
                if(!animator.isFinished()) {
                    animator.updateTimer(deltatime);
                    renderer.spriteBatch.draw(animator.getCurrentFrame(), animator.position.x - 25, animator.position.y - 25);
                }
            }
            if (time_elapsed > 30) {
                System.out.println("GAME OVER!");
                Level.isGameWon = true;
                time_elapsed = 0;
                return;
            }
            if(!score_counted) {
                for (int i = 0; i < birds.size(); i++) {
                    level.current_score += 100;
                }
                score_counted = true;
            }
            level.star_count = birds.size();
            time_elapsed += deltatime;
        }
        else if (checkBirds()) {
            for(Objects o : rendering_objects){
                o.draw(renderer);
            }
            for(BlastAnimator animator : animators){
                if(!animator.isFinished()) {
                    animator.updateTimer(deltatime);
                    renderer.spriteBatch.draw(animator.getCurrentFrame(), animator.position.x - 25, animator.position.y - 25);
                }
            }
            if(time_elapsed > 10){
                System.out.println("GAME OVER!");
                Level.isGameLost = true;
                time_elapsed = 0;
                return;
            }
            if(!Level.isPlaying){
                time_elapsed = 0;
            }
            time_elapsed += deltatime;
        } else if (checkPigs()) {
            for (Objects o : rendering_objects){
                o.draw(renderer);
            }
            for(BlastAnimator animator : animators){
                if(!animator.isFinished()) {
                    animator.updateTimer(deltatime);
                    renderer.spriteBatch.draw(animator.getCurrentFrame(), animator.position.x - 25, animator.position.y - 25);
                }
            }
            if (time_elapsed > 10) {
                System.out.println("GAME OVER!");
                Level.isGameWon = true;
                time_elapsed = 0;
                return;
            }
            if(!score_counted) {
                for (int i = 0; i < birds.size(); i++) {
                    level.current_score += 100;
                }
                score_counted = true;
            }
            level.star_count = birds.size();
            time_elapsed += deltatime;
        } else {
            if(Level.isPlaying) {
                timer.nextTurn(deltatime, firstturn);
            }
            for (Objects o : rendering_objects) {
                if (o instanceof Damageable) {
                    checkOutsideWorld((Damageable) o);
                }
            }
            for (Objects o : rendering_objects) {
                o.draw(renderer);
            }
            for (Objects o : rendering_objects) {
                if (o instanceof Damageable && ((Damageable) o).getHealth() < 0) {
                    animators.add(new BlastAnimator(animation, o.body.body.getPosition().x - 25, o.body.body.getPosition().y - 25));
                    world.destroyBody(o.body);
                    if (o instanceof Bird) {
                        birds.remove(o);
                    }
                }
            }
            for(BlastAnimator animator : animators){
                if(!animator.isFinished()) {
                    animator.updateTimer(deltatime);
                    renderer.spriteBatch.draw(animator.getCurrentFrame(), animator.position.x - 25, animator.position.y - 25);
                }
            }
            Structure.rendering_objects.removeAll(destroy_objects);
            destroy_objects.clear();
        }
    }

    private Objects getObject(Fixture fixture){
        for(Objects o : rendering_objects){
            if(o.body.body.getFixtureList().get(0).equals(fixture) && o.state == Bird.BirdEnum.HURT){
                return o;
            }
        }
        return null;
    }

    private void checkOutsideWorld(Damageable damageable){
        if(((Objects) damageable).body.body.getPosition().y < 0){
            damageable.dispose();
            if(!(damageable instanceof Bird)){
                level.current_score += damageable.getHealth();
            } else {
                ((Bird) damageable).hasfallen = true;
            }
            damageable.updateHealth(damageable.getHealth() + 1);
        }
    }

    private boolean checkPigs(){
        for(Objects o : rendering_objects){
            if(o instanceof Pig){
                return false;
            }
        }
        return true;
    }

    private boolean checkBirds(){
        for(Objects o : birds){
            if(o instanceof Bird){
                return false;
            }
        }
        for(Objects o : rendering_objects){
            if(o instanceof Bird){
                return false;
            }
        }
        return true;
    }

    private Vector2[] rectangleVertices(float width, float height){
        Vector2[] vertices = new Vector2[4];
        vertices[0] = new Vector2(-width/2, -height/2);
        vertices[1] = new Vector2(-width/2, height/2);
        vertices[2] = new Vector2(width/2, height/2);
        vertices[3] = new Vector2(width/2, -height/2);
        return vertices;
    }

    public Vector2[] circleVertices(float radius){
        Vector2[] vertices = new Vector2[1];
        vertices[0] = new Vector2(radius, radius);
        return vertices;
    }

    public Vector2[] triangleVertices(float radius){
        Vector2[] vertices = new Vector2[3];

        for(int i = 0; i < 3; i++){
            float angle = (float) Math.toRadians(90 + i*120);
            vertices[i] = new Vector2(radius*(float) Math.cos(angle), radius*(float) Math.sin(angle));
        }

        float minY = Math.min(vertices[0].y, Math.min(vertices[1].y, vertices[2].y));
        for(int i = 0; i < 3; i++){
            vertices[i].y -= minY;
        }

        return vertices;
    }

    public float getGroundWidth(){
        return ((Ground) rendering_objects.get(0)).getWidth();
    }

    public float getGroundheight(){
        return ((Ground) rendering_objects.get(0)).getHeight();
    }

    public void saveData() {
        objects = rendering_objects;
        destroyables = destroy_objects;
        isFirstturn = firstturn;
        for(Objects o : rendering_objects){
            o.saveData();
        }
    }

    public void loadData(){
        rendering_objects = objects;
        destroy_objects = destroyables;
        firstturn = isFirstturn;
        sprite1.loadData();
        sprite2.loadData();
        sprite3.loadData();
        animation = new Animation<>(0.2f, sprite1, sprite2, sprite3);
        animators.clear();
        setBodies.loadData();
        world.loadData(contactListener, (fixture, fixture1) -> {
            if(fixture.getBody().getUserData() == null || fixture1.getBody().getUserData() == null){
                return true;
            }
            Bird obj1 = (Bird) getObject(fixture);
            Bird obj2 = (Bird) getObject(fixture1);
            if(obj1 != null){
                return true;
            }
            if(obj2 != null){
                return true;
            }
            if(fixture.getBody().getUserData().equals("bird") && fixture1.getBody().getUserData().equals("left hand") || fixture.getBody().getUserData().equals("left hand") && fixture.getBody().getUserData().equals("bird")){
                return false;
            } else if(fixture.getBody().getUserData().equals("bird") && fixture1.getBody().getUserData().equals("right hand") || fixture.getBody().getUserData().equals("right hand") && fixture1.getBody().getUserData().equals("bird")){
                return false;
            } else if(fixture.getBody().getUserData().equals("bird") && fixture1.getBody().getUserData().equals("launch body") || fixture.getBody().getUserData().equals("launch body") && fixture1.getBody().getUserData().equals("bird")){
                return false;
            } else if(fixture.getBody().getUserData().equals("bird") && fixture1.getBody().getUserData().equals("bird")){
                return false;
            }
            return true;
        });
        setBodies.scheduleTask(new com.badlogic.gdx.utils.Timer.Task() {
            @Override
            public void run() {
                for(Objects o : rendering_objects){
                    if(o instanceof Damageable) {
                        o.body.body.setType(BodyDef.BodyType.DynamicBody);
                    }
                }
            }
        }, 2);
        for(BlastAnimator animator : animators){
            animator.loadData();
        }
        for(Objects o : rendering_objects){
            o.loadData();
        }
    }
}
