package utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;


public class ScrollingBackground {

    // Constants
    public static final int DEFAULT_SPEED = 100;
    public static final int ACCELERATION = 50;
    public static final int GOAL_REACH_ACCELERATION = 300;

    // Properties
    Texture image;
    float x1, x2;
    int speed; // In pixels / second
    int goalSpeed;
    float imageScale;
    boolean speedFixed;

    public ScrollingBackground () {
        image = new Texture("background.png");

        x1 = -image.getWidth();
        x2 = 0;
        speed = 0;
        goalSpeed = DEFAULT_SPEED;
        imageScale = MyGdxGame.APP_DESKTOP_WIDTH / image.getWidth();
        speedFixed = true;
    }

    public void updateAndRender (float deltaTime, SpriteBatch batch) {
        // Speed adjustment to reach goal
        if (speed < goalSpeed) {
            speed += GOAL_REACH_ACCELERATION * deltaTime;
            if (speed > goalSpeed)
                speed = goalSpeed;
        } else if (speed > goalSpeed) {
            speed -= GOAL_REACH_ACCELERATION * deltaTime;
            if (speed < goalSpeed)
                speed = goalSpeed;
        }

        if (!speedFixed)
            speed += ACCELERATION * deltaTime;

        x1 += speed * deltaTime;
        x2 += speed * deltaTime;

        // If image reached the right edge of screen and is not visible, put it back on left
        if (x1 >= MyGdxGame.APP_DESKTOP_WIDTH)
            x1 = x2 - image.getWidth() * imageScale;

        if (x2 >= MyGdxGame.APP_DESKTOP_WIDTH)
            x2 = x1 - image.getWidth() * imageScale;

        // Render
        batch.draw(image, x1, 0, image.getWidth() * imageScale, MyGdxGame.APP_DESKTOP_HEIGHT);
        batch.draw(image, x2, 0, image.getWidth() * imageScale, MyGdxGame.APP_DESKTOP_HEIGHT);
    }

    public void setSpeed (int goalSpeed) {
        this.goalSpeed = goalSpeed;
    }

    public void setSpeedFixed (boolean speedFixed) {
        this.speedFixed = speedFixed;
    }

    public void resize(int width, int height) {
        imageScale = width / image.getWidth();
    }
}