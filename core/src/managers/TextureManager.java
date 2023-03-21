package managers;

import com.mygdx.game.MyGdxGame;

import utils.InputKeysConstants;
import utils.MapBricks;
import utils.SurfaceAreaBox;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import java.util.*;

public class TextureManager implements MapBricks, SurfaceAreaBox, InputKeysConstants{
    private final MyGdxGame app;

    private TextureAtlas bg, boss, food, player, tile, heart;

    private static AtlasRegion bigCloud, sky, skyline, smallCloud;
    private static AtlasRegion bossWalk, bossDeath;
    private static AtlasRegion cabbage, soda;
    private static AtlasRegion playerWalk, playerHurt, playerIdle, playerDeath;
    private static AtlasRegion brick, podium;
    private static AtlasRegion hudHeart;

    public static List<AtlasRegion> bgTextures;
    public static List<AtlasRegion> bossWalkTextures, bossDeathTextures;
    public static List<AtlasRegion> foodTextures;
    public static List<AtlasRegion> playerWalkTextures, playerHurtTextures, playerIdleTextures, playerDeathTextures;
    public static List<AtlasRegion> tileTextures, podiumTextures, brickTextures;
    public static List<AtlasRegion> heartTextures;
    public static List<AtlasRegion> speedTextures;

    public TextureManager(final MyGdxGame app) {
        this.app = app;

        // unload assets
        bg = this.app.getGenericAssetsManager().getAsset("Bg/bg.atlas", TextureAtlas.class);
        boss = this.app.getGenericAssetsManager().getAsset("Boss/boss.atlas", TextureAtlas.class);
        food = this.app.getGenericAssetsManager().getAsset("Food/food.atlas", TextureAtlas.class);
        player = this.app.getGenericAssetsManager().getAsset("Player/player.atlas", TextureAtlas.class);
        tile = this.app.getGenericAssetsManager().getAsset("Tile/tile.atlas", TextureAtlas.class);
        heart = this.app.getGenericAssetsManager().getAsset("Heart/heart.atlas", TextureAtlas.class);

        loadBackgroundTextures();
        loadBossTextures();
        loadFoodTextures();
        loadPlayerTextures();
        loadTileTextures();
        loadHeartTextures();
    }

    private void loadBackgroundTextures() {
        if (bgTextures == null) {
            bgTextures = new ArrayList<AtlasRegion>();
            bigCloud = bg.findRegion("Big_Cloud");
            sky = bg.findRegion("Sky");
            skyline = bg.findRegion("Skyline");
            smallCloud = bg.findRegion("Small_Cloud");
            bgTextures.add(sky);
            bgTextures.add(skyline);
            bgTextures.add(bigCloud);
            bgTextures.add(smallCloud);
        }
    }

    private void loadBossTextures() {
        int x, y, w, h;
        if (bossWalkTextures == null) {
            x = 0;
            y = 29;
            w = 71;
            h = 43;
            bossWalk = boss.findRegion("Boss_Walk");
            bossWalkTextures = new ArrayList<AtlasRegion>();
            for (int i = 0; i < 4; i++) {
                AtlasRegion bossWalkDup = new AtlasRegion(bossWalk);
                bossWalkDup.setRegion(bossWalkDup, x, y, w, h);
                bossWalkTextures.add(bossWalkDup);
                x += 72;
            }
        }

        if (bossDeathTextures == null) {
            x = 0;
            y = 29;
            w = 71;
            h = 43;
            bossDeath = boss.findRegion("Boss_Death");
            bossDeathTextures = new ArrayList<AtlasRegion>();
            for (int i = 0; i < 6; i++) {
                AtlasRegion bossDeathDup = new AtlasRegion(bossDeath);
                bossDeathDup.setRegion(bossDeathDup, x, y, w, h);
                bossDeathTextures.add(bossDeathDup);
                x += 72;
            }
        }
        
        

    }

    private void loadFoodTextures() {
        if (foodTextures == null) {
            foodTextures = new ArrayList<AtlasRegion>();
            cabbage = food.findRegion("Cabbage");
            soda = food.findRegion("Soda");
            foodTextures.add(cabbage);
            foodTextures.add(soda);
            foodTextures.add(soda);
            foodTextures.add(soda);
            foodTextures.add(soda);
            foodTextures.add(soda);
        }
        
    }

    private void loadPlayerTextures() {
        int x, y, w, h;
        
        // initialise individual player walking textures
        if (playerWalk == null) {
            x = 3;
            y = 21;
            w = 21;
            h = 27;
            playerWalk = player.findRegion("Player_Walk");
            playerWalkTextures = new ArrayList<AtlasRegion>();
            for (int i = 0; i < 6; i++) {
                AtlasRegion playerWalkDup = new AtlasRegion(playerWalk);
                playerWalkDup.setRegion(playerWalkDup, x, y, w, h);
                playerWalkTextures.add(playerWalkDup);
                x += 48;
            }
        }

        if (playerDeath == null) {
            x = 3;
            y = 22;
            w = 16;
            h = 26;
            playerDeath = player.findRegion("Player_Death");
            playerDeathTextures = new ArrayList<AtlasRegion>();
            for (int i = 0; i < 4; i++) {
                AtlasRegion playerDeathDup = new AtlasRegion(playerDeath);
                playerDeathDup.setRegion(playerDeathDup, x, y, w, h);
                playerDeathTextures.add(playerDeathDup);
                if (i == 0) {
                    x += 48;
                    continue;
                }
                x += 49;
            }
        }

        if (playerIdle == null) {
            x = 3;
            y = 22;
            w = 16;
            h = 26;
            playerIdle = player.findRegion("Player_Idle");
            playerIdleTextures = new ArrayList<AtlasRegion>();
            for (int i = 0; i < 4; i++) {
                AtlasRegion playerIdleDup = new AtlasRegion(playerIdle);
                playerIdleDup.setRegion(playerIdleDup, x, y, w, h);
                playerIdleTextures.add(playerIdleDup);
                x += 48;
            }
        }
        
        if (playerHurt == null) {
            x = 1;
            y = 22;
            w = 18;
            h = 26;
            playerHurt = player.findRegion("Player_Hurt");
            playerHurtTextures = new ArrayList<AtlasRegion>();
            for (int i = 0; i < 2; i++) {
                AtlasRegion playerHurtDup = new AtlasRegion(playerHurt);
                playerHurtDup.setRegion(playerHurtDup, x, y, w, h);
                playerHurtTextures.add(playerHurtDup);
                x += 50;
            }
        }
        
        
    }

    private void loadTileTextures() {
        if (podiumTextures == null) {
            podiumTextures = new ArrayList<AtlasRegion>();
            for (int i = 0; i < podiumQty; i++) {
                podium = tile.findRegion("Podium");
                AtlasRegion anotherPodium = new AtlasRegion(podium);
                podiumTextures.add(anotherPodium);
            }
        }
        if (brickTextures == null) {
            brickTextures = new ArrayList<AtlasRegion>();
            for (int i = 0; i < brickQty; i++) {
                brick = tile.findRegion("Brick");
                AtlasRegion anotherBrick = new AtlasRegion(brick);
                brickTextures.add(anotherBrick);
            }
        }
    }

    private void loadHeartTextures() {
        if (heartTextures == null) {
            heartTextures = new ArrayList<AtlasRegion>();
            hudHeart = heart.findRegion("heart");
            for (int i = 0; i < 5; i++) {
                AtlasRegion fullHeart = new AtlasRegion(hudHeart);
                fullHeart.setRegion(fullHeart, 1, 3, 15, 14);
                heartTextures.add(fullHeart);
            }
        }
    }

    public List<AtlasRegion> getBgTextures() {
        return bgTextures;
    }

    public List<AtlasRegion> getBossWalkTextures() {
        return bossWalkTextures;
    }

    public List<AtlasRegion> getBossDeathTextures() {
        return bossDeathTextures;
    }

    public List<AtlasRegion> getFoodTextures() {
        return foodTextures;
    }

    public List<AtlasRegion> getPlayerWalkTextures() {
        return playerWalkTextures;
    }

    public List<AtlasRegion> getPlayerHurtTextures() {
        return playerHurtTextures;
    }

    public List<AtlasRegion> getPlayerIdleTextures() {
        return playerIdleTextures;
    }

    public List<AtlasRegion> getPlayerDeathTextures() {
        return playerDeathTextures;
    }

    public List<AtlasRegion> getTileTextures() {
        return tileTextures;
    }

    public List<AtlasRegion> getBrickTextures() {
        return brickTextures;
    }

    public List<AtlasRegion> getPodiumTextures() {
        return podiumTextures;
    }

    public List<AtlasRegion> getHeartTextures() {
        return heartTextures;
    }

    public void flipBossTextures() {
        for (int i = 0; i < bossDeathTextures.size(); i++) {
            if (bossDeathTextures.get(i).isFlipX() == false) {
                bossDeathTextures.get(i).flip(true, false);
            } else {
                bossDeathTextures.get(i).flip(false, false);
            }
        }
        for (int i = 0; i < bossWalkTextures.size(); i++) {
            if (bossWalkTextures.get(i).isFlipX() == false) {
                bossWalkTextures.get(i).flip(true, false);
            } else {
                bossWalkTextures.get(i).flip(true, false);
            }
        }
    }

    public void flipPlayerTextures() {
        for (int i = 0; i < playerDeathTextures.size(); i++) {
            if (playerDeathTextures.get(i).isFlipX() == false) {
                playerDeathTextures.get(i).flip(true, false);
            } else {
                playerDeathTextures.get(i).flip(true, false);
            }
        }
        for (int i = 0; i < playerHurtTextures.size(); i++) {
            if (playerHurtTextures.get(i).isFlipX() == false) {
                playerHurtTextures.get(i).flip(true, false);
            } else {
                playerHurtTextures.get(i).flip(true, false);
            }
        }
        for (int i = 0; i < playerIdleTextures.size(); i++) {
            if (playerIdleTextures.get(i).isFlipX() == false) {
                playerIdleTextures.get(i).flip(true, false);
            } else {
                playerIdleTextures.get(i).flip(true, false);
            }
        }
        for (int i = 0; i < playerWalkTextures.size(); i++) {
            if (playerWalkTextures.get(i).isFlipX() == false) {
                playerWalkTextures.get(i).flip(true, false);
            } else {
                playerWalkTextures.get(i).flip(true, false);
            }
        }
    }

    public int getBossTextureDirection() {
        if (bossDeathTextures.get(0).isFlipX() == false || bossWalkTextures.get(0).isFlipX() == false) {
            return leftDirection;
        }
        if (bossDeathTextures.get(0).isFlipX() == true || bossWalkTextures.get(0).isFlipX() == true) {
            return rightDirection;
        }
        return rightDirection;
    }

    public int getPlayerTextureDirection() {
        if (playerDeathTextures.get(0).isFlipX() == false || playerHurtTextures.get(0).isFlipX() == false ||
        playerIdleTextures.get(0).isFlipX() == false || playerWalkTextures.get(0).isFlipX() == false) {
            return rightDirection;
        }
        if (playerDeathTextures.get(0).isFlipX() == true || playerHurtTextures.get(0).isFlipX() == true ||
        playerIdleTextures.get(0).isFlipX() == true || playerWalkTextures.get(0).isFlipX() == true) {
            return leftDirection;
        }
        return rightDirection;
    }
}
