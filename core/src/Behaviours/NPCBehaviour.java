package Behaviours;

import com.mygdx.game.MyGdxGame;

import managers.EntityManager;
import utils.InputKeysConstants;
import Entity.Characters.*;
import Entity.Objects.*;
import com.badlogic.gdx.math.*;

import java.util.*;
// import java.lang.*;

public class NPCBehaviour implements InputKeysConstants {

    private final MyGdxGame app;

    // used as reference pointer
    private EntityManager entityManager;
    

    private List<Boss> bossList;
    private List<Player> playerList;
    private List<UnhealthyFood> foodThrownList;
    // private List<Static> staticList;
    // private List<Item> itemList;

    private Boss activeBoss;
    private Player activePlayer;
    private UnhealthyFood unhealthyFood;
    private HealthFood healthFood;

    private List<UnhealthyFood> unhealthyFoodList;
    private float maxSodaY = 250;

    // boss coordinates
    private float ogX, maxX, minX;

    private float timeBossAtk;

    public NPCBehaviour(final MyGdxGame app) {
        this.app = app;

        entityManager = this.app.getEntityManager();

        Initialise();
    }

    public void Initialise() {
        bossList = entityManager.getBossList();
        playerList = entityManager.getPlayerList();
        foodThrownList = new ArrayList<UnhealthyFood>();
        // staticList = entityManager.getStaticList();
        // itemList = entityManager.getItemList();

        activeBoss = bossList.get(0);
        ogX = activeBoss.getPositionX();
        maxX = ogX + 51;
        minX = ogX - 51;
        activePlayer = playerList.get(entityManager.activePlayerIndex());
        

        unhealthyFoodList = entityManager.getUnhealthyFoodList();

        healthFood = entityManager.getHealthFood();
        unhealthyFood = entityManager.getUnhealthyFood();
    }

    public void setTimeBossAtk(float delta) {
        if (delta > 0.0f) {
            timeBossAtk = delta;
        }
    }
    
    public void bossMovement() {
        int currDir = activeBoss.getDirection();
        float speed = activeBoss.getSpeed();
        float currBossX = activeBoss.getPositionX();
        // float currBossY
        if (currDir == leftDirection) {
            if (currBossX - speed < minX) {
                activeBoss.setDirection(rightDirection);
                activeBoss.setPositionX(currBossX + speed);
                activeBoss.getCollideBox().setX(currBossX + speed);
            } else {
                activeBoss.setPositionX(currBossX - speed);
                activeBoss.getCollideBox().setX(currBossX - speed);
            }
        }
        if (currDir == rightDirection) {
            if (currBossX + speed > maxX) {
                activeBoss.setDirection(leftDirection);
                activeBoss.setPositionX(currBossX - speed);
                activeBoss.getCollideBox().setX(currBossX - speed);
            } else {
                activeBoss.setPositionX(currBossX + speed);
                activeBoss.getCollideBox().setX(currBossX + speed);
            }
        }
    }


    

    public void spawnUnhealthyFood() {
        float moveSpeed = 2;
        for (int i = 0; i < unhealthyFoodList.size(); i++) {
            float y = unhealthyFoodList.get(i).getPositionY();
            // System.out.print(unhealthyFoodList.get(i).getConsume());
            if (unhealthyFoodList.get(i).getConsume() == false) {
                // unhealthyFoodList.get(i).setPositionY(y + moveSpeed);
                // unhealthyFoodList.get(i).getCollideBox().setY(y + moveSpeed);
                if (y < maxSodaY) {
                    unhealthyFoodList.get(i).setPositionY(y + moveSpeed);
                    unhealthyFoodList.get(i).getCollideBox().setY(y + moveSpeed);
                } else {
                    unhealthyFoodList.get(i).setPositionY(10);
                    unhealthyFoodList.get(i).getCollideBox().setY(10);
                }
            }
            // reset unhealthy food y axis values when eaten
            if (unhealthyFoodList.get(i).getConsume()) {
                unhealthyFoodList.get(i).setPositionY(10);
                unhealthyFoodList.get(i).getCollideBox().setY(10);
            }
        }
    }

    // not working
    public void bossNearPlayer() {
        float currBossX = activeBoss.getPositionX();
        float currBossY = activeBoss.getPositionY();
        int bossDirection = activeBoss.getDirection();
        float currPlayerX = activePlayer.getPositionX();
        int playerDirection = activePlayer.getDirection();
        if (Math.abs(currBossX - currPlayerX) < 300) {
            System.out.print("player detected");
            if (bossDirection == leftDirection) {
                UnhealthyFood badFood = entityManager.createUnhealthyFood(currBossX, currBossY, bossDirection);
                foodThrownList.add(badFood);
                bossThrowFood(badFood, currBossX, leftDirection);

            } else if (bossDirection == rightDirection) {
                UnhealthyFood badFood = entityManager.createUnhealthyFood(currBossX, currBossY, bossDirection);
                foodThrownList.add(badFood);
                bossThrowFood(badFood, currBossX, rightDirection);
            }
        }
    }


    // to rework
    public void bossThrowFood(UnhealthyFood food, float bossX, int direction) {
        float x = bossX;
        float movespeed = 1;
        int foodNumber = foodThrownList.indexOf(food);
        if (direction == leftDirection) {
            // int foodNumber = foodThrownList.indexOf(food);
            // foodThrownList.get(foodNumber).setPostionX(x - movespeed);
            // foodThrownList.get(foodNumber).getCollideBox().setX(x - movespeed);
            // unhealthyFood.setPostionX(x - movespeed);
        } else if (direction == rightDirection) {
            // foodThrownList.get(0).setPostionX(x + movespeed);
            // foodThrownList.get(0).getCollideBox().setX(x + movespeed);
            // unhealthyFood.setPostionX(x + movespeed);
        }
        
    }

    public void bossDie() {
        activeBoss.setStatus();
    }
}
