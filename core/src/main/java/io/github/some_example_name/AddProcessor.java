package io.github.some_example_name;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import net.dermetfan.gdx.Multiplexer;

public interface AddProcessor {
    public InputMultiplexer inputMultiplexer = new InputMultiplexer();

    public void addProcessor(InputAdapter inputAdapter);
}
