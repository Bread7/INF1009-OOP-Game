package Entity.Characters;

import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.Input.Keys;
import com.mygdx.game.MyGdxGame;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;

import Entity.Objects.GameObject;

public class Player extends Actor{
    private int direction;
    private Boolean isDead = false;
    private int health = 3;
    private float speed = 3;
    private float maxSpeed = 6;

    public Player(float x, float y, Rectangle collideBox, int direction) {
        super(x, y, collideBox);
        this.direction = direction;        
    }

    public int getDirection() {
        return this.direction;
    }

    public void setDirection(int keyCode) {
        this.direction = keyCode;
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

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        if (health > -1 && health < 4) {
            this.health = health;
        }
    }

    public float getSpeed() {
        return this.speed;
    }

    public void setSpeed(float speed) {
        if (speed > 0 && speed < this.maxSpeed + 1) {
            this.speed = speed;
        }
    }

    public void movement(int keysCode, float x, float y) {
        if(keysCode == Keys.LEFT) {
            x -= 1;
            setPositionX(x);
        }
        if (keysCode == Keys.RIGHT) {
            x += 1;
            setPositionX(x);
        }
        if (keysCode == Keys.UP) {
            y += 1;
            setPositionY(y);
        }
        if (keysCode == Keys.DOWN) {
            y -= 1;
            setPositionY(y);
        }
    }
    
}
