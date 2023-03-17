package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import managers.GameScreenManager;
import utils.Settings;

public class GameOverScreen extends BaseScreen {

    private Viewport viewport;
    private Stage stage;
    private SpriteBatch batch;
    private Sprite background;
    private Label scoreLabel;
    private Label highScoreLabel;
    private Label gameOverLabel;
    private Label playAgainLabel;
    private Table table;
    private BitmapFont font;
    private BitmapFont gameOver_font;
    private int score;
    private int highScore;
    private Skin skin;

    private static Preferences prefs = Gdx.app.getPreferences("Warrior Preferences");

    public GameOverScreen(final MyGdxGame app) {
        super(app);
        this.highScore = Settings.getHighestScore();

        viewport = new ScreenViewport();
        stage = new Stage(viewport);
        batch = new SpriteBatch();

        // Load background image
      //  background = new Sprite(new Texture("gameover_bg.png"));
       // background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Load assets
        app.getGenericAssetsManager().load_gameOver();
        app.getGenericAssetsManager().manager.finishLoading();
        skin = app.getGenericAssetsManager().manager.get("skins/clean-crispy-ui.json", Skin.class);

        // Set font and size
        font = skin.getFont("font");
        font.getData().setScale(2f);


        // Create table to hold labels and buttons
        table = new Table();
        table.center();
        table.setFillParent(true);

        // Create labels
        gameOverLabel = new Label("GAME OVER", new Label.LabelStyle(font, Color.WHITE));
        scoreLabel = new Label("Your Score: " + score, new Label.LabelStyle(font, Color.WHITE));
        highScoreLabel = new Label("High Score: " + highScore, new Label.LabelStyle(font, Color.WHITE));
        playAgainLabel = new Label("Play Again", new Label.LabelStyle(font, Color.WHITE));

        playAgainLabel.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Play sound effect

                // Switch to game screen
                System.out.println("Play Again Clicked");
                app.getGameScreenManager().setScreen(GameScreenManager.STATE.GAME_SCREEN);
                return true;
            }
        });

        // fade-in effect for the labels
        gameOverLabel.getColor().a = 0f;
        scoreLabel.getColor().a = 0f;
        highScoreLabel.getColor().a = 0f;
        playAgainLabel.getColor().a = 0f;

        gameOverLabel.addAction(Actions.fadeIn(3.0f));
        scoreLabel.addAction(Actions.fadeIn(3.0f));
        highScoreLabel.addAction(Actions.fadeIn(3.0f));
        playAgainLabel.addAction(Actions.fadeIn(3.0f));

        // Add labels to table
        table.add(gameOverLabel).colspan(2).padBottom(50f).row();
        table.add(scoreLabel).expandX().padBottom(20f);
        table.add(highScoreLabel).expandX().padBottom(20f).row();
        table.add(playAgainLabel).colspan(2).padTop(20f);

        // Add table to stage
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);

        // Play game over sound effect
        app.getAudioManager().playSound("Sounds/gameOver.mp3");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        // Clear screen and draw background
       // batch.begin();
       // background.draw(batch);
       // batch.end();

        // Draw stage
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void dispose() {
        super.dispose();
       // batch.dispose();
        font.dispose();
        stage.dispose();
    }

    @Override
    public void hide() {
        app.getAudioManager().stopMusic();
    }
}