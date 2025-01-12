package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GlassTextureManager extends TextureManager implements Serializable {
    private TextureState.GlassTextureEnum e;
    private Map<TextureState.GlassTextureEnum, SerializableRenderComponent[]> textureRegions;

    public GlassTextureManager(String object){
        e = TextureState.GlassTextureEnum.valueOf(object);
        textureRegions = new HashMap<>();

        SerializableRenderComponent tex1 = new SerializableRenderComponent(e.getPath(), e.getX(), e.getY(), e.getWidth(), e.getHeight());
        SerializableRenderComponent tex2 = new SerializableRenderComponent(e.getPath(), e.getDamagedx(), e.getDamagedy(), e.getDamagedwidth(), e.getDamagedheight());

        textureRegions.put(e, new SerializableRenderComponent[]{tex1, tex2});
    }

    @Override
    protected SerializableRenderComponent getTextureRegion(Enum<?> enums) {
        if(enums == Glass.GlassEnum.NORMAL){
            return textureRegions.get(e)[0];
        } else if(enums == Glass.GlassEnum.DAMAGED1){
            return textureRegions.get(e)[1];
        }
        return null;
    }
}
