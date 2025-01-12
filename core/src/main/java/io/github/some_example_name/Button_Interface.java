package io.github.some_example_name;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Button_Interface extends SetPosition, AddProcessor{
    public void draw(SerializableRenderer renderer);
    public void setVisibility(boolean visibility);
}
