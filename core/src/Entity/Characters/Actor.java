package Entity.Characters;

import com.badlogic.gdx.math.Rectangle;


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

    // music functions 
}
