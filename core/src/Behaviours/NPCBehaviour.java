package Behaviours;

import com.mygdx.game.MyGdxGame;

import managers.EntityManager;
import managers.TextureManager;
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
    private TextureManager textureManager;

    private List<Boss> bossList;
    private Boss activeBoss;

    private List<UnhealthyFood> unhealthyFoodList;
    private float maxSodaY = 250;

    // boss coordinates
    private float ogX, maxX, minX;


    public NPCBehaviour(final MyGdxGame app) {
        this.app = app;

        entityManager = this.app.getEntityManager();
        textureManager = this.app.getTextureManager();

        Initialise();
    }

    public void Initialise() {
        bossList = entityManager.getBossList();

        activeBoss = bossList.get(0);
        ogX = activeBoss.getPositionX();
        maxX = ogX + 51;
        minX = ogX - 51;
        

        unhealthyFoodList = entityManager.getUnhealthyFoodList();
    }
    
    public void bossMovement() {
        int currDir = activeBoss.getDirection();
        float speed = activeBoss.getSpeed();
        float currBossX = activeBoss.getPositionX();
        // float currBossY
        if (currDir == leftDirection) {
            if (currBossX - speed < minX) {
                if (textureManager.getBossTextureDirection() == rightDirection) {
                    textureManager.flipBossTextures();
                }
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
                if (textureManager.getBossTextureDirection() == leftDirection) {
                    textureManager.flipBossTextures();
                }
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

    public void bossDie() {
        activeBoss.setStatus();
    }
}
