package managers;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.MyGdxGame;

import Behaviours.PlayerBehaviour;
import Entity.Characters.*;
import Entity.Objects.*;
import java.util.*;

// public interface CollisionManager {
// public void handlecollision(float x1, float y1, float x2, float y2);
// public void reacttocollision();
// }

public class CollisionManager {

    private final MyGdxGame app;

    private List<Brick> brickList;
    private List<Podium> podiumList;
    private List<HealthFood> healthFoodList;
    private List<UnhealthyFood> unhealthyFoodList;

    private PlayerBehaviour playerBehaviour;

    private Rectangle activePlayerArea;
    private Rectangle activeBossArea;

    private Player activePlayer;
    private Boss activeBoss;
    private UnhealthyFood eatUnhealthyFood;
    private HealthFood eatHealthyFood;
    private Podium podium;

    public CollisionManager(final MyGdxGame app) {
        this.app = app;
        // activePlayerArea = new Rectangle();
        // activeBossArea = new Rectangle();
        Initialise();
    }

    public void Initialise() {
        activePlayer = app.getEntityManager().getPlayerList().get(app.getEntityManager().activePlayerIndex());
        activePlayerArea = activePlayer.getCollideBox();
        activeBoss = app.getEntityManager().getBossList().get(app.getEntityManager().activeBossIndex());
        activeBossArea = activeBoss.getCollideBox();
        brickList = app.getEntityManager().getBrickList();
        healthFoodList = app.getEntityManager().getHealthFoodList();
        unhealthyFoodList = app.getEntityManager().getUnhealthyFoodList();
        podiumList = app.getEntityManager().getPodiumList();
        playerBehaviour = app.getBehaviourManager().getPlayerBehaviour();
    }

    public boolean collisionCheck(Rectangle inputArea, Rectangle compareArea) {
        Rectangle area = new Rectangle();

        if (Intersector.intersectRectangles(inputArea, compareArea, area)) {
            if (area.overlaps(inputArea)) {
                return true;
            }
        }
        return false;
    }

    // public boolean playerCollisionCheck() {

    // }

    public boolean playerCollideBrick() {
        for (int i = 0; i < brickList.size(); i++) {
            Rectangle brickArea = brickList.get(i).getCollideBox();
            boolean collided = collisionCheck(activePlayerArea, brickArea);
            // System.out.print(bool);
            if (collided) {
                // System.out.print("collision with brick ");
                return true;
            }
        }
        return false;
    }

    public boolean playerCollidePodium() {
        for (int i = 0; i < podiumList.size(); i++) {
            Rectangle podiumArea = podiumList.get(i).getCollideBox();
            boolean collided = collisionCheck(activePlayerArea, podiumArea);
            if (collided) {
                // System.out.print("collision with podium ");
                podium = podiumList.get(i);
                return true;
            }
        }
        // for (int i = 0;)
        return false;
    }

    public boolean playerCollideBoss() {
        if (activeBossArea != null && activePlayerArea != null) {
            boolean collided = collisionCheck(activePlayerArea, activeBossArea);
            if (collided) {
                // System.out.print("collision with boss ");
                return true;
            }
        }
        return false;
    }

    public boolean playerCollideUnhealthyFood() {
        for (int i = 0; i < unhealthyFoodList.size(); i++) {
            Rectangle unhealthyFoodArea = unhealthyFoodList.get(i).getCollideBox();
            boolean collided = collisionCheck(activePlayerArea, unhealthyFoodArea);
            if (collided) {
                // System.out.print("collision with soda ");
                eatUnhealthyFood = unhealthyFoodList.get(i);
                return true;
            }
        }
        return false;
    }

    public boolean playerCollideHealthFood() {
        for (int i = 0; i < healthFoodList.size(); i++) {
            Rectangle healthFoodArea = healthFoodList.get(i).getCollideBox();
            boolean collided = collisionCheck(activePlayerArea, healthFoodArea);
            if (collided) {
                // System.out.print("collision with cabbage ");
                eatHealthyFood = healthFoodList.get(i);
                return true;
            }
        }
        return false;
    }

    public void fullPlayerCollisionCheck() {
        if (playerCollideBoss()) {
            playerBehaviour.playerTouchBoss(activeBoss);
        }
        if (playerCollideBrick()) {
            playerBehaviour.evadeBrick();
        }
        if (playerCollideHealthFood()) {
            playerBehaviour.consumeHealthFood(eatHealthyFood);
            eatHealthyFood = null;
        }
        if (playerCollideUnhealthyFood()) {
            playerBehaviour.consumeUnhealthyFood(eatUnhealthyFood);
            eatUnhealthyFood = null;
        }
        if (playerCollidePodium()) {
            playerBehaviour.winGame(podium);
            podium = null;

        }
    }

}
