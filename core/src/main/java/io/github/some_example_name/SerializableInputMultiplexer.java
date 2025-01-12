package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;

import java.io.Serializable;
import java.util.ArrayList;

public class SerializableInputMultiplexer implements Serializable {
    public transient InputMultiplexer inputMultiplexer;
    public ArrayList<CustomInputAdapter> inputAdapters = new ArrayList<>();

    public SerializableInputMultiplexer(){
        inputMultiplexer = new InputMultiplexer();
    }

    public void addProcessor(InputProcessor processor){
        inputAdapters.add((CustomInputAdapter) processor);
        inputMultiplexer.addProcessor(processor);
    }

    public void removeProcessor(InputProcessor processor) {
        inputMultiplexer.removeProcessor(processor);
        inputAdapters.remove((CustomInputAdapter) processor);
    }

    public void loadData(){
        inputMultiplexer = new InputMultiplexer();
        for(CustomInputAdapter inputAdapter : inputAdapters){
            inputMultiplexer.addProcessor(inputAdapter);
        }
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public void setConnection(){
        Gdx.input.setInputProcessor(inputMultiplexer);
    }
}
