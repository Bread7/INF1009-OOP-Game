package Entity.Objects;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.MyGdxGame;


public class Static extends GameObject{
    private Boolean isBreakable;

    public Static(float x, float y, Rectangle collideBox, boolean isBreakable) {
        super(x, y, collideBox);
        this.isBreakable = isBreakable;
    }

    public Boolean getBreak() {
        return this.isBreakable;
    }

    public void setBreak() {
        if (this.isBreakable) {
            this.isBreakable = false;
        } else {
            this.isBreakable = true;
        }
    }
}
