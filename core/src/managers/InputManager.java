package managers;

import com.badlogic.gdx.InputAdapter;
import com.mygdx.game.MyGdxGame;
import Behaviours.PlayerBehaviour;
import managers.GameScreenManager.STATE;
import utils.InputKeysConstants;
import Entity.Characters.*;
import Entity.Objects.*;
import java.util.*;

public class InputManager extends InputAdapter implements InputKeysConstants {

    // get game screen active screen
    GameScreenManager gameScreenManager;

    // get game behaviours
    PlayerBehaviour playerBehaviour;

    // get collision manager
    CollisionManager collisionManager;

    // active player
    Player activePlayer;
    List<Brick> brickList;
    
    // random number generator
    Random random = new Random();

    // check for long press and set active key
    private boolean keyHold = false;
    private int activeKey = 0;

    private final MyGdxGame app;


    // constructor
    public InputManager(final MyGdxGame app) {
        this.app = app;

        gameScreenManager = this.app.getGameScreenManager();

    }

    // happens after constructor methods in the main class
    public void Initialise() {
        if (playerBehaviour == null) {
            playerBehaviour = this.app.getBehaviourManager().getPlayerBehaviour();
        }
        if (collisionManager == null) {
            collisionManager = this.app.getCollisionManager();
        }
        activePlayer = this.app.getEntityManager().getPlayerList().get(this.app.getEntityManager().activePlayerIndex());
        brickList = this.app.getEntityManager().getBrickList();
    }

    // set active key
    @Override
    public boolean keyDown(int keycode) {
        if (playerBehaviour == null) {
            Initialise();
        }

        // // old version
        if (keycode != anyKey) {
            setKeyHold(true);
            setActiveKey(keycode);
            return true;

        }
        return false;
    }

    // release active key
    @Override
    public boolean keyUp(int keycode) {
        if (keycode != anyKey && keycode == activeKey) {
            setActiveKey(0);
            setKeyHold(false);
            return true;
        }
        if (getKeyHold() == true) {
            return true;
        }
        return false;
    }

    public void forceKeyRelease() {
        setActiveKey(0);
        setKeyHold(false);
    }

    private void setKeyHold(boolean bool) {
        keyHold = bool;
    }

    public boolean getKeyHold() {
        return keyHold;
    }

    private void setActiveKey(int keycode) {

        activeKey = keycode;
    }

    public int getActiveKey() {
        return activeKey;
    }

    public int getLeftDirection() {
        return leftDirection;
    }

    public int getRightDirection() {
        return rightDirection;
    }

    public int randomDirection() {
        int min = leftDirection;
        int max = rightDirection;
        return random.nextInt(max - min + 1) + min;
    }

    
    //----------------------------------------------------------------------------------------
    //--------------------------------------INPUT---------------------------------------------
    //----------------------------------------------------------------------------------------

    public void keyMap(int keycode) {
        if (gameScreenManager.getActiveScreen() == STATE.GAME_SCREEN) {
            keyGameMap(keycode);
        }
        if (gameScreenManager.getActiveScreen() == STATE.SETTING_SCREEN) {
            // keySettingsMapInt(keycode);
        }
        if (gameScreenManager.getActiveScreen() == STATE.MAIN_MENU) {
            // keyMenuMapInt(keycode);
        }
    }

    public void keyGameMap(int keycode) {
        // game movement keys
        if (keycode == sKey) {
            playerBehaviour.movement(keycode);
        }
        if (keycode == wKey) {
            playerBehaviour.movement(keycode);
        }
        if (keycode == dKey) {
            playerBehaviour.movement(keycode);
        }
        if (keycode == aKey) {
            playerBehaviour.movement(keycode);
        }

        // pause or unpause key
        if (keycode == pKey) {
            System.out.println("p");
        }

        // volume keys
        if (keycode == minusKey) {
            System.out.println("minus");
        }
        if (keycode == plusKey) {
            System.out.println("plus");
        }
        if (keycode == escKey) {
            System.out.println("esc");
        }
    }

    public void keyMenuMapInt(int keycode) {
        // menu navigation keys
        if (keycode == downKey) {
            System.out.println("down");
        }
        if (keycode == upKey) {
            System.out.println("up");
        }
        if (keycode == rightKey) {
            System.out.println("right");
        }
        if (keycode == leftKey) {
            System.out.println("left");
        }

        // volume keys
        if (keycode == minusKey) {
            System.out.println("minus");
        }
        if (keycode == plusKey) {
            System.out.println("plus");
        }
        if (keycode == enterKey) {
            System.out.println("enter");
        }
    }

    public void keySettingsMapInt(int keycode) {
        // menu navigation keys
        if (keycode == downKey) {
            System.out.println("down");
        }
        if (keycode == upKey) {
            System.out.println("up");
        }
        if (keycode == rightKey) {
            System.out.println("right");
        }
        if (keycode == leftKey) {
            System.out.println("left");
        }

        // volume keys
        if (keycode == minusKey) {
            System.out.println("minus");
        }
        if (keycode == plusKey) {
            System.out.println("plus");
        }

        // select option keys
        if (keycode == enterKey) {
            System.out.println("enter");
        }
        if (keycode == escKey) {
            System.out.println("esc");
        }
    }
    
}
