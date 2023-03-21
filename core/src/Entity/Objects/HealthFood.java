package Entity.Objects;

import com.badlogic.gdx.math.Rectangle;

public class HealthFood extends Item{
    private float addSpeed;

    public HealthFood(float x, float y, Rectangle collideBox, Boolean isConsumed, float addSpeed) {
        super(x, y, collideBox, isConsumed);
        this.addSpeed = addSpeed;
    }

    public float getAddSpeed() {
        return this.addSpeed;
    }

    public void setAddSpeed(float speed) {
        this.addSpeed = speed;
    }
}
