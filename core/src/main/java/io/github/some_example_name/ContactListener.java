package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;

import java.io.Serializable;
import java.util.ArrayList;

public class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener, Serializable {
    private ArrayList<io.github.some_example_name.Objects> objects;
    private Level level;
    private Damageable obj1;
    private Damageable obj2;
    private boolean allowDamage = false;
    private Vector2 linearVelocity;
    private float angularVelocity;

    public ContactListener(ArrayList<io.github.some_example_name.Objects> objects, Level level){
        this.objects = objects;
        this.level = level;
        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                allowDamage = true;
            }
        }, 2);
    }

    @Override
    public void beginContact(Contact contact) {
/*
        Body body1 = contact.getFixtureA().getBody();
        Body body2 = contact.getFixtureB().getBody();
        if(body2.getFixtureList().get(0).getShape() instanceof CircleShape) {
            System.out.println(body1.getMassData().I);
            body2.setAngularVelocity(body2.getFixtureList().get(0).getFriction() * (body2.getFixtureList().get(0).getRestitution() * body2.getLinearVelocity().y - body2.getLinearVelocity().y) * body2.getFixtureList().get(0).getShape().getRadius() / body2.getMassData().I);
        }
        else if(body1.getFixtureList().get(0).getShape() instanceof CircleShape){
            body1.setAngularVelocity(body1.getFixtureList().get(0).getFriction() * (body1.getFixtureList().get(0).getRestitution() * body1.getLinearVelocity().y - body1.getLinearVelocity().y) * body1.getFixtureList().get(0).getShape().getRadius() / body1.getMassData().I);
        }
*/
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        obj1 = getObject(bodyA);
        obj2 = getObject(bodyB);
        if(obj1 != null && obj1 instanceof Bird && ((Bird) obj1).islaunched){
            linearVelocity = bodyA.getLinearVelocity().cpy();
            angularVelocity = bodyA.getAngularVelocity();
        } else if(obj2 != null && obj2 instanceof Bird && ((Bird) obj2).islaunched){
            linearVelocity = bodyB.getLinearVelocity().cpy();
            angularVelocity = bodyB.getAngularVelocity();
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

//        Body body1 = contact.getFixtureA().getBody();
//        Body body2 = contact.getFixtureB().getBody();
//
//        if(body2.getFixtureList().get(0).getShape() instanceof CircleShape){
//            body2.setLinearDamping(0.5f);
//        }
//        else if(body2.getFixtureList().get(0).getShape() instanceof CircleShape){
//            body1.setLinearDamping(0.5f);
//        }
        float impactForce = 0;
        for (float impulse : contactImpulse.getNormalImpulses()) {
            impactForce += Math.abs(impulse); // Use absolute value to account for both positive and negative impulses
        }
        for(float impulse : contactImpulse.getTangentImpulses()){
            impactForce += Math.abs(impulse);
        }

// Set a threshold for the impact force to start applying damage
        float damageThreshold = 10.0f;  // Threshold where damage starts
        float damageScale = 1.0f;  // Damage scaling factor, you can adjust this as needed

// Normalize the impact force to the range of health reduction (example: 0 to 100 health points)
        float damageAmount = Math.max(0, (impactForce - damageThreshold) * damageScale);

// Apply damage if the damage amount is significant
        int scaledHealth = (int) damageAmount;

        if (scaledHealth > 0 && allowDamage) {
            if(obj1 != null && obj1 instanceof Bird && ((Bird) obj1).islaunched){
                ((Bird) obj1).hasAttacked = true;
                ((Bird) obj1).state = Bird.BirdEnum.HURT;
                if(((Bird) obj1).birdTextureManager.getType() == TextureState.BirdTextureEnum.BLACK){
                    ((Bird) obj1).powerUp.activate(((Bird) obj1).powerUp.getE());
                }
            } else if(obj2 != null && obj2 instanceof Bird && ((Bird) obj2).islaunched){
                ((Bird) obj2).hasAttacked = true;
                ((Bird) obj2).state = Bird.BirdEnum.HURT;
                if (((Bird) obj2).birdTextureManager.getType() == TextureState.BirdTextureEnum.BLACK){
                    ((Bird) obj2).powerUp.activate((Bird) obj2);
                }
            }
            if(obj1 != null && !(obj1 instanceof Bird)){
                obj1.updateHealth(scaledHealth);
                level.current_score += scaledHealth;
                if(obj2 != null && obj2 instanceof Bird && obj1.getHealth() < 0){
                    ((Bird) obj2).body.body.setLinearVelocity(linearVelocity);
                    ((Bird) obj2).body.body.setAngularVelocity(angularVelocity);
                }
            }
            if(obj2 != null && !(obj2 instanceof Bird)){
                obj2.updateHealth(scaledHealth);
                level.current_score += scaledHealth;
                if(obj1 != null && obj1 instanceof Bird && obj2.getHealth() < 0){
                    ((Bird) obj1).body.body.setLinearVelocity(linearVelocity);
                    ((Bird) obj1).body.body.setAngularVelocity(angularVelocity);
                }
            }
        }
    }

    private Damageable getObject(Body body){
        for(io.github.some_example_name.Objects o : Structure.rendering_objects){
            if(o.body.body.equals(body) && o instanceof Damageable){
                return (Damageable) o;
            }
        }
        return null;
    }

    public void setLevel(Level level){
        this.level = level;
    }
}
