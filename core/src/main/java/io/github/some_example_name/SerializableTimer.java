package io.github.some_example_name;

import com.badlogic.gdx.utils.Timer;

import java.io.Serializable;

public class SerializableTimer implements Serializable {
    private transient Timer timer;

    public SerializableTimer(){
        timer = new Timer();
    }

    public void scheduleTask(Timer.Task task, int delayseconds){
        timer.scheduleTask(task, delayseconds);
    }

    public void loadData(){
        timer = new Timer();
    }
}
