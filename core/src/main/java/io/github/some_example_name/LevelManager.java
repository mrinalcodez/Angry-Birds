package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.awt.*;
import java.util.ArrayList;
import java.util.prefs.PreferenceChangeEvent;

public class LevelManager implements Screen {
    private Sprite background;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private AngryBirds game;
    private StretchViewport screenViewport;
    private Stage stage;
    private Json json = new Json();
    private Preferences preferences = Gdx.app.getPreferences("level");
    private float worldwidth, worldheight;
    private ArrayList<ArrayList<Integer>> levels = new ArrayList<>();

    public LevelManager(AngryBirds game, float worldwidth, float worldheight){
        //basic setup
        this.game = game;
        camera = new OrthographicCamera();
        screenViewport = new StretchViewport(worldwidth, worldheight, camera);
        screenViewport.apply();
        this.batch = new SpriteBatch();
        this.worldwidth = worldwidth;
        this.worldheight = worldheight;

        //background setup
        background = new Sprite(new Texture("splash.png"));
        background.setSize(worldwidth, worldheight);


        //stage setup
        stage = new Stage(screenViewport);
        Gdx.input.setInputProcessor(stage);


        //Levels button setup
        Texture texture1 = new Texture("level1.png");
        TextureRegionDrawable level1_drawable = new TextureRegionDrawable(texture1);
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = level1_drawable;
        ImageButton level1 = new ImageButton(level1_drawable);
        level1.setSize(worldwidth /11, (4 * worldwidth / 11) /3);
        level1.setPosition(level1.getWidth()/2, (level1.getWidth()/2)*10);
        level1.setOrigin(level1.getImage().getWidth()/2, level1.getImage().getHeight()/2);
//        Texture texture1 = new Texture("level1.png");
//        TextureRegionDrawable level1_drawable = new TextureRegionDrawable(texture1);
//        ImageButton level1 = new ImageButton(level1_drawable);
//        level1.setSize(worldwidth /11, (4 * worldwidth / 11) /3);
//        level1.setPosition(level1.getWidth()/2, (level1.getWidth()/2)*10);
//        level1.getImage().setOrigin(level1.getImage().getWidth()/2, level1.getImage().getHeight()/2);

        level1.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                Screen level = new Level(game, worldwidth, worldheight, 1);
                Main.loaded_screens.add(level);
                game.setScreen(level);
            }
        });
        level1.addListener(new InputListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                level1.clearActions();
                level1.getImage().setOrigin(level1.getWidth()/2, level1.getHeight()/2);
                level1.getImage().setScale(1.2f, 1.2f);
                level1.addAction(Actions.scaleTo(1.2f, 1.2f, 0.5f));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                level1.clearActions();
                level1.getImage().setOrigin(level1.getWidth()/2, level1.getHeight()/2);
                level1.getImage().setScale(1f, 1f);
                level1.addAction(Actions.scaleTo(1f, 1f, 0.5f));
            }
        });
        stage.addActor(level1);


        Texture texture2 = new Texture("level2.png");
        TextureRegionDrawable level2_drawable = new TextureRegionDrawable(texture2);
        ImageButton level2 = new ImageButton(level2_drawable);
        level2.setSize(level1.getWidth(), level1.getHeight());
        level2.setPosition(level1.getWidth() + 2*level1.getX(), level1.getY());
        level2.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                Screen level = new Level(game, worldwidth, worldheight, 2);
                Main.loaded_screens.add(level);
                game.setScreen(level);
            }
        });
        level2.addListener(new InputListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                level2.clearActions();
                level2.getImage().setOrigin(level2.getImage().getWidth()/2, level2.getImage().getHeight()/2);
                level2.getImage().setScale(1.2f, 1.2f);
                level2.addAction(Actions.scaleTo(1.2f, 1.2f, 0.5f));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                level2.clearActions();
                level2.getImage().setOrigin(level2.getImage().getWidth()/2, level2.getImage().getHeight()/2);
                level2.getImage().setScale(1f, 1f);
                level2.addAction(Actions.scaleTo(1f, 1f, 0.5f));
            }
        });
        stage.addActor(level2);


        Texture texture3 = new Texture("level3.png");
        TextureRegionDrawable level3_drawable = new TextureRegionDrawable(texture3);
        ImageButton level3 = new ImageButton(level3_drawable);
        level3.setSize(level1.getWidth(), level1.getHeight());
        level3.setPosition(2*level1.getWidth() + 3*level1.getX(), level1.getY());
        level3.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                Screen level = new Level(game, worldwidth, worldheight, 3);
                Main.loaded_screens.add(level);
                game.setScreen(level);
            }
        });
        level3.addListener(new InputListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                level3.clearActions();
                level3.getImage().setOrigin(level3.getImage().getWidth()/2, level3.getImage().getHeight()/2);
                level3.getImage().setScale(1.2f, 1.2f);
                level3.addAction(Actions.scaleTo(1.2f, 1.2f, 0.5f));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                level3.clearActions();
                level3.getImage().setOrigin(level3.getImage().getWidth()/2, level3.getImage().getHeight()/2);
                level3.getImage().setScale(1f, 1f);
                level3.addAction(Actions.scaleTo(1f, 1f, 0.5f));
            }
        });
        stage.addActor(level3);

        TextureRegion back_texture = new TextureRegion(new Texture("game_icons.png"), 207, 124, 87, 74);
        TextureRegionDrawable back_drawable = new TextureRegionDrawable(back_texture);
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        buttonStyle.up = back_drawable;
        ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle(buttonStyle);
        ImageButton back_button = new ImageButton(imageButtonStyle);
        back_button.setSize(40, 40);
        back_button.setPosition(0, 0);
        back_button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.input.setInputProcessor(((Menu) Main.loaded_screens.get(0)).getStage());
                game.setScreen(Main.loaded_screens.get(0));
            }
        });
        stage.addActor(back_button);

        setAccesstoLevels();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        background.draw(batch);
        batch.end();
        stage.act(v);
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {
        screenViewport.update(i, i1);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
    }

    Stage getStage(){
        return stage;
    }

    public void setAccesstoLevels(){
        for(int i = 0; i < stage.getActors().size; i++){
            stage.getActors().get(i).setTouchable(Touchable.disabled);
        }
        stage.getActors().get(3).setTouchable(Touchable.enabled);
        String jsonData = preferences.getString("LevelData", "{}");
        Level.NestedList list = json.fromJson(Level.NestedList.class, jsonData);
        for(int i = 0; i < list.getListData().size(); i++){
            if(i == 3){
                break;
            }
            stage.getActors().get(i).setTouchable(Touchable.enabled);
            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.imageUp = new TextureRegionDrawable(new Texture("level"+(i+1)+list.getListData().get(i).get(2)+".png"));
            ((ImageButton) stage.getActors().get(i)).setStyle(style);
        }
        if(list.getListData().size() < 3){
            stage.getActors().get(list.getListData().size()).setTouchable(Touchable.enabled);
        }
    }
}
