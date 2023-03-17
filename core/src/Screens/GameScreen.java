package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

import Behaviours.NPCBehaviour;
import Behaviours.PlayerBehaviour;
import managers.BehaviourManager;
import managers.CollisionManager;
import managers.EntityManager;
import managers.InputManager;
import managers.GameScreenManager.STATE;

import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import utils.B2DConstants;
import utils.Hud;
import utils.MapBricks;
import utils.SurfaceAreaBox;
import Entity.Characters.*;
import Entity.Objects.*;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.*;


public class GameScreen extends BaseScreen implements MapBricks, SurfaceAreaBox {
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
    private Viewport viewport;

    private EntityManager entityManager;
    private InputManager inputManager;
    private BehaviourManager behaviourManager;
    private PlayerBehaviour playerBehaviour;
    private NPCBehaviour npcBehaviour;

    private float foodX, foodY, badFoodX, badFoodY;
    private HealthFood healthFood;
    private UnhealthyFood unhealthyFood;

    public List<AtlasRegion> bg, playerIdle, playerHurt, bossWalk, bossDeath, tiles, food, podiums, bricks;
    public List<Brick> brickList;
    public List<Podium> podiumList;
    public List<HealthFood> healthFoodList;
    public List <UnhealthyFood> unhealthyFoodList;
    public List<Player> playerList;
    public List<Boss> bossList;

    public STATE state = STATE.GAME_SCREEN;

    public float timeJump, timeSpawnHealthFood, timeSpawnUnhealthyFood, timeBossAtk, timeElapsed;

    private CollisionManager collisionManager;

    private ShapeRenderer shape;
    private Rectangle rec;

    public GameScreen(final MyGdxGame app) {
        super(app);
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, app.APP_DESKTOP_WIDTH, app.APP_DESKTOP_HEIGHT);
        viewport = new ScreenViewport();
        stage = new Stage(viewport);
        hud = new Hud(app.batch);
        b2dr = new Box2DDebugRenderer();
        pauseTexture = new TextureRegion(new Texture("badlogic.jpg"));

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
        

        // testing purposes
        // for (int i = 0; i < brickList.size(); i++) {
        //     System.out.print(brickList.get(i).getPositionX() + " ");
        //     System.out.println(brickList.get(i).getCollideBox().getX());
        // }
        shape = new ShapeRenderer();
        shape.setAutoShapeType(true);
        // rec = new Rectangle(20, 20, 21, 27);
        // System.out.print(brickList.get(54).getPositionX());
        // System.out.print(brickList.get(55).getPositionX());
        // System.out.print(brickList.get(56).getPositionX());
    }

    @Override
    public void update(float delta) {

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

        // pass delta values to behaviours and input for functions handling
        // timeSpawnHealthFood += delta;
        timeBossAtk+= delta;
        timeElapsed += delta;

        // spawn npc behaviours
        npcBehaviour.spawnUnhealthyFood();
        npcBehaviour.bossMovement();
        // npcBehaviour.bossNearPlayer();

        // constant collision checks for player
        collisionManager.fullPlayerCollisionCheck();
        
        // check if player die
        
        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).getStatus() == true) {
                // go to gameover screen

            }
        }


        // when player wins game
        for (int i = 0; i < podiumList.size(); i++) {
            if (podiumList.get(i).checkPodium()) {
                // go to gameoverscreen;

            }
        }
    }
    @Override
    public void render(float delta)
    {
        // if it's not pause
        if (paused) {
            // Clear the screen to the default clear color
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            // Render the pause screen texture
            app.getBatch().setProjectionMatrix(camera.combined);
            app.getBatch().begin();
            app.getBatch().draw(pauseTexture, camera.viewportWidth / 2 - pauseTexture.getRegionWidth() / 2,
                    camera.viewportHeight / 2 - pauseTexture.getRegionHeight() / 2);
            app.getBatch().end();
        } else {
            // Clear the screen to the default clear color
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            // Render the game normally
            super.render(delta);
            b2dr.render(world, camera.combined.cpy().scl(B2DConstants.PPM)); // scale the camera by pixel per meter
            hud.stage.draw();
            stage.draw();

            // draw background and textures
            app.getBatch().begin();
            app.getBatch().draw(bg.get(0), 0, 0, camera.viewportWidth, camera.viewportHeight);


            // draw player if not dead
            for (int i = 0; i < playerList.size(); i ++) {
                if (playerList.get(i).getStatus() == false) {
                    app.getBatch().draw(playerIdle.get(i), playerList.get(i).getPositionX(), playerList.get(i).getPositionY());
                }
            }

            // draw boss if not dead
            for (int i = 0; i < bossList.size(); i++) {
                if (bossList.get(i).getStatus() == false) {
                    app.getBatch().draw(bossWalk.get(1), bossList.get(i).getPositionX(), bossList.get(i).getPositionY());
                }
            }

            // if (timeBossAtk > 3) {
            //     timeBossAtk = 0.0f;
            // }

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
            app.getBatch().end();
            
            // testing purposes
            shape.begin();
            // shape.rect(bossList.get(0).getCollideBox().x, bossList.get(0).getCollideBox().y, bossList.get(0).getCollideBox().width, bossList.get(0).getCollideBox().height);
            shape.end();
        }

        // Check for user input to pause/unpause the game
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            paused = !paused;
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

        // foodX = healthFoodList.get(0).getPositionX();
        // foodY = healthFoodList.get(0).getPositionY();
        badFoodX = unhealthyFoodList.get(0).getPositionX();
        badFoodY = unhealthyFoodList.get(0).getPositionY();

        // texture references
        bg = app.getTextureManager().getBgTextures();
        playerIdle = app.getTextureManager().getPlayerIdleTextures();
        playerHurt = app.getTextureManager().getPlayerHurtTextures();
        bossWalk = app.getTextureManager().getBossWalkTextures();
        bossDeath = app.getTextureManager().getBossDeathTextures();
        tiles = app.getTextureManager().getTileTextures();
        food = app.getTextureManager().getFoodTextures();
        bricks = app.getTextureManager().getBrickTextures();
        podiums = app.getTextureManager().getPodiumTextures();

        // behaviour references
        behaviourManager = app.getBehaviourManager();
        playerBehaviour = behaviourManager.getPlayerBehaviour();
        npcBehaviour = behaviourManager.getNPCBehaviour();

        // collision reference
        collisionManager = app.getCollisionManager();
    }

    public void updatePause() {
        if (paused) {
            // unpause
            paused = !paused;
        } else {
            // to pause
            paused = !paused;
        }
    }

    public void hide()
    {
        app.getAudioManager().stopMusic();
    }

    public boolean getPause() {
        return paused;
    }

    @Override
    public void dispose()
    {
        super.dispose();
        world.dispose();
    }
}
