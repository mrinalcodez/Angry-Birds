package io.github.some_example_name;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class RestoreButton implements Button_Interface, Serializable {
    private SerializableRenderComponent button_texture;
    private Rectangle button;
    private float size;
    private static boolean isButtonVisible;
    private CustomInputAdapter inputAdapter;
    private SerializableInputMultiplexer multiplexer;
    public RestoreButton(String path, int x, int y, int width, int height, float size, SerializableRenderer renderer, SerializableInputMultiplexer multiplexer){
        this.size = size;
        button_texture = new SerializableRenderComponent(path, x, y, width, height);
        button_texture.setSize(size*(renderer.viewportWidth/renderer.viewportHeight), size);
        button = new Rectangle((int) button_texture.x, (int) button_texture.y, (int) button_texture.width, (int) button_texture.height);
        this.multiplexer = multiplexer;
    }

    @Override
    public void setPosition(float x, float y) {
        button_texture.setPosition(x, y);
        button.setSize((int) button_texture.width, (int) button_texture.height);
        button.setLocation((int) button_texture.x, (int) button_texture.y);
    }

    @Override
    public void draw(SerializableRenderer renderer) {
        if(isButtonVisible) {
            button_texture.draw(renderer, button_texture.x, button_texture.y, button_texture.width, button_texture.height, button_texture.rotation);
        }
    }

    public void setFunctionality(AngryBirds game, Level level, SerializableRenderer renderer) {
        inputAdapter = new CustomInputAdapter("Button", game){
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int b) {
                Vector3 worldcoordinates = renderer.camera.unproject(new Vector3(screenX, screenY, 0));

                if(isButtonVisible && button.contains(worldcoordinates.x, worldcoordinates.y)){
                    if(!Level.isGameWon && !Level.isGameLost){
                        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("game_data.ser"))){
                            level.dispose();
                            Level level1 = (Level) ois.readObject();
                            level1.loadLevel(game);
                            game.setScreen(level1);
                            System.out.println("Game loaded successfully");
                        } catch (IOException | ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    return true;
                }
                return false;
            }
        };
        addProcessor(inputAdapter);
    }

    @Override
    public void setVisibility(boolean visibility){
        isButtonVisible = visibility;
    }

    public void setPosition(float x, float y, SerializableRenderer renderer) {
        button_texture.setSize(size*(renderer.viewportWidth/renderer.viewportHeight), size);
        setPosition(x, y);
    }

    @Override
    public void addProcessor(InputAdapter inputAdapter) {
        multiplexer.addProcessor(inputAdapter);
    }

    public void loadData(AngryBirds game, Level level, SerializableRenderer renderer) {
        button_texture.loadData();
        inputAdapter.setGame(game);
    }
}
