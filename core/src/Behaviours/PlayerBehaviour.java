package Behaviours;

import com.mygdx.game.MyGdxGame;
import Entity.Characters.*;
import Entity.Objects.*;
import managers.EntityManager;
import managers.TextureManager;
import utils.InputKeysConstants;

import java.util.*;

public class PlayerBehaviour implements InputKeysConstants {

    private final MyGdxGame app;

    // used as reference pointer
    private EntityManager entityManager;
    private TextureManager textureManager;
    

    private List<Player> playerList;

    private Player activePlayer;
    private float minX, maxX, maxY;

    private boolean playerJustHurt, playerJustDied, bossJustDied;


    public PlayerBehaviour(final MyGdxGame app) {
        this.app = app;

        entityManager = this.app.getEntityManager();
        textureManager = this.app.getTextureManager();

        Initialise();
        
    }

    public void Initialise() {
        playerList = entityManager.getPlayerList();
        if (playerList != null) {
            activePlayer = playerList.get(entityManager.activePlayerIndex());
        }
        minX = 0;
        maxX = 1260;
        maxY = activePlayer.getPositionY()+250;
        playerJustDied = false;
        playerJustHurt = false;
        bossJustDied = false;
    }

    public boolean getPlayerJustDied() {
        return playerJustDied;
    }

    public boolean getPlayerJustHurt() {
        return playerJustHurt;
    }

    public boolean getBossJustDied() {
        return bossJustDied;
    }

    public void setPlayerJustDied(boolean bool) {
        playerJustDied = bool;
    }

    public void setPlayerJustHurt(boolean bool) {
        playerJustHurt = bool;
    }

    public void setBossJustDied(boolean bool) {
        bossJustDied = bool;
    }

    //----------------------------------------------------------------------------------------
    //--------------------------------------BEHAVIOUR-----------------------------------------
    //----------------------------------------------------------------------------------------

    public void movement(int keycode) {
        float x = activePlayer.getPositionX();
        float y = activePlayer.getPositionY();
        float speed = activePlayer.getSpeed();
        // player movement
        if (keycode == wKey || keycode == spaceKey) {
            if (y + speed < maxY) {
                activePlayer.setPositionY(y + speed);
                activePlayer.getCollideBox().setY(y + speed);
                activePlayer.setDirection(upDirection);
            }            
        }
        if (keycode == aKey) {
            if (x - speed > minX) {
                if (textureManager.getPlayerTextureDirection() == rightDirection) {
                    textureManager.flipPlayerTextures();
                }
                activePlayer.setPositionX(x - speed);
                activePlayer.getCollideBox().setX(x - speed);
                activePlayer.setDirection(leftDirection);
            }
        }
        if (keycode == sKey) {
            activePlayer.setPositionY(y - speed);
            activePlayer.getCollideBox().setY(y - speed);
            activePlayer.setDirection(downDirection);
        }
        if (keycode == dKey) {
            if (x + speed < maxX) {
                if (textureManager.getPlayerTextureDirection() == leftDirection) {
                    textureManager.flipPlayerTextures();
                }
                activePlayer.setPositionX(x + speed);
                activePlayer.getCollideBox().setX(x + speed);
                activePlayer.setDirection(rightDirection);
            }
        }
    }

    public void consumeHealthFood(HealthFood food) {
        // if healthy, add speed
        if (food.getConsume() == false) {
            float addSpeed = food.getAddSpeed();
            activePlayer.setSpeed(activePlayer.getSpeed() + addSpeed);
            food.setConsume();
        }
    }

    public void consumeUnhealthyFood(UnhealthyFood unhealthyFood) {
        // if unhealthy, decrease speed
        if (unhealthyFood.getConsume() == false) {
            float decSpeed = unhealthyFood.getDecSpeed();
            activePlayer.setSpeed(activePlayer.getSpeed() - decSpeed);
            unhealthyFood.setConsume();
        }
    }

    public void evadeBrick() {
        int direction = activePlayer.getDirection();
        if (direction == leftDirection){
            movement(dKey);
        } else if (direction == upDirection) {
            movement(sKey);
        } else if (direction == downDirection) {
            movement(wKey);
        } else if (direction == rightDirection) {
            movement(aKey);
        }
        
    }

    public void playerTouchBoss(Boss boss) {
        if (boss.getStatus() == false) {
            float bossTopY = boss.getCollideBox().getY() + ( boss.getCollideBox().getHeight() / 2);
            float playerBottomY = activePlayer.getCollideBox().getY() - (activePlayer.getCollideBox().getHeight() / 2);

            if (playerBottomY >= bossTopY) {
                // player kill boss
                bossJustDied = true;
                this.app.getBehaviourManager().getNPCBehaviour().bossDie();
            } else {
                // player get damage
                if (activePlayer.getHealth()>0) {
                    playerJustHurt = true;
                    activePlayer.setHealth(activePlayer.getHealth() - 1);
                    activePlayer.setPositionX(activePlayer.getPositionX() - activePlayer.getSpeed() * 40);
                    activePlayer.getCollideBox().setX(activePlayer.getCollideBox().getX() - activePlayer.getSpeed() * 40);
                }
                if (activePlayer.getHealth() == 0) {
                    activePlayer.setStatus();
                }
            }
        }
        
    }

    public void winGame(Podium podium) {
        podium.standOnPodium();
        evadeBrick();
    }

    public void playerDie(Player player) {
        playerJustDied = true;
        player.setStatus();
    }
    
}
