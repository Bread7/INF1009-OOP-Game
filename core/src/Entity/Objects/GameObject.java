package Entity.Objects;

import com.badlogic.gdx.math.Rectangle;


public abstract class GameObject {
    private float positionX, positionY;
    private Rectangle collideBox;

    public GameObject(float x, float y, Rectangle collideBox) {
        this.positionX = x;
        this.positionY = y;
        this.collideBox = collideBox;
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
}
