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
        Rectangle collideBox = new Rectangle(300, 23, unhealthyFoodWidth, unhealthyFoodHeight);
        unhealthyFood = new UnhealthyFood(300, 23, collideBox, false, app.getInputManager().randomDirection(), 0.5f);
        unhealthyFoodList.add(unhealthyFood);
        collideBox = new Rectangle(150, 13, healthyFoodWidth, healthyFoodHeight);
        healthFood = new HealthFood(150, 13, collideBox, false, 0.5f);
        healthFoodList.add(healthFood);
    }

    private void createStatics() {
        float updateBrickX = initialBrickX;
        float updateBrickX2 = secondBrickX;
        float updatePodiumX = initialPodiumX;
        for (int i = 0; i < podiumQty; i++) {
            Rectangle collideBox = new Rectangle(updatePodiumX, defaultPodiumY, podiumWidth, podiumHeight);
            podium = new Podium(updatePodiumX, defaultPodiumY, collideBox, false);
            podiumList.add(podium);
            updatePodiumX += distance;
        }
        for (int i = 0; i < brickQty; i++) {
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
        
}

