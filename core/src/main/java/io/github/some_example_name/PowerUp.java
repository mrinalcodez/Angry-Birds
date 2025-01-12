package io.github.some_example_name;

import java.io.Serializable;

public abstract class PowerUp<T> implements Serializable {
    private final T T;
    protected String name;
    protected boolean isactivated;

    public PowerUp(T t, String name){
        T = t;
        this.name = name;
        this.isactivated = false;
    }

    public void activate(T target) {
        applyEffect(target);
        isactivated = true;
    }

    protected T getE(){
        return T;
    }

    protected abstract void applyEffect(T target);
    protected abstract void loadData();
}
