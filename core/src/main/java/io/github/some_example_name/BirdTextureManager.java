package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class BirdTextureManager extends TextureManager{
    private final TextureState.BirdTextureEnum e;
    private Map<TextureState.BirdTextureEnum, SerializableRenderComponent[]> textureRegions;

    public BirdTextureManager(String object){
        textureRegions = new HashMap<>();

        e = TextureState.BirdTextureEnum.valueOf(object);
        SerializableRenderComponent tex1 = new SerializableRenderComponent(e.getPath(), e.getX(), e.getY(), e.getWidth(), e.getHeight());

        SerializableRenderComponent tex2 = new SerializableRenderComponent(e.getPath(), e.getblinkx(), e.getblinky(), e.getBlinkwidth(), e.getBlinkheight());

        SerializableRenderComponent tex3 = new SerializableRenderComponent(e.getPath(), e.getHurtx(), e.getHurty(), e.getHurtwidth(), e.getHurtheight());

        textureRegions.put(e, new SerializableRenderComponent[]{tex1, tex2, tex3});

    }

    @Override
    public SerializableRenderComponent getTextureRegion(Enum<?> enums) {
        if(enums == Bird.BirdEnum.BLINKING){
            return textureRegions.get(e)[1];
        } else if(enums == Bird.BirdEnum.HURT){
            return textureRegions.get(e)[2];
        }
        return textureRegions.get(e)[0];
    }

    public TextureState.BirdTextureEnum getType(){
        return e;
    }
}
