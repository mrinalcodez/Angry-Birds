package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class WoodTextureManager extends TextureManager implements Serializable {
    private TextureState.WoodTextureEnum e;
    private Map<TextureState.WoodTextureEnum, SerializableRenderComponent[]> textureRegions;

    public WoodTextureManager(String object) {
        textureRegions = new HashMap<>();

        e = TextureState.WoodTextureEnum.valueOf(object);

        SerializableRenderComponent tex1 = new SerializableRenderComponent(e.getPath(), e.getX(), e.getY(), e.getWidth(), e.getHeight());

        SerializableRenderComponent tex2 = new SerializableRenderComponent(e.getPath(), e.getDamagedx(), e.getDamagedy(), e.getDamagedwidth(), e.getDamagedheight());

        textureRegions.put(e, new SerializableRenderComponent[]{tex1, tex2});

    }

    @Override
    protected SerializableRenderComponent getTextureRegion(Enum<?> enums) {
        if(enums == Wood.WoodEnum.DAMAGED1){
            return textureRegions.get(e)[1];
        } return textureRegions.get(e)[0];
    }
}
