package Behaviours;

import com.badlogic.gdx.Game;
import com.mygdx.game.MyGdxGame;
import Entity.Characters.*;
import Entity.Objects.*;
import managers.EntityManager;
import utils.InputKeysConstants;

import java.util.*;

public class PlayerBehaviour implements InputKeysConstants {

    private final MyGdxGame app;

    // used as reference pointer
    private EntityManager entityManager;
    

    private List<Player> playerList;

    private Player activePlayer;
    private float minX, maxX, maxY;


    public PlayerBehaviour(final MyGdxGame app) {
        this.app = app;

        entityManager = this.app.getEntityManager();

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
            // float bossLeftX = boss.getCollideBox().getX() - ( boss.getCollideBox().getWidth() / 2 );
            // float bossRightX = boss.getCollideBox().getX() + ( boss.getCollideBox().getWidth() / 2 );

            float playerBottomY = activePlayer.getCollideBox().getY() - (activePlayer.getCollideBox().getHeight() / 2);
            // float playerLeftX = activePlayer.getCollideBox().getX() - (activePlayer.getCollideBox().getWidth() / 2);
            // float playerRightX = activePlayer.getCollideBox().getX() + (activePlayer.getCollideBox().getWidth() / 2);

            if (playerBottomY >= bossTopY) {
                // player kill boss
                System.out.print(" on top ");
                this.app.getBehaviourManager().getNPCBehaviour().bossDie();
            } else {
                // player get damage
                if (activePlayer.getHealth()>0) {
                    System.out.print("hurt ");
                    activePlayer.setHealth(activePlayer.getHealth() - 1);
                    activePlayer.setPositionX(activePlayer.getPositionX() - activePlayer.getSpeed() * 40);
                    activePlayer.getCollideBox().setX(activePlayer.getCollideBox().getX() - activePlayer.getSpeed() * 40);
                }
            }
        }
        
    }

    public void winGame(Podium podium) {
        System.out.print("game won");
        podium.standOnPodium();
        evadeBrick();
    }

    public void playerDie(Player player) {
        player.setStatus();
        // playerList.remove(activePlayer);
    }

    
    // public void consume
    
}
