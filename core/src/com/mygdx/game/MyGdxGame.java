package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


import managers.AudioManager;
import managers.BehaviourManager;
import managers.CollisionManager;
import managers.EntityManager;
import managers.GameScreenManager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import managers.GenericAssetsManager;

import managers.InputManager;
import managers.TextureManager;
import utils.Settings;



public class MyGdxGame extends Game {

	//Application Vars
	public static String APP_TITLE = "Adventures of Sir Health";
	public static double APP_VERSION = 0.1;
	public static int APP_DESKTOP_WIDTH = 1280;
	public static int APP_DESKTOP_HEIGHT = 720;
	public static int APP_DESKTOP_FPS = 60;

	public SpriteBatch batch;
	public GenericAssetsManager genericAssetsManager;
	public GameScreenManager gameScreenManager;
	public ShapeRenderer shape;
	public AssetManager assetManager;
	public AudioManager audioManager;
	public TextureManager textureManager;
	public EntityManager entityManager;
	public BehaviourManager behaviourManager;
	public InputManager inputManager;
	public CollisionManager collisionManager;

	public Settings settings;

	
	@Override
	public void create () {
		// Initialize the game
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		genericAssetsManager = new GenericAssetsManager(assetManager);
		shape = new ShapeRenderer();

		audioManager = new AudioManager(assetManager);

		// load atlas assets
		genericAssetsManager.loadAsset("Bg/bg.atlas", TextureAtlas.class);
		genericAssetsManager.loadAsset("Boss/boss.atlas", TextureAtlas.class);
		genericAssetsManager.loadAsset("Food/food.atlas", TextureAtlas.class);
		genericAssetsManager.loadAsset("Player/player.atlas", TextureAtlas.class);
		genericAssetsManager.loadAsset("Tile/tile.atlas", TextureAtlas.class);
		genericAssetsManager.loadAsset("Heart/heart.atlas", TextureAtlas.class);
		
		// background music
		genericAssetsManager.loadAsset("Music/theme_music.ogg", Music.class);
		// this must set at last, else it will switch screen before all assets loaded
		
		// behaviourManager = new BehaviourManager(this);
		gameScreenManager = new GameScreenManager(this);
		textureManager = new TextureManager(this);
		inputManager = new InputManager(this);
		entityManager = new EntityManager(this);
		behaviourManager = new BehaviourManager(this);
		collisionManager = new CollisionManager(this);
		
	}

	@Override
	public void render () {
		super.render();
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
			Gdx.app.exit();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
		assetManager.dispose();
		gameScreenManager.dispose();
		// entityManager.dispose();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public GenericAssetsManager getGenericAssetsManager() {
		return genericAssetsManager;
	}

	public GameScreenManager getGameScreenManager() {
		return gameScreenManager;
	}

	public AudioManager getAudioManager() {
		return audioManager;
	}

	public TextureManager getTextureManager() {
		return textureManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
	public Settings getSettings() {
		return settings;
	}

	public InputManager getInputManager() {
		return inputManager;
	}

	public BehaviourManager getBehaviourManager() {
		return behaviourManager;
	}

	public CollisionManager getCollisionManager() {
		return collisionManager;
	}

	public void restartGame() {
		// restart player health and status and boss status
		entityManager.restartEntity();
		gameScreenManager.restartGameScreen();
	}

}
