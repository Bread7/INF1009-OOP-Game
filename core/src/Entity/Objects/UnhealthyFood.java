package Entity.Objects;

import com.mygdx.game.MyGdxGame;
import com.badlogic.gdx.math.Rectangle;

public class UnhealthyFood extends Item{
    private int direction;
    private float decSpeed;

    public UnhealthyFood(float x, float y, Rectangle collideBox, Boolean isConsumed, int direction, float decSpeed) {
        super(x, y, collideBox, isConsumed);
        this.direction = direction;
        this.decSpeed = decSpeed;
    }

    public int getDirection() {
         return this.direction;
    }

    public void setDirection(int keysCode) {
        this.direction = keysCode;
    }

    public float getDecSpeed() {
        return this.decSpeed;
    }

    public void setDecSpeed(float speed) {
        this.decSpeed = speed;
    }

    public void movement() {}
}
