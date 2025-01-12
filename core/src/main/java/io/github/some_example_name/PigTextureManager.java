package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PigTextureManager extends TextureManager implements Serializable {
    private TextureState.PigTextureEnum e;
    private Map<TextureState.PigTextureEnum, SerializableRenderComponent[]> textureRegions;

    public PigTextureManager(String object) {
        textureRegions = new HashMap<>();

        e = TextureState.PigTextureEnum.valueOf(object);

        SerializableRenderComponent tex1 = new SerializableRenderComponent(e.getPath(), e.getX(), e.getY(), e.getWidth(), e.getHeight());
        SerializableRenderComponent tex2 = new SerializableRenderComponent(e.getPath(), e.getblinkx(), e.getblinky(), e.getBlinkwidth(), e.getBlinkheight());
        SerializableRenderComponent tex3 = new SerializableRenderComponent(e.getPath(), e.getHurtx(), e.getHurty(), e.getHurtwidth(), e.getHurtheight());

        textureRegions.put(e, new SerializableRenderComponent[]{tex1, tex2, tex3});
    }

    @Override
    protected SerializableRenderComponent getTextureRegion(Enum<?> enums) {
        if(enums == Pig.PigEnum.BLINKING){
            return textureRegions.get(e)[1];
        } else if(enums == Pig.PigEnum.HURT){
            return textureRegions.get(e)[2];
        }
        else {
            return textureRegions.get(e)[0];
        }
    }
}
