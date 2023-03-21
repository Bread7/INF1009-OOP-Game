package Entity.Objects;

import com.badlogic.gdx.math.Rectangle;

public class Podium extends Static{
    private Boolean onTop = false;

    public Podium(float x, float y, Rectangle collideBox, Boolean isBreakable) {
        super(x, y, collideBox, isBreakable);
    }

    public Boolean checkPodium() {
        return this.onTop;
    }

    public void standOnPodium() {
        if (onTop) {
            onTop = false;
        } else {
            onTop = true;
        }
    }
    
}
