package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import Behaviours.NPCBehaviour;
import Behaviours.PlayerBehaviour;
import managers.*;
import managers.GameScreenManager.STATE;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import utils.B2DConstants;
import utils.Hud;
import utils.MapBricks;
import utils.QuestionConstants;
import utils.SurfaceAreaBox;
import utils.Settings;
import Entity.Characters.*;
import Entity.Objects.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import java.util.*;


public class GameScreen extends BaseScreen implements MapBricks, SurfaceAreaBox, QuestionConstants {
    //camera
    OrthographicCamera camera;
    //world
    World world;
    Box2DDebugRenderer b2dr;
    Body box;
    Hud hud;
    private boolean paused = false;
    private TextureRegion pauseTexture;
    private Stage stage;
    private Stage pauseStage;
    private Viewport viewport;
    private Table mainTable;

    // for force pausing mechanics
    private float forcePauseDelta;
    private boolean forcedPause = false;

    // reference pointer to managers
    private EntityManager entityManager;
    private InputManager inputManager;
    private BehaviourManager behaviourManager;
    private PlayerBehaviour playerBehaviour;
    private NPCBehaviour npcBehaviour;

    // lists of textures and different entities
    private List<AtlasRegion> bg, playerIdle,playerWalk, playerDeath, playerHurt, bossWalk, bossDeath, food, podiums, bricks, hearts;
    private List<Brick> brickList;
    private List<Podium> podiumList;
    private List<HealthFood> healthFoodList;
    private List <UnhealthyFood> unhealthyFoodList;
    private List<Player> playerList;
    private List<Boss> bossList;
    private Skin skin;

    private float timeSpawnHealthFood, timeSpawnUnhealthyFood, timeElapsed, pauseTimeElapsed;

    private CollisionManager collisionManager;

    // control frames to display
    private boolean playerJustDied = true;
    private boolean bossJustDied = true;
    private boolean playerJustHurt = true;
    private int playerDeathCounter = 0;
    private int bossDeathCounter = 0;

    private int playerWalkFrame = 0;
    private int playerIdleFrame = 0;
    private int playerHurtFrame = 0;
    private int playerDeathFrame = 0;
    private int bossWalkFrame = 0;
    private int bossDeathFrame = 0;

    private float playerHurtDelta, playerDeathDelta, bossDeathDelta;
    private float playerFullDeathDelta = 0;

    // display heart health
    private int activePlayerHealth;
    private float heartX = 200;
    private float heartY = 635;

    // questions mechanics
    private Stage qnsStage;
    private boolean qnsState;
    private boolean ans;
    private Label trueLabel;
    private Label questionLabel;
    private Label titleLabel;
    private Label falseLabel;
    Random random = new Random();
    private int qnsCount = 0;

    public GameScreen(final MyGdxGame app) {
        super(app);
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, app.APP_DESKTOP_WIDTH, app.APP_DESKTOP_HEIGHT);
        viewport = new ScreenViewport();
        stage = new Stage(viewport);
        hud = new Hud(app.batch);
        b2dr = new Box2DDebugRenderer();
        // pauseTexture = new TextureRegion(new Texture("badlogic.jpg"));
    }



    @Override
    public void show()
    {
        app.getAudioManager().playMusic("Music/theme_music.ogg");

        world = new World(new Vector2(0f,0f),false);
        //box = B2DBodyBuilder.createBox(world, camera.viewportWidth / 2, camera.viewportWidth / 2 , 32, 32);
        //app.batch.setProjectionMatrix(camera.combined);
        //app.shape.setProjectionMatrix(camera.combined);
        Initialise();
        pauseStage = new Stage(viewport);
        qnsStage = new Stage(viewport);
        initQuestion();
    }

    @Override
    public void update(float delta) {
        for (int i = 0; i < playerList.size(); i++) {
            float speed = playerList.get(i).getSpeed();
            hud.setPlayerSpeed(speed);
        }
        

        // takes 1 step in the physics simulation ( 60 times per second)
        world.step(1 / 60f, 6, 2);

        // update the counter in the hud
        hud.update(delta);
        stage.act(delta); // update actor

        // input movement for player
        inputManager.keyDown(Keys.ANY_KEY); 
        // while key is being held down
        if (inputManager.getKeyHold()) {            
            inputManager.keyMap(inputManager.getActiveKey());
        }
        // when key has been released
        inputManager.keyUp(Keys.ANY_KEY);
        
        // track hurt and death status
        playerJustDied = playerBehaviour.getPlayerJustDied();
        playerJustHurt = playerBehaviour.getPlayerJustHurt();
        bossJustDied = playerBehaviour.getBossJustDied();

        // delta values for tracking purposes
        timeElapsed += delta;
        pauseTimeElapsed += delta;

        // spawn food behaviours
        npcBehaviour.spawnUnhealthyFood();

        // constant collision checks for player
        collisionManager.fullPlayerCollisionCheck();
        
        // check if player die
        for (int i = 0; i < playerList.size(); i++) {
            activePlayerHealth = playerList.get(i).getHealth();
            if (playerList.get(i).getStatus() == true) {
                // go to question page to gain more health
                inputManager.forceKeyRelease();
                qnsState = true;
                if (qnsState == false) {
                    // show final death scenes
                    if (playerDeathFrame < 4 && playerDeathCounter == 0) {
                        playerJustDied = true;
                        if (playerDeathFrame == 3) {
                            playerDeathCounter += 1;
                        }
                    }
                    
                    // move to game over screen with 0 score
                    if (playerFullDeathDelta >= 6) {
                        int score = 0;
                        Settings.setHighestScore(score);
                        this.app.getGameScreenManager().setScreen(STATE.GAME_OVER);
                    }
                    playerFullDeathDelta += delta;
                }
            }
        }

        for (int i = 0; i < bossList.size(); i++) {
            // when boss is dead
            if (bossList.get(i).getStatus() == true) {
                if (bossDeathFrame < 6 && bossDeathCounter == 0) {
                    bossJustDied = true;
                    if (bossDeathFrame == 5) {
                        bossDeathCounter += 1;
                    }
                }
            } else {
                // move boss if alive
                npcBehaviour.bossMovement();
            }
        }

        // when player wins game
        for (int i = 0; i < podiumList.size(); i++) {
            if (podiumList.get(i).checkPodium()) {
                // go to gameoverscreen;
                int timeLeft = hud.getWorldTime() - Math.round(timeElapsed);
                int score = timeLeft * 100;
                Settings.setHighestScore(score);
                this.app.getGameScreenManager().setScreen(STATE.GAME_OVER);
            }
        }

        // force pause every 5 mins for 1 min of break time
        if (pauseTimeElapsed > 1 && Math.round(pauseTimeElapsed) % 300 == 0) {
            paused = true;
            forcedPause = true;
        }

        // for question state
        if (qnsState && qnsCount == 0) {
            int qnsNum = randomQns();
            switch (qnsNum) {
                case qns1:
                    questionLabel.setText(qns1Text);
                    ans = qns1Ans;
                    break;
                case qns2:
                    questionLabel.setText(qns2Text);
                    ans = qns2Ans;
                    
                    break;
                case qns3:                    
                    questionLabel.setText(qns3Text);
                    ans = qns3Ans;
                    break;
            }
            System.out.print(ans);
            qnsCount += 1;
        }
        
    }
    @Override
    public void render(float delta)
    {
        skin = app.getGenericAssetsManager().manager.get("skins/clean-crispy-ui.json", Skin.class);

        // mainTable = new Table();
        // mainTable.setFillParent(true);
        if (qnsState) {
            super.render(delta);
            qnsStage.act(delta);
            Gdx.input.setInputProcessor(qnsStage);

            trueLabel.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // System.out.print("Click");
                    boolean clickVal = true;
                    if (clickVal == ans) {
                        // user answer correctly
                        for (int i = 0; i < playerList.size(); i++) {
                            // revive player and give 1 health back
                            if (playerList.get(i).getStatus() == true) {
                                
                                playerList.get(i).setHealth(1);
                                activePlayerHealth = 1;
                                qnsCount = 0;
                                qnsState = false;
                                playerList.get(i).setStatus();
                                
                                
                            }
                        }
                        
                    } else {
                        // game over / lose
                        int score = 0;
                        Settings.setHighestScore(score);
                        qnsCount = 0;
                        qnsState = false;
                        app.getGameScreenManager().setScreen(STATE.GAME_OVER);
                        
                    }
                    
                }
            });
    
            falseLabel.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // System.out.print("Click");
                    boolean clickVal = false;
                    if (clickVal == ans) {
                        // user answer correctly
                        for (int i = 0; i < playerList.size(); i++) {
                            // revive player and give 1 health back
                            if (playerList.get(i).getStatus() == true) {
                                playerList.get(i).setHealth(1);
                                activePlayerHealth = 1;
                                qnsCount = 0;
                                qnsState = false;
                                playerList.get(i).setStatus();
                                
                            }
                        }
                        
                    } else {
                        // game over / lose
                        int score = 0;
                        Settings.setHighestScore(score);
                        qnsCount = 0;
                        qnsState = false;
                        app.getGameScreenManager().setScreen(STATE.GAME_OVER);
                        
                    }
                }
            });
            
            
            qnsStage.draw();
            // qnsTable.remove();

        } else if (paused) {
            
            // to force pause every 5 mins for 1 min
            if (forcedPause) {
                forcePauseDelta += delta;
            }
            if (forcePauseDelta > 60) { 

                forcedPause = false;
                paused = false;
                forcePauseDelta = 0;
                pauseTimeElapsed = delta;
            }
            
            // skin = app.getGenericAssetsManager().manager.get("skins/clean-crispy-ui.json", Skin.class);

            mainTable = new Table();
            mainTable.setFillParent(true);
            
            Gdx.input.setInputProcessor(pauseStage);

            
            TextButton unpause = addButton("Unpause");
            unpause.addListener(new ClickListener() {
                @Override
                public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                    app.getAudioManager().playSound("Music/click.mp3");
                    paused = false;
                    pauseStage.clear();
                }
            });
            if (forcedPause == true) {
                mainTable.removeActor(unpause, false);
            }
            addButton("Back to Menu").addListener(new ClickListener() {
                @Override
                public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                    app.getAudioManager().playSound("Music/click.mp3");
                    paused = false;
                    pauseStage.dispose();
                    dispose();
                    app.getGameScreenManager().setScreen(GameScreenManager.STATE.MAIN_MENU);
                    System.out.println("Back to menu Clicked"); //debugging
                }
            });

            addButton("Exit Game").addListener(new ClickListener() {
                @Override
                public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                    app.getAudioManager().playSound("Music/click.mp3");
                    System.out.println("Exit has been clicked"); //debugging
                    pauseStage.clear();
                    Gdx.app.exit();
                }
            });
            
            pauseStage.addActor(mainTable);
            pauseStage.draw();
            mainTable.remove();
        } else if (!paused) {
            // Clear the screen to the default clear color
            Gdx.input.setInputProcessor(inputManager);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            // Render the game normally
            super.render(delta);
            b2dr.render(world, camera.combined.cpy().scl(B2DConstants.PPM)); // scale the camera by pixel per meter
            

            // draw background and textures
            app.getBatch().begin();
            app.getBatch().draw(bg.get(0), 0, 0, camera.viewportWidth, camera.viewportHeight);


            // draw player
            for (int i = 0; i < playerList.size(); i ++) {
                if (playerList.get(i).getStatus() == false) {
                    // when idle
                    if (inputManager.getKeyHold() == false && playerJustHurt == false) {
                        if (playerIdleFrame > 3) {
                            playerIdleFrame = 0;
                        }
                        app.getBatch().draw(playerIdle.get(playerIdleFrame), playerList.get(i).getPositionX(), playerList.get(i).getPositionY());
                        playerIdleFrame += 1;
                    }
                    // when moving
                    if (inputManager.getKeyHold() == true){
                        if (playerWalkFrame > 5) {
                            playerWalkFrame = 0;
                        }
                        app.getBatch().draw(playerWalk.get(playerWalkFrame), playerList.get(i).getPositionX(), playerList.get(i).getPositionY());
                        playerWalkFrame += 1;
                    }
                    // when player hurt
                    if (playerJustHurt == true) {
                        if (playerHurtFrame > 0) {
                            playerHurtFrame = 0;
                            playerJustHurt = false;
                            playerBehaviour.setPlayerJustHurt(playerJustHurt);
                            playerHurtDelta = 0.0f;
                        } else if (playerHurtDelta > 0.5) {
                            playerHurtFrame += 1;
                            playerHurtDelta = 0.0f;
                        }
                        app.getBatch().draw(playerHurt.get(playerHurtFrame), playerList.get(i).getPositionX(), playerList.get(i).getPositionY());
                        playerHurtDelta += delta;
                        
                        
                    }
                }
                // when player die
                if (playerJustDied == true && playerList.get(i).getStatus() == true) {
                    if (playerDeathFrame == 3) {
                        playerDeathFrame = 0;
                        playerJustDied = false;
                        playerBehaviour.setPlayerJustDied(playerJustDied);
                        playerDeathDelta = 0.0f;
                    } else if (playerDeathDelta > 0.75) {
                        playerFullDeathDelta += playerDeathDelta;
                        playerDeathFrame += 1;
                        playerDeathDelta = 0.0f;
                        System.out.print(Math.ceil(playerFullDeathDelta));
                    }
                    app.getBatch().draw(playerDeath.get(playerDeathFrame), playerList.get(i).getPositionX(), playerList.get(i).getPositionY());
                    playerDeathDelta += delta;
                }
            }

            // draw boss
            for (int i = 0; i < bossList.size(); i++) {
                if (bossList.get(i).getStatus() == false) {
                    if (bossWalkFrame > 3) {
                        bossWalkFrame = 0;
                    }
                    app.getBatch().draw(bossWalk.get(bossWalkFrame), bossList.get(i).getPositionX(), bossList.get(i).getPositionY());
                    bossWalkFrame += 1;
                }
                if (bossList.get(i).getStatus() == true && bossJustDied == true) {
                    if (bossDeathFrame == 5) {
                        bossDeathFrame = 0;
                        bossJustDied = false;
                        playerBehaviour.setBossJustDied(bossJustDied);
                        bossDeathDelta = 0.0f;
                    } else if (bossDeathDelta > 0.75) {
                        bossDeathFrame += 1;
                        bossDeathDelta = 0.0f;
                    }
                    app.getBatch().draw(bossDeath.get(bossDeathFrame), bossList.get(i).getPositionX(), bossList.get(i).getPositionY());
                    bossDeathDelta += delta;
                }
            }

            // draw health food if not consumed
            for (int i = 0; i < healthFoodList.size(); i++) {
                if (healthFoodList.get(i).getConsume() == false) {
                    app.getBatch().draw(food.get(0), healthFoodList.get(i).getPositionX(), healthFoodList.get(i).getPositionY());
                }
                if (healthFoodList.get(i).getConsume()) {
                    timeSpawnHealthFood += delta;
                    if (timeSpawnHealthFood > 3) {
                        // set to false to spawn again
                        healthFoodList.get(i).setConsume();
                        timeSpawnHealthFood = 0;
                    }
                }          
            }

            // draw unhealthy food if not consumed
            for (int i = 0; i < unhealthyFoodList.size(); i++) {
                if (unhealthyFoodList.get(i).getConsume() == false) {
                    app.getBatch().draw(food.get(1), unhealthyFoodList.get(0).getPositionX(), unhealthyFoodList.get(0).getPositionY());
                }
                if (unhealthyFoodList.get(i).getConsume()) {
                    timeSpawnUnhealthyFood += delta;
                    if (timeSpawnUnhealthyFood > 2.5) {
                        unhealthyFoodList.get(i).setConsume();
                        timeSpawnUnhealthyFood = 0;
                    }
                }
            }            
            
            // draw podiums
            for (int i = 0; i < podiumQty; i++) {
                app.getBatch().draw(podiums.get(i), podiumList.get(i).getPositionX(), podiumList.get(i).getPositionY());
            }

            // draw bricks
            for (int i = 0; i < brickQty; i ++) {
                app.getBatch().draw(bricks.get(i), brickList.get(i).getPositionX(), brickList.get(i).getPositionY());
            }

            // draw hearts
            // if (activePlayerHealth > 0) {
            //     for (int i = 0; i < activePlayerHealth; i++) {
            //         app.getBatch().draw(hearts.get(i), heartX, heartY);
            //         heartX += 20;
            //     }
            //     // reset to default values
            //     heartX = 200;
            //     heartY = 635;
            // }
            
            for (int i = 0; i < playerList.size(); i++) {
                if (playerList.get(i).getStatus() == false) {
                    activePlayerHealth = playerList.get(i).getHealth();
                    for (int j = 0; j < activePlayerHealth; j++) {
                        app.getBatch().draw(hearts.get(i), heartX, heartY);
                        heartX += 20;
                    }
                }
            }
            // reset to default values
            heartX = 200;
            heartY = 635;
            
            app.getBatch().end();

            hud.stage.draw();
            stage.draw();
            
        }
        if (forcedPause == false) {
            // Check for user input to pause/unpause the game
            if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
                paused = !paused;
            }
        }
        
    }

    public void Initialise() {
        // reference pointers, not new instances
        // input reference
        inputManager = app.getInputManager();
        Gdx.input.setInputProcessor(inputManager);
        
        // entity references
        entityManager = app.getEntityManager();
        playerList = entityManager.getPlayerList();
        bossList = entityManager.getBossList();
        healthFoodList = entityManager.getHealthFoodList();
        unhealthyFoodList = entityManager.getUnhealthyFoodList();
        brickList = entityManager.getBrickList();
        podiumList = entityManager.getPodiumList();

        // texture references
        bg = app.getTextureManager().getBgTextures();
        playerIdle = app.getTextureManager().getPlayerIdleTextures();
        playerHurt = app.getTextureManager().getPlayerHurtTextures();
        playerWalk = app.getTextureManager().getPlayerWalkTextures();
        playerDeath = app.getTextureManager().getPlayerDeathTextures();
        bossWalk = app.getTextureManager().getBossWalkTextures();
        bossDeath = app.getTextureManager().getBossDeathTextures();
        food = app.getTextureManager().getFoodTextures();
        bricks = app.getTextureManager().getBrickTextures();
        podiums = app.getTextureManager().getPodiumTextures();
        hearts = app.getTextureManager().getHeartTextures();

        // behaviour references
        behaviourManager = app.getBehaviourManager();
        playerBehaviour = behaviourManager.getPlayerBehaviour();
        npcBehaviour = behaviourManager.getNPCBehaviour();

        // collision reference
        collisionManager = app.getCollisionManager();
    }



    public void hide()
    {
        app.getAudioManager().stopMusic();
    }


    @Override
    public void dispose()
    {
        super.dispose();
        world.dispose();
    }

    private TextButton addButton(String name){
        TextButton button = new TextButton(name,skin);
        mainTable.add(button).width(700).height(60).padBottom(20);
        mainTable.row();
        return button;
    }
 
    public void pause_dispose()
    {
        skin.dispose();
        pauseStage.dispose();
        qnsStage.dispose();
    }

    private void initQuestion() {
        skin = app.getGenericAssetsManager().manager.get("skins/clean-crispy-ui.json", Skin.class);
        Table qnsTable = new Table();
        qnsTable.setFillParent(true);
        qnsTable.center();
        
        // Set font and size
        BitmapFont font = skin.getFont("font");
        font.getData().setScale(2f);

        titleLabel = new Label("TRIVIA FOR HEALTH", new Label.LabelStyle(font, Color.WHITE));
        questionLabel = new Label("qns here", new Label.LabelStyle(font, Color.WHITE));
        trueLabel = new Label("True", new Label.LabelStyle(font, Color.WHITE));
        falseLabel = new Label("False", new Label.LabelStyle(font, Color.WHITE));

        // fade-in effect for the labels
        titleLabel.getColor().a = 0f;
        questionLabel.getColor().a = 0f;
        trueLabel.getColor().a = 0f;
        falseLabel.getColor().a = 0f;

        titleLabel.addAction(Actions.fadeIn(3.0f));
        questionLabel.addAction(Actions.fadeIn(3.0f));
        trueLabel.addAction(Actions.fadeIn(3.0f));
        falseLabel.addAction(Actions.fadeIn(3.0f));

        // Add labels to table
        qnsTable.add(titleLabel).colspan(2).padBottom(50f).row();
        qnsTable.add(questionLabel).center().padBottom(40f).row();
        qnsTable.add(trueLabel).center().padRight(600f);
        qnsTable.add(falseLabel);

        qnsStage.addActor(qnsTable);
    }

    private int randomQns() {
        int min = 1;
        int max = totalQns;
        return random.nextInt(max - min + 1) + min;
    }

}
