package utils;

import com.badlogic.gdx.Input;

public interface InputKeysConstants {
    // for directions
    public static int leftDirection = 0;
    public static int rightDirection = 1;
    public static int upDirection = 2;
    public int downDirection = 3;

    // for movement or navigation
    public static int wKey = Input.Keys.W;
    public static int aKey = Input.Keys.A;
    public static int sKey = Input.Keys.S;
    public static int dKey = Input.Keys.D;
    public static int leftKey = Input.Keys.LEFT;
    public static int rightKey = Input.Keys.RIGHT;
    public static int downKey = Input.Keys.DOWN;
    public static int upKey = Input.Keys.UP;
    public static int spaceKey = Input.Keys.SPACE;

    // for pause/to menu
    public static int pKey = Input.Keys.P;
    public static int enterKey = Input.Keys.ENTER;

    // to exit game
    public static int escKey = Input.Keys.ESCAPE;
    
    // for volume adjustments
    public static int minusKey = Input.Keys.MINUS;
    public static int plusKey = Input.Keys.PLUS;

    // any key int value
    public static int anyKey = Input.Keys.ANY_KEY;
}
