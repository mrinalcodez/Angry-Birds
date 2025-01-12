package io.github.some_example_name;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Json;

import java.io.*;
import java.util.ArrayList;

public class Level extends InputAdapter implements Screen, Serializable {
    private AngryBirds game;
    private SerializableRenderer serializableRenderer;
    private SerializableRenderComponent background;
    private transient Box2DDebugRenderer debugRenderer;
    private SerializableWorld world;
    public boolean movingRight = true;
    public boolean positioning = true;
    public float progression = 0;
    public float elapsedTime = 0f;
    public boolean movingLeft = false;
    public boolean left_positioning = true;
    public float worldwidth, worldheight;
    private SerializableInputMultiplexer multiplexer;
    private Structure structure;
    private SerializableRenderComponent font;
    public static boolean isGameWon;
    public static boolean isGameLost;
    public static boolean isPlaying = true;
    public int level;
    private Pause_Button pause_button;
    private Play_Button play_button;
    private Retry_Button retry_button;
    private LevelBack_Button levelBack_button;
    private Next_Level_Button nextLevelButton;
    private RestoreButton restoreButton;
    private SaveButton saveButton;
    public int current_score = 0;
    private transient Json json = new Json();
    public boolean isSaved = false;
    public int star_count = 0;
    private transient Preferences save_game = Gdx.app.getPreferences("level");
    private ArrayList<SerializableRenderComponent> stars = new ArrayList<>();
    public int count = 0;
    public float time_elapsed = 0;
    public boolean isPaused = false;
    private final SerializableRenderComponent level_cleared_sprite = new SerializableRenderComponent("level cleared.png", 0, 0, 440, 65);
    private final SerializableRenderComponent level_failed_sprite = new SerializableRenderComponent("level failed.png", 0, 0, 380, 65);
    private boolean gameLost;
    private boolean gameWon;
    private boolean playing;


    public Level(AngryBirds game, float worldwidth, float worldheight, int level) {
        //Basic setup
        this.worldheight = worldheight;
        this.worldwidth = worldwidth;
        this.game = game;
        Level.isPlaying = true;
        Level.isGameWon = false;
        Level.isGameLost = false;
        serializableRenderer = new SerializableRenderer(worldwidth/2, worldheight);
//        camera = new OrthographicCamera(worldwidth/2, worldheight);
//        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
//        this.batch = new SpriteBatch();
        debugRenderer = new Box2DDebugRenderer();

        //Pause button, play button, retry button, back button
//        multiplexer = new InputMultiplexer();
        multiplexer = new SerializableInputMultiplexer();

        //world setup
//        world = new World(new Vector2(0, -9.8f), true);
        world = new SerializableWorld();
        this.level = level;
        structure = new Structure(this, world, worldwidth, worldheight, serializableRenderer, multiplexer);

//        //Objects creation
//        Body ground = createGround(worldwidth, worldheight);

        //background setup
//        background = new Sprite(new Texture("background.png"));
//        background.setSize(worldwidth, worldheight - structure.getGroundheight());
//        background.setPosition(0, structure.getGroundheight());
        background = new SerializableRenderComponent("background.png", 0, 0, 1280, 560);
        background.setSize(worldwidth, worldheight - structure.getGroundheight());
        background.setPosition(0, structure.getGroundheight());


        //setup_pause_button();
//        setup_play_button();
//        setup_retry_button();
//        setup_levelback_button();
        pause_button = new Pause_Button("game_icons2.png", 128, 285, 30, 30, 25, serializableRenderer, multiplexer);
        pause_button.setFunctionality(this.game, this, serializableRenderer);
        pause_button.setVisibility(true);
        play_button = new Play_Button("game_icons2.png", 53, 137, 31, 31, 50, serializableRenderer, multiplexer);
        play_button.setFunctionality(this.game, this, serializableRenderer);
        play_button.setVisibility(false);
        retry_button = new Retry_Button("game_icons.png", 185, 331, 76, 76, 50, serializableRenderer, multiplexer);
        retry_button.setFunctionality(this.game, this, serializableRenderer);
        retry_button.setVisibility(false);
        levelBack_button = new LevelBack_Button("game_icons.png", 274, 331, 77, 77, 50, serializableRenderer, multiplexer);
        levelBack_button.setFunctionality(this.game, this, serializableRenderer);
        levelBack_button.setVisibility(false);
        nextLevelButton = new Next_Level_Button("game_icons.png", 5, 427, 75, 74, 50, serializableRenderer, multiplexer);
        nextLevelButton.setFunctionality(this.game, this, serializableRenderer);
        nextLevelButton.setVisibility(false);
        restoreButton = new RestoreButton("game_icons.png", 96, 331, 77, 76, 25, serializableRenderer, multiplexer);
        restoreButton.setFunctionality(this.game, this, serializableRenderer);
        restoreButton.setVisibility(false);
        saveButton = new SaveButton("game_icons2.png", 128, 172, 29, 29, 25, serializableRenderer, multiplexer);
        saveButton.setFunctionality(this.game, this, serializableRenderer);
        saveButton.setVisibility(true);


//        Gdx.input.setInputProcessor(multiplexer);
        multiplexer.setConnection();

        //Highscore font
//        layout = new GlyphLayout();
//        font = new BitmapFont();
        font = new SerializableRenderComponent();
        String jsonData = save_game.getString("LevelData", "{}");
        NestedList levelData = json.fromJson(NestedList.class, jsonData);
        current_score = levelData.getScore();

        //Stars
//        Sprite star1 = new Sprite(new Texture("star1.png"), 23, 38, 166, 163);
//        Sprite star2 = new Sprite(new Texture("star2.png"), 182, 8, 180, 178);
//        Sprite star3 = new Sprite(new Texture("star3.png"), 354, 38, 167, 163);
//        star1.setSize(100, 100);
//        star2.setSize(100, 100);
//        star3.setSize(100, 100);
//        stars.add(star1);
//        stars.add(star2);
//        stars.add(star3);
//        level_cleared_sprite.setSize(100, 30);
//        level_failed_sprite.setSize(100, 30);
        SerializableRenderComponent star1 = new SerializableRenderComponent("star1.png", 23, 38, 166, 163);
        SerializableRenderComponent star2 = new SerializableRenderComponent("star2.png", 182, 8, 180, 178);
        SerializableRenderComponent star3 = new SerializableRenderComponent("star3.png", 354, 38, 167, 163);
        star1.setSize(100, 100);
        star2.setSize(100, 100);
        star3.setSize(100, 100);
        stars.add(star1);
        stars.add(star2);
        stars.add(star3);
        level_cleared_sprite.setSize(100, 30);
        level_failed_sprite.setSize(100, 30);
    }


//    public Body createGround(float worldwidth, float worldheight){
//        BodyDef bodyDef = new BodyDef();
//        bodyDef.position.set(worldwidth/2, 2*17.25f);
//        bodyDef.type = BodyDef.BodyType.StaticBody;
//        Body groundbody = world.createBody(bodyDef);
//        PolygonShape polygonShape = new PolygonShape();
//        polygonShape.setAsBox(worldwidth/2, 2*17.25f);
//        groundwidth = 4*17.25f;
//        groundbody.createFixture(polygonShape, 0.0f);
//        groundsprite = new Sprite(new Texture("ground.png"));
//        groundsprite.setSize(worldwidth, 4*17.25f + 35*(4*17.25f)/ groundsprite.getRegionHeight());
//        polygonShape.dispose();
//        groundbody.setUserData("ground");
//        return groundbody;
//    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
//        camera.update();
//        batch.setProjectionMatrix(camera.combined);
        serializableRenderer.start();

//        batch.begin();
//        background.draw(batch);
        background.draw(serializableRenderer, background.x, background.y, background.width, background.height, background.rotation);
        //groundsprite.draw(batch);
//        layout.setText(font, "HIGH SCORE: " + current_score);
//        font.draw(batch, layout, worldwidth - layout.width, worldheight - layout.height);
        font.setText("HIGH SCORE: " + current_score);
        font.draw(serializableRenderer, worldwidth - font.width, worldheight - font.height);
        structure.render(v);
        if(!isGameWon && !isGameLost) {
            if (!isPaused) {
                background.setColor(1, 1, 1, 1);
//                groundsprite.setColor(1, 1, 1, 1);
                font.setColor(1, 1, 1, 1);
                isPlaying = true;
//                set_pause_button(camera.position.x - camera.viewportWidth/2, camera.position.y + camera.viewportHeight/2);
//                pause_sprite.draw(batch);
                pause_button.setVisibility(true);
                retry_button.setVisibility(false);
                levelBack_button.setVisibility(false);
                play_button.setVisibility(false);
                restoreButton.setVisibility(false);
                saveButton.setVisibility(true);
                pause_button.setPosition(serializableRenderer.positionx - serializableRenderer.viewportWidth/2, serializableRenderer.positiony + serializableRenderer.viewportHeight/2, serializableRenderer);
                saveButton.setPosition(serializableRenderer.positionx - serializableRenderer.viewportWidth/2, serializableRenderer.positiony - serializableRenderer.viewportHeight/2);
                pause_button.draw(serializableRenderer);
                saveButton.draw(serializableRenderer);
                world.step(v, 6, 2);
                float spriteRightEdge = background.x + background.width;
                float spriteLeftEdge = 0;
                if (movingRight) {
                    if (serializableRenderer.camera.position.x + serializableRenderer.camera.viewportWidth / 2 < spriteRightEdge && positioning) {
                        serializableRenderer.translate(progression, 0f, 0f);
                        progression += 0.05f;
                    } else {
                        movingRight = false;
                        elapsedTime = 0f;
                    }
                } else if (!movingLeft) {
                    elapsedTime += 0.05f;
                    if (elapsedTime >= 2f) {
                        movingLeft = true;
                        progression = 0f;
                    }
                } else {
                    if (serializableRenderer.camera.position.x - serializableRenderer.camera.viewportWidth / 2 > spriteLeftEdge && left_positioning) {
                        serializableRenderer.translate(-progression, 0f, 0f);
                        progression += 0.05f;
                    } else {
                        left_positioning = false;
//                        camera.viewportWidth = worldwidth;
//                        camera.viewportHeight = worldheight;
                        serializableRenderer.setSize(worldwidth, worldheight);
//                        pause_sprite.setPosition((camera.viewportWidth - 50*(camera.viewportWidth/camera.viewportHeight)), (camera.viewportHeight - 50*(camera.viewportWidth/camera.viewportHeight)));
//                        this.pause.setLocation((int) (camera.viewportWidth - pause_sprite.getWidth()), (int) (camera.viewportHeight - pause_sprite.getHeight()));
                        //set_pause_button(camera.position.x - camera.viewportWidth/2, camera.position.y + camera.viewportHeight/2);
//                        pause_button.setPosition(camera.position.x - camera.viewportWidth/2, camera.position.y + camera.viewportHeight/2, camera);
//                        camera.position.set(worldwidth / 2, worldheight / 2, 0f);
                        pause_button.setPosition(serializableRenderer.positionx - serializableRenderer.viewportWidth/2, serializableRenderer.positiony + serializableRenderer.viewportHeight/2, serializableRenderer);
                    }
                }
            } else {
                isPlaying = false;
                background.setColor(0.5f, 0.5f, 0.5f, 1);
//                groundsprite.setColor(0.5f, 0.5f, 0.5f, 1);
                font.setColor(0.5f, 0.5f, 0.5f, 1);
                Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
                pixmap.setColor(Color.BLACK);
                pixmap.fill();

                Texture texture = new Texture(pixmap);
                pixmap.dispose();
                Sprite sprite = new Sprite(texture);
//                sprite.setSize(camera.viewportWidth / 4, camera.viewportHeight);
//                sprite.setPosition(camera.position.x - camera.viewportWidth / 2, 0);
//                sprite.draw(batch);
                sprite.setSize(serializableRenderer.viewportWidth/4, serializableRenderer.viewportHeight);
                sprite.setPosition(serializableRenderer.positionx - serializableRenderer.viewportWidth/2, 0);
                sprite.draw(serializableRenderer.spriteBatch);
                pause_button.setVisibility(false);
                play_button.setVisibility(true);
                retry_button.setVisibility(true);
                levelBack_button.setVisibility(true);
                restoreButton.setVisibility(true);
                saveButton.setVisibility(false);
//                play_button.setPosition(camera.position.x - camera.viewportWidth/2 + camera.viewportWidth/8, camera.position.y + camera.viewportHeight/4, camera);
//                retry_button.setPosition(camera.position.x - camera.viewportWidth/2 + camera.viewportWidth/8, camera.position.y, camera);
//                levelBack_button.setPosition(camera.position.x - camera.viewportWidth/2 + camera.viewportWidth/8, camera.position.y - camera.viewportHeight/4, camera);
//                restoreButton.setPosition(camera.position.x - camera.viewportWidth/2, camera.position.y - camera.viewportHeight/2, camera);
                play_button.setPosition(serializableRenderer.positionx - serializableRenderer.viewportWidth/2 + serializableRenderer.viewportWidth/8, serializableRenderer.positiony + serializableRenderer.viewportHeight/4, serializableRenderer);
                retry_button.setPosition(serializableRenderer.positionx - serializableRenderer.viewportWidth/2 + serializableRenderer.viewportWidth/8, serializableRenderer.positiony, serializableRenderer);
                levelBack_button.setPosition(serializableRenderer.positionx - serializableRenderer.viewportWidth/2 + serializableRenderer.viewportWidth/8, serializableRenderer.positiony - serializableRenderer.viewportHeight/4, serializableRenderer);
                restoreButton.setPosition(serializableRenderer.positionx - serializableRenderer.viewportWidth/2, serializableRenderer.positiony - serializableRenderer.viewportHeight/2, serializableRenderer);
//                set_play_button(camera.position.x - camera.viewportWidth / 2 + camera.viewportWidth / 8, camera.position.y + camera.viewportHeight / 4);
//                set_retry_button(camera.position.x - camera.viewportWidth / 2 + camera.viewportWidth / 8, camera.position.y);
//                set_levelback_button(camera.position.x - camera.viewportWidth / 2 + camera.viewportWidth / 8, camera.position.y - camera.viewportHeight / 4);
//                play_sprite.draw(batch);
//                retry_sprite.draw(batch);
//                level_back_sprite.draw(batch);
                play_button.draw(serializableRenderer);
                retry_button.draw(serializableRenderer);
                levelBack_button.draw(serializableRenderer);
                restoreButton.draw(serializableRenderer);
            }

            serializableRenderer.end();
            //debugRenderer.render(world.world, serializableRenderer.camera.combined);
        } else{
            if(isGameWon){
                isPlaying = false;
                background.setColor(0.5f, 0.5f, 0.5f, 1);
                //groundsprite.setColor(0.5f, 0.5f, 0.5f, 1);
                font.setColor(0.5f, 0.5f, 0.5f, 1);
                Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
                pixmap.setColor(Color.BLACK);
                pixmap.fill();

                Texture texture = new Texture(pixmap);
                pixmap.dispose();
                Sprite sprite = new Sprite(texture);
                sprite.setSize(serializableRenderer.viewportWidth / 2, serializableRenderer.viewportHeight);
                sprite.setPosition(serializableRenderer.positionx - sprite.getWidth()/2, 0);
                sprite.draw(serializableRenderer.spriteBatch);
                pause_button.setVisibility(false);
                play_button.setVisibility(false);
                retry_button.setVisibility(true);
                levelBack_button.setVisibility(true);
                saveButton.setVisibility(false);
                retry_button.setPosition(serializableRenderer.positionx - serializableRenderer.viewportWidth/8, serializableRenderer.positiony - serializableRenderer.viewportHeight/4, serializableRenderer);
                levelBack_button.setPosition(serializableRenderer.positionx, serializableRenderer.positiony - serializableRenderer.viewportHeight/4, serializableRenderer);
                if(level < AngryBirds.MAX_LEVELS){
                    nextLevelButton.setVisibility(true);
                    nextLevelButton.setPosition(serializableRenderer.positionx + serializableRenderer.viewportWidth/8, serializableRenderer.positiony - serializableRenderer.viewportHeight/4, serializableRenderer);
                    nextLevelButton.draw(serializableRenderer);
                }
//                set_play_button(camera.position.x - camera.viewportWidth / 2 + camera.viewportWidth / 8, camera.position.y + camera.viewportHeight / 4);
//                set_retry_button(camera.position.x - camera.viewportWidth / 2 + camera.viewportWidth / 8, camera.position.y);
//                set_levelback_button(camera.position.x - camera.viewportWidth / 2 + camera.viewportWidth / 8, camera.position.y - camera.viewportHeight / 4);
//                play_sprite.draw(batch);
//                retry_sprite.draw(batch);
//                level_back_sprite.draw(batch);
                retry_button.draw(serializableRenderer);
                levelBack_button.draw(serializableRenderer);
                stars.get(0).setPosition(serializableRenderer.positionx - 100 - stars.get(0).width/2, serializableRenderer.positiony + 75 - stars.get(0).height/2);
                stars.get(1).setPosition(serializableRenderer.positionx - stars.get(1).width/2, serializableRenderer.positiony + 75 + 50- stars.get(1).height/2);
                stars.get(2).setPosition(serializableRenderer.positionx + 100 - stars.get(2).width/2, serializableRenderer.positiony + 75 - stars.get(2).height/2);
                if(!isSaved){
                    ArrayList<Integer> list = new ArrayList<>();
                    list.add(level);
                    list.add(current_score);
                    list.add(star_count);
                    String jsonData = save_game.getString("LevelData", "{}");
                    NestedList levelData = json.fromJson(NestedList.class, jsonData);
                    if(level < levelData.getListData().size()){
                        levelData.setListData(level-1, list);
                        levelData.setScore(current_score);
                    } else {
                        levelData.addData(list);
                        levelData.setScore(current_score);
                    }
                    String addjsonData = json.toJson(levelData);
                    save_game.putString("LevelData", addjsonData);
                    save_game.flush();
                    isSaved = true;
                }
                if(count < star_count){
                    if(time_elapsed > 5){
                        count++;
                        time_elapsed = 0;
                    }
                    time_elapsed += v;
                }
                for(int i = 0; i < count; i++){
                    stars.get(i).draw(serializableRenderer, stars.get(i).x, stars.get(i).y, stars.get(i).width, stars.get(i).height, stars.get(i).rotation);
                }
                level_cleared_sprite.setPosition(serializableRenderer.positionx - level_cleared_sprite.width/2, serializableRenderer.positiony + ((float) 8 /9)*serializableRenderer.viewportHeight/2 - level_cleared_sprite.height/2);
                level_cleared_sprite.draw(serializableRenderer, level_cleared_sprite.x, level_cleared_sprite.y, level_cleared_sprite.width, level_cleared_sprite.height, level_cleared_sprite.rotation);
            } else if(isGameLost){
                isPlaying = false;
                background.setColor(0.5f, 0.5f, 0.5f, 1);
                //groundsprite.setColor(0.5f, 0.5f, 0.5f, 1);
                font.setColor(0.5f, 0.5f, 0.5f, 1);
                Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
                pixmap.setColor(Color.BLACK);
                pixmap.fill();

                Texture texture = new Texture(pixmap);
                pixmap.dispose();
                Sprite sprite = new Sprite(texture);
                sprite.setSize(serializableRenderer.viewportWidth / 2, serializableRenderer.viewportHeight);
                sprite.setPosition(serializableRenderer.positionx - sprite.getWidth()/2, 0);
                sprite.draw(serializableRenderer.spriteBatch);
                pause_button.setVisibility(false);
                play_button.setVisibility(false);
                retry_button.setVisibility(true);
                levelBack_button.setVisibility(true);
                saveButton.setVisibility(false);
                retry_button.setPosition(serializableRenderer.positionx - serializableRenderer.viewportWidth/8, serializableRenderer.positiony - serializableRenderer.viewportHeight/4, serializableRenderer);
                levelBack_button.setPosition(serializableRenderer.positionx + serializableRenderer.viewportWidth/8, serializableRenderer.positiony - serializableRenderer.viewportHeight/4, serializableRenderer);
//                set_play_button(camera.position.x - camera.viewportWidth / 2 + camera.viewportWidth / 8, camera.position.y + camera.viewportHeight / 4);
//                set_retry_button(camera.position.x - camera.viewportWidth / 2 + camera.viewportWidth / 8, camera.position.y);
//                set_levelback_button(camera.position.x - camera.viewportWidth / 2 + camera.viewportWidth / 8, camera.position.y - camera.viewportHeight / 4);
//                play_sprite.draw(batch);
//                retry_sprite.draw(batch);
//                level_back_sprite.draw(batch);
                retry_button.draw(serializableRenderer);
                levelBack_button.draw(serializableRenderer);
                level_failed_sprite.setPosition(serializableRenderer.positionx - level_failed_sprite.width/2, serializableRenderer.positiony + ((float) 8 /9)*serializableRenderer.viewportHeight/2 - level_failed_sprite.height/2);
                level_failed_sprite.draw(serializableRenderer, level_failed_sprite.x, level_failed_sprite.y, level_failed_sprite.width, level_failed_sprite.height, level_failed_sprite.rotation);
            }
            serializableRenderer.end();
        }
    }

    @Override
    public void resize(int i, int i1) {

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
        serializableRenderer.dispose();
        font.dispose();
        world.dispose();
    }

//    private void setup_pause_button(){
//        //Pause Button
//        TextureRegion pause_texture_region = new TextureRegion(new Texture("game_icons2.png"), 128, 285, 30, 30);
//        pause_sprite = new Sprite(pause_texture_region);
//        pause_sprite.setSize(25*(camera.viewportWidth/camera.viewportHeight), 25);
//        this.pause = new Rectangle(0, (int) (camera.viewportHeight - pause_sprite.getHeight()), (int) pause_sprite.getWidth(), (int) pause_sprite.getHeight());
//        multiplexer.addProcessor(new InputAdapter() {
//            @Override
//            public boolean touchDown(int screenX, int screenY, int pointer, int button){
//                camera.update();
//                Vector3 worldcoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
//
//                if(pause.contains(worldcoordinates.x, worldcoordinates.y)){
//                    isPaused = true;
//                    return true;
//                }
//                return false;
//            }
//        });
//    }
//
//    private void setup_play_button(){
//        play_sprite = new Sprite(new Texture("game_icons2.png"), 53, 137, 31, 31);
//        play_sprite.setSize(50*(camera.viewportWidth/camera.viewportHeight), 50);
//        play = new Rectangle((int) (camera.viewportWidth/8 - play_sprite.getWidth()/2), (int) (camera.viewportHeight*0.75f - play_sprite.getHeight()/2), (int) play_sprite.getWidth(), (int) play_sprite.getHeight());
//        multiplexer.addProcessor(new InputAdapter(){
//            @Override
//            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//                Vector3 worldcoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
//
//                if(play.contains(worldcoordinates.x, worldcoordinates.y)){
//                    isPaused = false;
//                    //groundsprite.setColor(1, 1, 1, 1);
//                    background.setColor(1, 1, 1, 1);
//                    font.setColor(1, 1, 1, 1);
//                    return true;
//                }
//                return false;
//            }
//        });
//    }
//
//    private void set_play_button(float x, float y){
//        this.play_sprite.setSize(50*(camera.viewportWidth/camera.viewportHeight), 50);
//        this.play_sprite.setPosition(x - play_sprite.getWidth()/2, y - play_sprite.getHeight()/2);
//        this.play.setSize((int) play_sprite.getWidth(), (int) play_sprite.getHeight());
//        this.play.setLocation((int) play_sprite.getX(), (int) play_sprite.getY());
//    }
//
//    private void set_pause_button(float x, float y){
//        this.pause_sprite.setSize(25*(camera.viewportWidth/camera.viewportHeight), 25);
//        this.pause_sprite.setPosition(x, y - this.pause_sprite.getHeight());
//        this.pause.setSize((int) pause_sprite.getWidth(), (int) pause_sprite.getHeight());
//        this.pause.setLocation((int) this.pause_sprite.getX(), (int) this.pause_sprite.getY());
//    }
//
//    private void setup_retry_button() {
//        retry_sprite = new Sprite(new Texture("game_icons.png"), 185, 331, 76, 76);
//        retry_sprite.setSize(50*(camera.viewportWidth/camera.viewportHeight), 50);
//        this.retry = new Rectangle((int) (camera.viewportWidth/8 - retry_sprite.getWidth()/2), (int) (camera.viewportHeight/2 - retry_sprite.getHeight()/2), (int) retry_sprite.getWidth(), (int) retry_sprite.getHeight());
//        multiplexer.addProcessor(new InputAdapter(){
//            @Override
//            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//                Vector3 worldcoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
//
//                if(retry.contains(worldcoordinates.x, worldcoordinates.y)){
//                    Level new_level = null;
//                    new_level = new Level(game, worldwidth, worldheight, level);
//                    Level.isPlaying = true;
//                    //Level.isGameOver = false;
//                    game.setScreen(new_level);
//                    return true;
//                }
//                return false;
//            }
//        });
//    }
//
//    private void setup_levelback_button() {
//        this.level_back_sprite = new Sprite(new TextureRegion(new Texture("game_icons.png"), 274, 331, 77, 77));
//        this.level_back_sprite.setSize(50*(camera.viewportWidth/camera.viewportHeight), 50);
//        this.level_back = new Rectangle((int) (camera.viewportWidth/8 - level_back_sprite.getWidth()/2), (int) (camera.viewportHeight/4 - level_back_sprite.getHeight()/2), (int) level_back_sprite.getWidth(), (int) level_back_sprite.getHeight());
//        multiplexer.addProcessor(new InputAdapter(){
//            @Override
//            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//                Vector3 worldcoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
//
//                if(level_back.contains(worldcoordinates.x, worldcoordinates.y)){
//                    Gdx.input.setInputProcessor(((LevelManager) game.loaded_screens.get(1)).getStage());
//                    game.setScreen(game.loaded_screens.get(1));
//                    return true;
//                }
//                return false;
//            }
//        });
//    }
//
//    private void set_retry_button(float x, float y){
//        this.retry_sprite.setSize(50*(camera.viewportWidth/camera.viewportHeight), 50);
//        this.retry_sprite.setPosition(x - retry_sprite.getWidth()/2, y - retry_sprite.getHeight()/2);
//        this.retry.setSize((int) retry_sprite.getWidth(), (int) retry_sprite.getHeight());
//        this.retry.setLocation((int) retry_sprite.getX(), (int) retry_sprite.getY());
//    }
//
//    private void set_levelback_button(float x, float y){
//        this.level_back_sprite.setSize(50*(camera.viewportWidth/camera.viewportHeight), 50);
//        this.level_back_sprite.setPosition(x - level_back_sprite.getWidth()/2, y - level_back_sprite.getHeight()/2);
//        this.level_back.setSize((int) level_back_sprite.getWidth(), (int) level_back_sprite.getHeight());
//        this.level_back.setLocation((int) level_back_sprite.getX(), (int) level_back_sprite.getY());
//    }

    public static class NestedList implements Serializable{
        private final ArrayList<ArrayList<Integer>> list;
        private int score;

        public NestedList(){
            list = new ArrayList<>();
        }

        public ArrayList<ArrayList<Integer>> getListData(){
            return list;
        }

        public void setListData(int index, ArrayList<Integer> element){
            list.set(index, element);
        }

        public void addData(ArrayList<Integer> element){
            list.add(element);
        }

        public int getScore(){
            return score;
        }

        public void setScore(int score){
            this.score = score;
        }
    }

    public void saveLevel(){
        structure.saveData();
        gameWon = isGameWon;
        gameLost = isGameLost;
        playing = isPlaying;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("game_data.ser"))) {
            oos.writeObject(this);
            System.out.println("Game saved!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadLevel(AngryBirds game){
        this.game = game;
        serializableRenderer.loadData();
        background.loadData();
        multiplexer.loadData();
        debugRenderer = new Box2DDebugRenderer();
        multiplexer.loadData();
        font.loadData();
        pause_button.loadData(game, this, serializableRenderer);
        play_button.loadData(game, this, serializableRenderer);
        retry_button.loadData(game, this, serializableRenderer);
        levelBack_button.loadData(game, this, serializableRenderer);
        nextLevelButton.loadData(game, this, serializableRenderer);
        restoreButton.loadData(game, this, serializableRenderer);
        saveButton.loadData(game, this, serializableRenderer);
        level_cleared_sprite.loadData();
        level_failed_sprite.loadData();
        for(SerializableRenderComponent component : stars){
            component.loadData();
        }
        json = new Json();
        save_game = Gdx.app.getPreferences("level");
        structure.loadData();
    }

}
