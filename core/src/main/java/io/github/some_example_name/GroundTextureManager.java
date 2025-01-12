package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class GroundTextureManager extends TextureManager{
    private TextureState.GroundTextureEnum e;
    private Map<TextureState.GroundTextureEnum, SerializableRenderComponent[]> textureRegions;

    public GroundTextureManager(String object){
        textureRegions = new HashMap<>();
        e = TextureState.GroundTextureEnum.valueOf(String.valueOf(object));

        SerializableRenderComponent tex1 = new SerializableRenderComponent(e.getPath(), e.getX(), e.getY(), e.getWidth(), e.getHeight());
        textureRegions.put(e, new SerializableRenderComponent[]{tex1});
    }

    @Override
    protected SerializableRenderComponent getTextureRegion(Enum<?> enums) {
        if(enums == Ground.GroundEnum.NORMAL){
            return textureRegions.get(e)[0];
        }
        return null;
    }
}
