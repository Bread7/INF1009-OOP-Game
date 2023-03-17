package Entity.Characters;

import com.badlogic.gdx.audio.*;
import com.mygdx.game.MyGdxGame;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.MyGdxGame;

import Entity.Objects.GameObject;

public class Boss extends Actor{
    private int direction;
    private Boolean isDead = false;
    private float speed = 2;

    public Boss(float x, float y, Rectangle collideBox, int direction) {
        super(x, y, collideBox);
        this.direction = direction;
    }

    public int getDirection() {
        return this.direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Boolean getStatus() {
        return this.isDead;
    }

    public void setStatus() {
        if (this.isDead) {
            this.isDead = false;
        } else {
            this.isDead = true;
        }
    }

    public float getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


    public void die() {
        this.isDead = true;
        // app.genericAssetsManager.loadAsset("Music/gameOver.mp3", Sound.class);
        // app.audioManager.playSound("Music/gameOver.mp3");
    }

    // // stop music
    //    public void stopMusic()
    // {
    //     app.audioManager.stopMusic();
    // }
}
