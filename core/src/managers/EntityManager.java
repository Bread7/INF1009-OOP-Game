package managers;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.MyGdxGame;

import Entity.Characters.*;
import Entity.Objects.*;

import java.util.*;

import utils.MapBricks;
import utils.SurfaceAreaBox;

public class EntityManager implements SurfaceAreaBox, MapBricks {

    private final MyGdxGame app;

    // active and initialise for game
    private Player player;
    private Boss boss;
    private Podium podium;
    private Brick brick;
    private HealthFood healthFood;
    private UnhealthyFood unhealthyFood;

    private static List<Player> playerList;
    private static List<Boss> bossList;
    private static List<Brick> brickList;
    private static List<Podium> podiumList;
    private static List<HealthFood> healthFoodList;
    private static List<UnhealthyFood> unhealthyFoodList;

    public EntityManager(final MyGdxGame app) {
        this.app = app;
        
        playerList = new ArrayList<Player>();
        bossList = new ArrayList<Boss>();
        brickList = new ArrayList<Brick>();
        podiumList = new ArrayList<Podium>();
        healthFoodList = new ArrayList<HealthFood>();
        unhealthyFoodList = new ArrayList<UnhealthyFood>();
        createPlayer();
        createBoss();
        createItems();
        createStatics();
    }

    // Initialise entities

    private void createPlayer() {
        // set player direction to the right
        Rectangle collideBox = new Rectangle(20, 24, playerWidth, playerHeight);
        player = new Player(20, 24, collideBox, app.getInputManager().getRightDirection());
        playerList.add(player);
    }

    private void createBoss() {
        // set boss direction to random
        Rectangle collideBox = new Rectangle(180, 23, bossWidth, bossHeight);
        boss = new Boss(1030, 23, collideBox, app.getInputManager().randomDirection());
        bossList.add(boss);
    }

    private void createItems() {
        Rectangle unhealthyCollideBox = new Rectangle(521, 23, unhealthyFoodWidth, unhealthyFoodHeight);
        unhealthyFood = new UnhealthyFood(500, 23, unhealthyCollideBox, false, app.getInputManager().randomDirection(), 0.5f);
        unhealthyFoodList.add(unhealthyFood);

        Rectangle healthFoodCollideBox = new Rectangle(262, 25, healthyFoodWidth, healthyFoodHeight);
        healthFood = new HealthFood(250, 13, healthFoodCollideBox, false, 0.5f);
        healthFoodList.add(healthFood);
    }

    private void createStatics() {
        // base layer bricks and podiums
        float updateBrickX = initialBrickX;
        float updateBrickX2 = secondBrickX;
        float updatePodiumX = initialPodiumX;
        for (int i = 0; i < podiumQty; i++) {
            Rectangle collideBox = new Rectangle(updatePodiumX, defaultPodiumY, podiumWidth, podiumHeight);
            podium = new Podium(updatePodiumX, defaultPodiumY, collideBox, false);
            podiumList.add(podium);
            updatePodiumX += distance;
        }
        for (int i = 0; i < brickBaseQty; i++) {
            if (i > brickLayer1Qty + 1) {
                Rectangle collideBox = new Rectangle(updateBrickX2, defaultBrickY, brickWidth, brickHeight);
                brick = new Brick(updateBrickX2, defaultBrickY, collideBox, false);
                brickList.add(brick);
                updateBrickX2 += distance;
                continue;
            }
            Rectangle collideBox = new Rectangle(updateBrickX, defaultBrickY, brickWidth, brickHeight);
            brick = new Brick(updateBrickX, defaultBrickY, collideBox, false);
            brickList.add(brick);
            updateBrickX += distance;
        }

        // adding vertical bricks to form a maze
        updateBrickX = vertInitialBrickX;
        float updateBrickY = vertInitialBrickY;
        for (int i = 0; i < vertBrick1Qty; i++) {
            Rectangle collideBox = new Rectangle(vertInitialBrickX, updateBrickY, brickWidth, brickHeight);
            brick = new Brick(vertInitialBrickX, updateBrickY, collideBox, false);
            brickList.add(brick);
            updateBrickY += distance;
        }

        updateBrickY = vertInitialBrickY2;
        for (int i = 0; i < vertBrick2Qty; i++) {
            Rectangle collideBox = new Rectangle(vertInitialBrickX2, updateBrickY, brickWidth, brickHeight);
            brick = new Brick(vertInitialBrickX2, updateBrickY, collideBox, false);
            brickList.add(brick);
            updateBrickY += distance;
        }

        updateBrickY = vertInitialBrickY3;
        for (int i = 0; i < vertBrick3Qty; i++) {
            Rectangle collideBox = new Rectangle(vertInitialBrickX3, updateBrickY, brickWidth, brickHeight);
            brick = new Brick(vertInitialBrickX3, updateBrickY, collideBox, false);
            brickList.add(brick);
            updateBrickY += distance;
        }

        // adding ceiling bricks
        updateBrickX = ceilDefaultX;
        for (int i = 0; i < ceilBrickQty; i++) {
            Rectangle collideBox = new Rectangle(updateBrickX, ceilDefaultY, brickWidth, brickHeight);
            brick = new Brick(updateBrickX, ceilDefaultY, collideBox, false);
            brickList.add(brick);
            updateBrickX += distance;
        }
    }

    public UnhealthyFood createUnhealthyFood(float x, float y, int direction) {
        Rectangle collideBox = new Rectangle(x, y, unhealthyFoodWidth, unhealthyFoodHeight);
        UnhealthyFood badFood = new UnhealthyFood(x, y, collideBox, false, direction, 0.05f);
        unhealthyFoodList.add(badFood);
        return badFood;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public List<Boss> getBossList() {
        return bossList;
    }

    public List<Brick> getBrickList() {
        return brickList;
    }

    public List<Podium> getPodiumList() {
        return podiumList;
    }

    public List<HealthFood> getHealthFoodList() {
        return healthFoodList;
    }

    public List<UnhealthyFood> getUnhealthyFoodList() {
        return unhealthyFoodList;
    }

    public int activePlayerIndex() {
        if (player != null) {
            return playerList.indexOf(player);
        }
        return -1;
    }

    public int activeBossIndex() {
        if (boss != null) {
            return bossList.indexOf(boss);
        }
        return -1;
    }

    public HealthFood getHealthFood() {
        return healthFood;
    }

    public UnhealthyFood getUnhealthyFood() {
        return unhealthyFood;
    }
    
    public void restartEntity() {
        // refresh player
        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).getStatus()) {
                playerList.get(i).setStatus();
            }
            playerList.get(i).setHealth(3);
            playerList.get(i).setSpeed(3);
            playerList.get(i).setPositionX(20);
            playerList.get(i).setPositionY(24);
            playerList.get(i).getCollideBox().setX(playerList.get(i).getPositionX());
            playerList.get(i).getCollideBox().setY(playerList.get(i).getPositionY());
        }

        // refresh boss
        for (int i = 0; i < bossList.size(); i++) {
            if (bossList.get(i).getStatus()) {
                bossList.get(i).setStatus();
            }
        }

        // refresh podium
        for (int i = 0; i < podiumList.size(); i++) {
            if (podiumList.get(i).checkPodium()) {
                podiumList.get(i).standOnPodium();
            }
        }
    }
}

