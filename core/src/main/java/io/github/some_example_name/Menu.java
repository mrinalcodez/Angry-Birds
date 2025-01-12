package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.*;

public class Menu implements Screen {
    private Stage stage;
    private ImageButton play;
    private ImageButton exit;
    private Sprite background;
    private StretchViewport screenViewport;
    private OrthographicCamera camera;
    private final AngryBirds game;
    private SpriteBatch batch;

    public Menu(AngryBirds game, float worldwidth, float worldheight){
        this.game = game;
        camera = new OrthographicCamera();
        screenViewport = new StretchViewport(worldwidth, worldheight, camera);
        screenViewport.apply();

        //SpriteBatch
        batch = new SpriteBatch();

        //background
        background = new Sprite(new Texture("splash.png"));
        background.setSize(worldwidth, worldheight);

        //stage setup
        stage = new Stage(screenViewport);
        Gdx.input.setInputProcessor(stage);

        //new game
        TextureRegion button_texture = new TextureRegion(new Texture("game_icons.png"), 166, 679, 134, 66);
        TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(button_texture);
        play = new ImageButton(textureRegionDrawable);
        play.setSize(200, 80);
        play.setPosition(worldwidth /2 - play.getWidth()/2, worldheight /2 - play.getHeight()/2);
        play.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                for(Screen screen : Main.loaded_screens){
                    if(screen instanceof LevelManager){
                        Gdx.input.setInputProcessor(((LevelManager) screen).getStage());
                        ((LevelManager) screen).setAccesstoLevels();
                        game.setScreen(screen);
                        return;
                    }
                }
                LevelManager levelManager = new LevelManager(game, worldwidth, worldheight);
                Main.loaded_screens.add(levelManager);
                game.setScreen(levelManager);
            }
        });
        play.addListener(new InputListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                play.clearActions();
                play.getImage().setOrigin(play.getImage().getWidth()/2, play.getImage().getHeight()/2);
                play.getImage().setScale(1.2f, 1.2f);
                play.addAction(Actions.scaleTo(1.2f, 1.2f, 0.5f));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                play.clearActions();
                play.getImage().setOrigin(play.getImage().getWidth()/2, play.getImage().getHeight()/2);
                play.getImage().setScale(1f, 1f);
                play.addAction(Actions.scaleTo(1f, 1f, 0.5f));
            }
        });
        stage.addActor(play);

        // exit button
        TextureRegion button_texture1 = new TextureRegion(new Texture("game_icons.png"), 170, 808, 53, 52);
        TextureRegionDrawable textureRegionDrawable1 = new TextureRegionDrawable(button_texture1);
        exit = new ImageButton(textureRegionDrawable1);
        exit.setSize(200, 80);
        exit.setPosition(worldwidth/2 - exit.getWidth()/2, worldheight /2 - worldheight /4 - exit.getHeight()/2);
        exit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }
        });
        stage.addActor(exit);

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        batch.setProjectionMatrix(camera.combined);
        stage.act(v);
        batch.begin();
        background.draw(batch);
        batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height){
        screenViewport.update(width, height);
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
    public void dispose(){
        stage.dispose();
        batch.dispose();
    }

    public Stage getStage(){
        return this.stage;
    }
}
