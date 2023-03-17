package Entity.Objects;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.MyGdxGame;

import Entity.Characters.Actor;


public abstract class GameObject {
    private float positionX, positionY;
    private Rectangle collideBox;
    // protected final MyGdxGame app;

    public GameObject(float x, float y, Rectangle collideBox) {
        this.positionX = x;
        this.positionY = y;
        this.collideBox = collideBox;
        // this.app = app;
    }

    public float getPositionX() {
        return this.positionX;
    }

    public void setPostionX(float x) {
        this.positionX = x;
    }

    public float getPositionY() {
        return this.positionY;
    }

    public void setPositionY(float y) {
        this.positionY = y;
    }

    public void setCollideBox(Rectangle collideBox) {
        this.collideBox = collideBox;
    }

    public Rectangle getCollideBox() {
        return this.collideBox;
    }


    // music functions
    // public void playMusic() {    
    //     app.genericAssetsManager.loadAsset("Music/music_intro.mp3", Sound.class);
    //     app.audioManager.playSound("Music/music_intro.mp3");
    // }
    
    //    public void stopMusic()
    // {
    //     app.audioManager.stopMusic();
    // }
}
