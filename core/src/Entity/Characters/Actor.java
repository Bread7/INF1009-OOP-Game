package Entity.Characters;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.Rectangle;

import managers.Audio;
import managers.GenericAssetsManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.MyGdxGame;

import Entity.Objects.GameObject;

public abstract class Actor {
    private float positionX, positionY;
    private Rectangle collideBox;
    // protected final MyGdxGame app;

    public Actor(float x, float y, Rectangle collideBox) {
        this.positionX = x;
        this.positionY = y;
        this.collideBox = collideBox;
        // this.app = app;
    }

    public float getPositionX() {
        return this.positionX;
    }

    public void setPositionX(float x) {
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

    // collision functions
    public Boolean detectCollision() {
        //          CollidableEntity = (CollidableEntity) other;
        //  if (this.getx() < CollidableEntity.getx() + CollidableEntity.getWidth() &&
        //          this.gety() < CollidableEntity.gety() + CollidableEntity.getHeight() &&
        //          this.getx() + this.getWidth() > CollidableEntity.getx() &&
        //          this.gety() + this.getHeight() > CollidableEntity.gety()){
        //      return true;
        //  }
        return false;
        
    }

    public void handleCollision(GameObject item) {
                     //CollidableEntity = (CollidableEntity) other;
         //if (this.getx() < CollidableEntity.getx() + CollidableEntity.getWidth() &&
                 //this.gety() < CollidableEntity.gety() + CollidableEntity.getHeight() &&
                 //this.getx() + this.getWidth() > CollidableEntity.getx() &&
                 //this.gety() + this.getHeight() > CollidableEntity.gety()
        
        // return true;
    }

    public void handleCollision(Actor actor) {
                     //CollidableEntity = (CollidableEntity) other;
         //if (this.getx() < CollidableEntity.getx() + CollidableEntity.getWidth() &&
                 //this.gety() < CollidableEntity.gety() + CollidableEntity.getHeight() &&
                 //this.getx() + this.getWidth() > CollidableEntity.getx() &&
                 //this.gety() + this.getHeight() > CollidableEntity.gety()
        
        // return true;
    }

    // music functions 
    // public void playMusic() {
        
    //     app.genericAssetsManager.loadAsset("Music/click.mp3", Sound.class);
    //     app.audioManager.playSound("Music/click.mp3");

    // }

    //    public void stopMusic()
    // {
    //     app.audioManager.stopMusic();
    // }
}
