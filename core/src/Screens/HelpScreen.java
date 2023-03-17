package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import managers.GameScreenManager;

public class HelpScreen extends BaseScreen {
    private MyGdxGame app;
    private Stage stage;
    private Image helpImage;
    private Skin skin;
    private BitmapFont font;
    private Label exitLabel;
    private Label spaceLabel;
    private Texture[] helpImages;

    private int currentImageIndex = 0;

    private boolean isFirstImage = true;

    public HelpScreen(MyGdxGame app) {
        super(app);
        // Load assets
        app.getGenericAssetsManager().load_help();
        app.getGenericAssetsManager().manager.finishLoading();

        // Images to use for display
        helpImages = new Texture[] {
                app.getGenericAssetsManager().manager.get("images/help-1.png", Texture.class),
                app.getGenericAssetsManager().manager.get("images/help-2.png", Texture.class),
                app.getGenericAssetsManager().manager.get("images/help-3.png", Texture.class)
        };

        // Create a new stage to hold the UI elements
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Create an Image widget with the first image and add it to the stage
        helpImage = new Image(helpImages[0]);
        helpImage.setSize(stage.getWidth(), stage.getHeight()); // set size here
        stage.addActor(helpImage);


        skin = app.getGenericAssetsManager().manager.get("skins/clean-crispy-ui.json", Skin.class);

        // Set font and size
        font = skin.getFont("font");
        font.getData().setScale(2f);

        BitmapFont font = skin.getFont("font");
        exitLabel = new Label("Press R return to main menu", new Label.LabelStyle(font, Color.WHITE));
        spaceLabel = new Label("Press space to continue", new Label.LabelStyle(font, Color.WHITE));
        spaceLabel.setPosition((stage.getWidth() - spaceLabel.getWidth()) / 2, 20);
        exitLabel.setPosition((stage.getWidth() - exitLabel.getWidth()) / 2, 40);
        stage.addActor(spaceLabel);
        stage.addActor(exitLabel);
    }

    @Override
    public void update(float delta) {

    }

    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {

            // Move to the next image
            int nextImageIndex = (currentImageIndex + 1) % helpImages.length;
            currentImageIndex = nextImageIndex;

            // Define the slide in animation
            float startX = -helpImage.getWidth();
            float endX = (stage.getWidth() - helpImage.getWidth()) / 2f;
            float duration = 1f;
            helpImage.setSize(stage.getWidth(), stage.getHeight());
            helpImage.setPosition(startX, (stage.getHeight() - helpImage.getHeight()) / 2f);
            MoveToAction slideIn = new MoveToAction();
            slideIn.setPosition(endX, (stage.getHeight() - helpImage.getHeight()) / 2f);
            slideIn.setDuration(duration);

            // Update the help image and apply the animation
            helpImage.setDrawable(new TextureRegionDrawable(new TextureRegion(helpImages[currentImageIndex])));
            helpImage.clearActions();
            helpImage.addAction(slideIn);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            app.getGameScreenManager().setScreen(GameScreenManager.STATE.MAIN_MENU);
        }

        // Clear the screen and render the stage
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }
    @Override
    public void resize(int width, int height) {

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
        for (Texture texture : helpImages) {
            texture.dispose();
        }
    }
}

