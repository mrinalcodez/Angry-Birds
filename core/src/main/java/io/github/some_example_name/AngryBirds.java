package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import com.google.gson.Gson;

public class AngryBirds extends Game implements Serializable {
    public static final int MAX_LEVELS = 3;

    @Override
    public void create() {
        Screen menu = new Menu(this, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Main.loaded_screens.add(menu);
        this.setScreen(menu);
    }

    public void render(float v){
        this.getScreen().render(v);
    }

    public void resize(int width, int height){
        this.getScreen().resize(width, height);
    }
}
