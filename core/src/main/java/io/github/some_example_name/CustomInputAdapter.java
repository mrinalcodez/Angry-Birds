package io.github.some_example_name;

import com.badlogic.gdx.InputAdapter;

import java.io.Serializable;

public class CustomInputAdapter extends InputAdapter implements Serializable {
    public String name;
    public AngryBirds game;

    public CustomInputAdapter(String name, AngryBirds game){
        this.name = name;
        this.game = game;
    }

    public void setGame(AngryBirds game){
        this.game = game;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return super.touchDragged(screenX, screenY, pointer);
    }
}
