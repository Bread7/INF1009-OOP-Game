package Entity.Objects;

import com.mygdx.game.MyGdxGame;
import com.badlogic.gdx.math.Rectangle;

public class Brick extends Static{

    public Brick(float x, float y, Rectangle collideBox, Boolean isBreakable) {
        super(x, y, collideBox, isBreakable);
    }

    public void destroy() {
        // to edit
    }
}
