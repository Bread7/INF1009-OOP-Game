package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;

import managers.InputManager;
import com.badlogic.gdx.Gdx;

import managers.GameScreenManager;
import utils.ScrollingBackground;
import utils.Settings;


public class SettingScreen extends BaseScreen{
    private Stage stage;
    // Settings class
    private Skin skin;
    private Settings settings;
    private InputManager inputManager;
    private Slider musicVolumeSlider;
    private ScrollingBackground background;

    public SettingScreen(final MyGdxGame app) {

        super(app);
        background = new ScrollingBackground();
        stage = new Stage(new ScreenViewport());
        // Load skin file
        skin = app.getGenericAssetsManager().manager.get("skins/clean-crispy-ui.json", Skin.class);

        // Create UI elements
        Label title = new Label("Settings", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label leftLabel = new Label("Move left key:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label rightLabel = new Label("Move right key:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label jumpLabel = new Label("Jump key:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label downLabel = new Label("Down key:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        TextButton backButton = new TextButton("Save Setting", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // go back to main menu
                app.getGameScreenManager().setScreen(GameScreenManager.STATE.MAIN_MENU);
            }
        });

        TextField leftTextField = new TextField(Settings.getMoveLeftKey(), skin);
        leftTextField.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                Settings.setMoveLeftKey(String.valueOf(c));
            }
        });

        TextField rightTextField = new TextField(Settings.getMoveRightKey(), skin);
        rightTextField.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                Settings.setMoveRightKey(String.valueOf(c));
            }
        });

        TextField jumpTextField = new TextField(Settings.getJumpKey(), skin);
        jumpTextField.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                Settings.setJumpKey(String.valueOf(c));
            }
        });

        TextField downTextField = new TextField(Settings.getDownKey(), skin);
        downTextField.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                Settings.setDownKey(String.valueOf(c));
            }
        });

        musicVolumeSlider = new Slider(0, 1, 0.1f, false, skin); // min, max, step size, is vertical, skin
        musicVolumeSlider.setValue(Settings.getMusicVolume()); // set the initial value to the current setting
        musicVolumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Settings.changeMusicVolume(musicVolumeSlider.getValue()); // update the setting when the slider is changed
            }
        });

        Label musicVolumeLabel = new Label("Music Volume:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        // Add UI elements to the stage
        stage.addActor(title);
        stage.addActor(leftLabel);
        stage.addActor(leftTextField);
        stage.addActor(rightLabel);
        stage.addActor(rightTextField);
        stage.addActor(jumpLabel);
        stage.addActor(jumpTextField);
        stage.addActor(downLabel);
        stage.addActor(downTextField);
        stage.addActor(musicVolumeLabel);
        stage.addActor(musicVolumeSlider);
        stage.addActor(backButton);

        // Position UI elements
        float centerX = Gdx.graphics.getWidth() / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f;
        // Position UI elements
        float elementWidth = Gdx.graphics.getWidth() * 0.6f;
        float elementHeight = 50f;
        float spacing = 20f;
        float y = centerY + 300f;

        title.setPosition(centerX - title.getWidth() / 2f, y);

        y -= elementHeight + spacing;
        leftLabel.setPosition(centerX - elementWidth / 2f, y);
        leftTextField.setSize(elementWidth, elementHeight);
        leftTextField.setPosition(centerX - elementWidth / 2f, y - leftTextField.getHeight());

        y -= elementHeight + spacing;
        rightLabel.setPosition(centerX - elementWidth / 2f, y);
        rightTextField.setSize(elementWidth, elementHeight);
        rightTextField.setPosition(centerX - elementWidth / 2f, y - rightTextField.getHeight());

        y -= elementHeight + spacing;
        jumpLabel.setPosition(centerX - elementWidth / 2f, y);
        jumpTextField.setSize(elementWidth, elementHeight);
        jumpTextField.setPosition(centerX - elementWidth / 2f, y - jumpTextField.getHeight());

        y -= elementHeight + spacing;
        downLabel.setPosition(centerX - elementWidth / 2f, y);
        downTextField.setSize(elementWidth, elementHeight);
        downTextField.setPosition(centerX - elementWidth / 2f, y - downTextField.getHeight());


        y -= elementHeight + spacing;
        musicVolumeLabel.setPosition(centerX - elementWidth / 2f, y);
        musicVolumeSlider.setSize(elementWidth, elementHeight);
        musicVolumeSlider.setPosition(centerX - elementWidth / 2f, y - musicVolumeSlider.getHeight());


        y -= elementHeight + spacing;
        backButton.setSize(elementWidth, elementHeight);
        backButton.setPosition(centerX - backButton.getWidth() / 2f, y - backButton.getHeight());


    }
    @Override
    public void update(float delta) {

    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        app.getBatch().begin();
        background.updateAndRender(delta, app.getBatch());
        app.getBatch().end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
    }
}