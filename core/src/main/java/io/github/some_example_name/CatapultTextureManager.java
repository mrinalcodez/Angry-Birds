package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class CatapultTextureManager extends TextureManager {
    private Map<TextureState.CatapultTextureEnum, SerializableRenderComponent[]> textureRegions;
    private final TextureState.CatapultTextureEnum e;
    public CatapultTextureManager(String object) {
        textureRegions = new HashMap<>();
        e = TextureState.CatapultTextureEnum.valueOf(object);

        SerializableRenderComponent tex1 = new SerializableRenderComponent(e.getPath(), e.getX(), e.getY(), e.getWidth(), e.getHeight());

        textureRegions.put(e, new SerializableRenderComponent[]{tex1});
    }

    @Override
    protected SerializableRenderComponent getTextureRegion(Enum<?> enums) {
        return textureRegions.get(e)[0];
    }
}
