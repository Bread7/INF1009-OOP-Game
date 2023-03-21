package Entity.Objects;

import com.badlogic.gdx.math.Rectangle;

public class Item extends GameObject{
    private Boolean isConsumed;

    public Item(float x, float y, Rectangle collideBox, Boolean isConsumed) {
        super(x, y, collideBox);
        this.isConsumed = isConsumed;
    }

    public Boolean getConsume() {
        return this.isConsumed;
    }

    public void setConsume() {
        if (this.isConsumed) {
            this.isConsumed = false;
        } else {
            this.isConsumed = true;
        }
    }

    public void destroy() {
        this.isConsumed = false;
    }
    
}
