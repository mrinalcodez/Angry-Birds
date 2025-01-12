package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Math.floor;

public class Timer implements Serializable {
    private ArrayList<Bird> objects;
    private Catapult catapult;
    private boolean jumped = false;
    public float wait_time = 0;
    public int count;
    public float time_elapsed = 0;
    public Timer(ArrayList<Bird> objects, Catapult catapult){
        this.objects = objects;
        this.catapult = catapult;
        count = objects.size();
    }

    public void nextTurn(float deltatime, boolean first) {
//        if(time_elapsed > 10 && count == 0 && !bird.islaunched && !bird.isSetOnCatapult && catapult.getBird() == null){
//            while (wait_time <= 0.2){
//                wait_time += deltatime;
//            }
//            wait_time = 0;
//            bird.body.applyLinearImpulse(new Vector2(0.5f, 15), bird.body.getWorldCenter(), true);
//            bird.isturn = true;
//
//            time_elapsed = 0;
//        }
//        else if(time_elapsed > 10 && count > 0 && catapult.getBird() == null && !jumped && Math.abs((launched_birds.get(launched_birds.size()-1)).body.getAngularVelocity()) <= 0.001 && (launched_birds.get(launched_birds.size()-1)).body.getLinearVelocity().len() <= 0.001) {
//            while(wait_time <= 0.2){
//                wait_time += deltatime;
//            }
//            wait_time = 0;
//            bird.body.applyLinearImpulse(new Vector2(0.5f, 15), bird.body.getWorldCenter(), true);
//            bird.isturn = true;
//
//            time_elapsed = 0;
//            jumped = true;
//        }
//        if(jumped) {
//            time_elapsed = 0;
//        }
//        if(catapult.getBird() != null || (launched_birds.get(launched_birds.size()-1).body.getLinearVelocity().len() <= 0.001 && (launched_birds.get(launched_birds.size()-1).body.getAngularVelocity() <= 0.001))){
//            time_elapsed = 0;
//        }
//        time_elapsed += deltatime;
//        if (new Vector2(catapult.getRightHand().body.getWorldCenter().x - bodySize(catapult.getRightHand().body).x / 2, catapult.getRightHand().body.getWorldCenter().y + bodySize(catapult.getRightHand().body).y / 2).dst(bird.body.getWorldCenter()) <= 5) {
//            catapult.setBird(bird);
//            count++;
//            launched_birds.add(bird);
//            objects.remove(bird);
//            for(Objects o : objects){
//                o.body.setTransform(o.body.getPosition().x + 15, o.body.getPosition().y, o.body.getAngle());
//            }
//            if(objects.isEmpty()){
//                System.out.println("GAME OVER!");
//            } else {
//                bird = objects.get(0);
//            }
//        }
        if(first){
            while (time_elapsed > 10){
                while (wait_time <= 0.2){
                    wait_time += deltatime;
                }
                wait_time = 0;
                float gravity = Math.abs(objects.get(0).world.world.getGravity().y);
                float velocity = (float) Math.sqrt(2*gravity*50);
                objects.get(0).body.body.applyLinearImpulse(new Vector2(0.5f, objects.get(0).body.body.getMass()*velocity), objects.get(0).body.body.getWorldCenter(), true);
                objects.get(0).isturn = true;
                time_elapsed = 0;
            }
            time_elapsed += deltatime;
        }
        else if(count > objects.size()){
            if(time_elapsed >= 10) {
                while (wait_time <= 0.2) {
                    wait_time += deltatime;
                }
                wait_time = 0;
                float gravity = Math.abs(objects.get(0).world.world.getGravity().y);
                float velocity = (float) Math.sqrt(2 * gravity * 50);
                float impulse = objects.get(0).body.body.getMass() * velocity;
                objects.get(0).body.body.applyLinearImpulse(new Vector2(0.5f, impulse), objects.get(0).body.body.getWorldCenter(), true);
                objects.get(0).isturn = true;
                count = objects.size();
                time_elapsed = 0;
            }
            time_elapsed += deltatime;
        }
        for(Bird b : objects){
            if(new Vector2(catapult.getRightHand().body.body.getWorldCenter().x - catapult.rightHand.body.getSize().x/2, catapult.getRightHand().body.body.getWorldCenter().y + catapult.rightHand.body.getSize().y/2).dst(b.body.body.getPosition()) <= 10 && catapult.getBird() == null && !b.islaunched){
                catapult.setBird(b);
                Structure.firstturn = false;
                for(Objects o : Structure.rendering_objects){
                    if(o instanceof Bird){
                        o.body.body.setTransform(o.body.body.getPosition().x + 15, o.body.body.getPosition().y, o.body.body.getAngle());
                    }
                }
                count = objects.size();
            }
        }
    }

    private Vector2 bodySize(Body body){
        float minX = Float.MAX_VALUE, minY = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE, maxY = Float.MIN_VALUE;

        if(body.getFixtureList().get(0).getShape() == null){
            System.out.println(true);
        }

        Shape shape = body.getFixtureList().get(0).getShape();
        if (shape instanceof PolygonShape) {
            PolygonShape polygonShape = (PolygonShape) shape;
            Vector2 vertex = new Vector2();
            Vector2 worldVertex = new Vector2(); // To store the world coordinates

            for (int i = 0; i < polygonShape.getVertexCount(); i++) {
                polygonShape.getVertex(i, vertex); // Get the local vertex
                body.getWorldPoint(vertex); // Convert to world coordinates

                minX = Math.min(minX, worldVertex.x);
                maxX = Math.max(maxX, worldVertex.x);
                minY = Math.min(minY, worldVertex.y);
                maxY = Math.max(maxY, worldVertex.y);
            }
        } else if(shape instanceof CircleShape){
            CircleShape circleShape = (CircleShape) shape;
            float radius = circleShape.getRadius();
            Vector2 position = body.getWorldCenter();

            minX = Math.min(minX, position.x - radius);
            maxX = Math.max(maxX, position.x + radius);
            minY = Math.min(minY, position.y - radius);
            maxY = Math.max(maxY, position.y + radius);
        }

        float width = maxX - minX;
        float height = maxY - minY;

        return new Vector2(width, height);
    }


    public void setObjects(ArrayList<Bird> objects){
        this.objects = objects;
    }

    public void setCatapult(Catapult catapult){
        this.catapult = catapult;
    }
}
