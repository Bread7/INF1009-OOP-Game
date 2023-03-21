package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import managers.GameScreenManager;

import managers.InputManager;


import utils.ScrollingBackground;


public class MenuScreen extends BaseScreen{
    private Stage stage;
    private Viewport viewport;
    private Table mainTable;
    private Skin skin;
    private Sound click;
    private ScrollingBackground backGround;

    private InputManager inputManager;
    public InputMultiplexer multiplexer;


    public MenuScreen(MyGdxGame app) {
        super(app);
        backGround = new ScrollingBackground();
       // app.getGenericAssetsManager().loadAsset("uiskin.json", Skin.class, new SkinLoader.SkinParameter("uiskin.atlas"));
        app.getGenericAssetsManager().loadAsset("skins/clean-crispy-ui.json",Skin.class);
        app.getGenericAssetsManager().loadAsset("Music/click.mp3", Sound.class);
        app.getGenericAssetsManager().manager.finishLoading();
        //skin = app.getGenericAssetsManager().manager.get("uiskin.json", Skin.class);
        skin = app.getGenericAssetsManager().manager.get("skins/clean-crispy-ui.json", Skin.class);
        click = app.getGenericAssetsManager().manager.get("Music/click.mp3", Sound.class);
        
    }

    public void show()
    {
        // create background music
        //app.audioManager.playMusic("Music/theme_music.ogg");

        viewport = new ScreenViewport();
        stage = new Stage(viewport);

        mainTable = new Table();
        mainTable.setFillParent(true);

        stage.addActor(mainTable);

        Gdx.input.setInputProcessor(stage);

        // add button to table
        addButton("Start Game").addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                app.getAudioManager().playSound("Music/click.mp3");
                app.getGameScreenManager().setScreen(GameScreenManager.STATE.GAME_SCREEN);
                System.out.println("Play has been clicked"); //debugging
            }
        });
        addButton("Settings").addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                app.getAudioManager().playSound("Music/click.mp3");
                app.getGameScreenManager().setScreen(GameScreenManager.STATE.SETTING_SCREEN);
                System.out.println("Settings has been clicked"); //debugging
            }
        });
        addButton("Help").addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                app.getAudioManager().playSound("Music/click.mp3");
                app.getGameScreenManager().setScreen(GameScreenManager.STATE.HELP_SCREEN);
                System.out.println("Help has been clicked"); //debugging
            }
        });
        addButton("Exit Game").addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                app.getAudioManager().playSound("Music/click.mp3");
                System.out.println("Exit has been clicked"); //debugging
                Gdx.app.exit();
            }
        });


        // reference pointers, not new instances, to add at bottom
        // input reference
        // inputManager = app.getInputManager();
        // multiplexer = new InputMultiplexer(stage, inputManager);
        // Gdx.input.setInputProcessor(multiplexer);
    }

    private TextButton addButton(String name){
        TextButton button = new TextButton(name,skin);
        mainTable.add(button).width(700).height(60).padBottom(20);
        mainTable.row();
        return button;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(float delta) {
        app.getBatch().begin();
        backGround.updateAndRender(delta,app.getBatch());
        app.getBatch().end();
       // super.render(delta);
        stage.act(delta);
        stage.draw();

    }

    @Override
    public void hide() {
        //stop the music when the MenuScreen is no longer visble
        app.getAudioManager().stopMusic();
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    public void dispose()
    {
        stage.dispose();
        skin.dispose();
        app.getGenericAssetsManager().dispose();
    }
}
