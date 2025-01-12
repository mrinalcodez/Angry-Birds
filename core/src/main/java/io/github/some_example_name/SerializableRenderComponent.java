package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;

import java.io.Serializable;

public class SerializableRenderComponent implements Serializable {
    public String path;
    public float x, y, width, height;
    public int regionx, regiony, regionwidth, regionheight;
    public float rotation;
    public transient Sprite sprite;
    public transient Texture texture;
    public transient BitmapFont font;
    public transient GlyphLayout glyphLayout;
    public String text;
    public float originx, originy;
    public String renderComponent;
    private boolean pixmapuse;

    public SerializableRenderComponent(String path, int regionx, int regiony, int regionwidth, int regionheight){
        texture = new Texture(path);
        this.path = path;
        sprite = new Sprite(texture, regionx, regiony, regionwidth, regionheight);
        this.regionx = regionx;
        this.regiony = regiony;
        this.regionwidth = regionwidth;
        this.regionheight = regionheight;
        this.renderComponent = "Sprite";
    }

    public SerializableRenderComponent(String path, boolean usePixmap, float width, float height){
        if(usePixmap){
            this.pixmapuse = true;
            Pixmap pixmap = new Pixmap(Gdx.files.internal("trail.png"));
            for(int x = 0; x < pixmap.getWidth(); x++){
                for(int y = 0; y < pixmap.getHeight(); y++){
                    int color = pixmap.getPixel(x, y);
                    Color c = new Color(color);

                    c.r = Math.min(c.r*1.5f, 1.0f);
                    c.g = Math.min(c.g*1.5f, 1.0f);
                    c.b = Math.min(c.b*1.5f, 1.0f);
                    pixmap.drawPixel(x, y, c.toIntBits());
                }
            }
            texture = new Texture(pixmap);
        } else {
            texture = new Texture(path);
        }
        this.renderComponent = "Texture";
        this.width = width;
        this.height = height;
    }

    public SerializableRenderComponent(){
        font = new BitmapFont();
        glyphLayout = new GlyphLayout();
        this.renderComponent = "Font";
    }

    public void setText(String text){
        this.text = text;
        glyphLayout.setText(font, text);
        this.width = glyphLayout.width;
        this.height = glyphLayout.height;
    }

    public void draw(SerializableRenderer renderer, float x, float y){
        font.draw(renderer.spriteBatch, glyphLayout, x, y);
    }

    public void setSize(float width, float height){
        this.width = width;
        this.height = height;
        sprite.setSize(width, height);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        sprite.setPosition(x, y);
    }

    private void setData(float x, float y, float width, float height, float rotation){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
    }

    public void draw(SerializableRenderer renderer, float x, float y, float width, float height, float rotation){
        if(sprite == null) {
            renderer.draw(texture, x, y, width, height);
        } else {
            renderer.draw(sprite);
        }
    }

    public void setColor(float r, float g, float b, float a){
        if(font == null) {
            sprite.setColor(r, g, b, a);
        } else {
            font.setColor(r, g, b, a);
        }
    }

    public void setOrigin(float x, float y){
        this.originx = x;
        this.originy = y;
        sprite.setOrigin(x, y);
    }

    public void setRotation(float rotation){
        sprite.setRotation(rotation);
    }

    public void loadData(){
        if(renderComponent.equals("Texture") && pixmapuse){
            Pixmap pixmap = new Pixmap(Gdx.files.internal("trail.png"));
            for(int x = 0; x < pixmap.getWidth(); x++){
                for(int y = 0; y < pixmap.getHeight(); y++){
                    int color = pixmap.getPixel(x, y);
                    Color c = new Color(color);

                    c.r = Math.min(c.r*1.5f, 1.0f);
                    c.g = Math.min(c.g*1.5f, 1.0f);
                    c.b = Math.min(c.b*1.5f, 1.0f);
                    pixmap.drawPixel(x, y, c.toIntBits());
                }
            }
            texture = new Texture(pixmap);
        } else if(renderComponent.equals("Texture")){
            texture = new Texture(path);
        }
        else if(renderComponent.equals("Sprite")) {
            texture = new Texture(path);
            sprite = new Sprite(texture, regionx, regiony, regionwidth, regionheight);
            sprite.setSize(width, height);
            sprite.setPosition(x, y);
            sprite.setOrigin(originx, originy);
            sprite.setRotation(rotation);
        } else {
            font = new BitmapFont();
            glyphLayout = new GlyphLayout();
            glyphLayout.setText(font, text);
        }
    }

    public void setOriginBasedPosition(float x, float y){
        this.x = x - originx;
        this.y = y - originy;
        sprite.setOriginBasedPosition(x, y);
    }

    public void dispose(){
        if(font != null){
            font.dispose();
        }
        if(texture != null){
            texture.dispose();
        }
    }
}
