package io.github.some_example_name;

import java.io.Serializable;

public abstract class TextureManager implements Serializable {
    protected abstract SerializableRenderComponent getTextureRegion(Enum<?> enums);
}
