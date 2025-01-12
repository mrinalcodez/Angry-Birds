package io.github.some_example_name;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.io.Serializable;
import java.util.ArrayList;

public class SerializableWorld implements Serializable {
    public transient World world;

    private static final float gravity = -9.8f;

    private ArrayList<SerializableBody> bodies = new ArrayList<>();

    public SerializableWorld(){
        world = new World(new Vector2(0, gravity), false);
    }

    public void step(float deltatime, int velocityiterations, int positioniterations){
        world.step(deltatime, velocityiterations, positioniterations);
    }

    public void destroyBody(SerializableBody body){
        world.destroyBody(body.body);
        bodies.remove(body);
    }

    public Joint createJoint(JointDef jointDef){
        return world.createJoint(jointDef);
    }

    public void destroyJoint(Joint joint){
        world.destroyJoint(joint);
    }

    public class SerializableBody implements Serializable {
        public transient Body body;
        public Vector2 position;
        public Vector2[] vertices;
        public float radius;
        public float rotation;
        public float inertia;
        public float restitution;
        public float density;
        public Vector2 linearVelocity;
        public float angularVelocity;
        public BodyDef.BodyType state;
        public String shape;
        public float friction;
        public String data;
        public float angularDamping;

        public SerializableBody(float positionx, float positiony, Vector2[] dimensions, BodyDef.BodyType state, float density, float restitution, float friction, String userData){
            BodyDef bodyDef = new BodyDef();
            bodyDef.position.set(positionx, positiony);
            bodyDef.type = state;
            body = world.createBody(bodyDef);
            FixtureDef fixtureDef = new FixtureDef();
            if(dimensions.length == 1){
                CircleShape circleShape = new CircleShape();
                circleShape.setRadius(dimensions[0].x);
                fixtureDef.shape = circleShape;
                radius = dimensions[0].x;
                shape = "CircleShape";
                fixtureDef.density = density;
                fixtureDef.restitution = restitution;
                fixtureDef.friction = friction;
                body.createFixture(fixtureDef);
                circleShape.dispose();
            } else {
                PolygonShape polygonShape = new PolygonShape();
                polygonShape.set(dimensions);
                fixtureDef.shape = polygonShape;
                vertices = new Vector2[dimensions.length];
                System.arraycopy(dimensions, 0, vertices, 0, dimensions.length);
                shape = "PolygonShape";
                fixtureDef.density = density;
                fixtureDef.restitution = restitution;
                fixtureDef.friction = friction;
                body.createFixture(fixtureDef);
                polygonShape.dispose();
            }
            body.setUserData(userData);
            this.data = userData;
            bodies.add(this);
        }

        public void saveData(){
            this.position = body.getPosition();
            this.linearVelocity = body.getLinearVelocity();
            this.angularVelocity = body.getAngularVelocity();
            this.rotation = body.getAngle();
            this.inertia = body.getInertia();
            this.density = body.getFixtureList().get(0).getDensity();
            this.restitution = body.getFixtureList().get(0).getRestitution();
            this.friction = body.getFixtureList().get(0).getFriction();
            this.angularDamping = body.getAngularDamping();
            this.state = body.getType();
        }

        public void loadData(){
            BodyDef bodyDef = new BodyDef();
            bodyDef.position.set(position.x, position.y);
            bodyDef.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(bodyDef);
            FixtureDef fixtureDef = new FixtureDef();
            if(shape.equals("PolygonShape")){
                PolygonShape polygonShape = new PolygonShape();
                polygonShape.set(vertices);
                fixtureDef.shape = polygonShape;
                fixtureDef.density = density;
                fixtureDef.friction = friction;
                fixtureDef.restitution = restitution;
                body.createFixture(fixtureDef);
                polygonShape.dispose();
            } else {
                CircleShape circleShape = new CircleShape();
                circleShape.setRadius(radius);
                fixtureDef.shape = circleShape;
                fixtureDef.density = density;
                fixtureDef.friction = friction;
                fixtureDef.restitution = restitution;
                body.createFixture(fixtureDef);
                circleShape.dispose();
            }
            body.setUserData(data);
            body.setTransform(position, rotation);
            body.setLinearVelocity(linearVelocity);
            body.setAngularVelocity(angularVelocity);
            body.setAngularDamping(angularDamping);
        }

        public void setAngularDamping(float damping){
            this.angularDamping = damping;
            body.setAngularDamping(damping);
        }

        public Vector2 getSize(){
            if(vertices == null){
                return new Vector2(2*radius, 2*radius);
            }
            else if(vertices.length == 3){
                float minX = Float.MAX_VALUE;
                float maxX = Float.MIN_VALUE;
                float minY = Float.MAX_VALUE;
                float maxY = Float.MIN_VALUE;

                // Iterate through the vertices to find min/max values
                for (Vector2 vertex : vertices) {
                    minX = Math.min(minX, vertex.x);
                    maxX = Math.max(maxX, vertex.x);
                    minY = Math.min(minY, vertex.y);
                    maxY = Math.max(maxY, vertex.y);
                }

                // The width and height of the triangle
                float width = maxX - minX;
                float height = maxY - minY;

                // Return the width and height as a Vector2
                return new Vector2(width, height);
            } else {
                float minX = Float.MAX_VALUE;
                float maxX = Float.MIN_VALUE;
                float minY = Float.MAX_VALUE;
                float maxY = Float.MIN_VALUE;

                // Iterate through the vertices to find min/max values
                for (Vector2 vertex : vertices) {
                    minX = Math.min(minX, vertex.x);
                    maxX = Math.max(maxX, vertex.x);
                    minY = Math.min(minY, vertex.y);
                    maxY = Math.max(maxY, vertex.y);
                }

                // The width and height of the polygon (bounding box)
                float width = maxX - minX;
                float height = maxY - minY;

                // Return the width and height as a Vector2
                return new Vector2(width, height);
            }
        }
    }

    public void setContactListener(ContactListener contactListener){
        world.setContactListener(contactListener);
    }

    public void setContactFilter(ContactFilter contactFilter){
        world.setContactFilter(contactFilter);
    }

    public void loadData(ContactListener contactListener, ContactFilter contactFilter){
        world = new World(new Vector2(0, gravity), false);
        for(SerializableBody body : bodies){
            body.loadData();
        }
        setContactFilter(contactFilter);
        setContactListener(contactListener);
    }

    public void dispose(){
        world.dispose();
    }
}
