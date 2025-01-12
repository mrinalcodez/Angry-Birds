package io.github.some_example_name;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

import java.io.Serializable;

public class SerializableRenderer implements Serializable {
    public float viewportWidth, viewportHeight;

    public transient OrthographicCamera camera;

    public float positionx, positiony;

    public transient SpriteBatch spriteBatch;

    public SerializableRenderer(float width, float height){
        camera = new OrthographicCamera(width, height);
        this.viewportWidth = width;
        this.viewportHeight = height;
        spriteBatch = new SpriteBatch();
        camera.position.set(viewportWidth/2, viewportHeight/2, 0);
        this.positionx = camera.position.x;
        this.positiony = camera.position.y;
    }

    private Matrix4 getCameraMatrix(){
        return camera.combined;
    }

    public void translate(float x, float y, float z){
        this.positionx += x;
        this.positiony += y;
        camera.translate(x, y, z);
    }


    public void update(){
        camera.update();
    }

    public void setSize(float width, float height){
        this.viewportWidth = width;
        this.viewportHeight = height;
        camera.viewportWidth = viewportWidth;
        camera.viewportHeight = viewportHeight;
        camera.position.set(viewportWidth/2, viewportHeight/2, 0);
        this.positionx = camera.position.x;
        this.positiony = camera.position.y;
    }

    public void start(){
        update();
        spriteBatch.setProjectionMatrix(getCameraMatrix());
        this.viewportWidth = camera.viewportWidth;
        this.viewportHeight = camera.viewportHeight;
        spriteBatch.begin();
    }

    public void draw(Sprite sprite){
        sprite.draw(spriteBatch);
    }

    public void draw(Texture texture, float x, float y, float width, float height){
        spriteBatch.draw(texture, x, y, width, height);
    }

    public void end(){
        spriteBatch.end();
    }

    public void loadData(){
        camera = new OrthographicCamera(viewportWidth, viewportHeight);
        spriteBatch = new SpriteBatch();
        this.viewportWidth = camera.viewportWidth;
        this.viewportHeight = camera.viewportHeight;
        camera.position.set(positionx, positiony, 0);
    }

    public void dispose(){
        spriteBatch.dispose();
    }
}
