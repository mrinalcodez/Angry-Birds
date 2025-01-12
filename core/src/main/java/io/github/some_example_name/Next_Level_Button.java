package io.github.some_example_name;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;

import java.awt.*;
import java.io.Serializable;

public class Next_Level_Button implements Button_Interface, Serializable {
    private final SerializableRenderComponent button_texture;
    private Rectangle button;
    private float size;
    private static boolean isButtonVisible;
    private CustomInputAdapter inputAdapter;
    private SerializableInputMultiplexer multiplexer;
    public Next_Level_Button(String path, int x, int y, int width, int height, float size, SerializableRenderer renderer, SerializableInputMultiplexer multiplexer) {
        this.size = size;
        button_texture = new SerializableRenderComponent(path, x, y, width, height);
        button_texture.setSize(size*(renderer.viewportWidth/renderer.viewportHeight), size);
        button = new Rectangle((int) button_texture.x, (int) button_texture.y, (int) button_texture.width, (int) button_texture.height);
        this.multiplexer = multiplexer;
    }


    @Override
    public void draw(SerializableRenderer renderer) {
        if(isButtonVisible) {
            button_texture.draw(renderer, button_texture.x, button_texture.y, button_texture.width, button_texture.height, button_texture.rotation);
        }
    }

    @Override
    public void setVisibility(boolean visibility) {
        isButtonVisible = visibility;
    }

    @Override
    public void addProcessor(InputAdapter inputAdapter) {
        multiplexer.addProcessor(inputAdapter);
    }

    @Override
    public void setPosition(float x, float y) {
        button_texture.setPosition(x - button_texture.width/2, y - button_texture.height/2);
        button.setSize((int) button_texture.width, (int) button_texture.height);
        button.setLocation((int) button_texture.x, (int) button_texture.y);
    }

    public void setFunctionality(AngryBirds game, Level level, SerializableRenderer renderer){
        inputAdapter = new CustomInputAdapter("Button", game){
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int b) {
                Vector3 worldcoordinates = renderer.camera.unproject(new Vector3(screenX, screenY, 0));
                if(isButtonVisible && button.contains(worldcoordinates.x, worldcoordinates.y)){
                    inputMultiplexer.clear();
                    Level next_level = new Level(game, level.worldwidth, level.worldheight, level.level+1);
                    game.setScreen(next_level);
                    return true;
                }
                return false;
            }
        };
        addProcessor(inputAdapter);
    }

    public void setPosition(float v, float v1, SerializableRenderer renderer) {
        button_texture.setSize(size*(renderer.viewportWidth/renderer.viewportHeight), size);
        setPosition(v, v1);
    }

    public void loadData(AngryBirds game, Level level, SerializableRenderer serializableRenderer){
        button_texture.loadData();
        inputAdapter.setGame(game);
    }
}
