package managers;

import com.mygdx.game.MyGdxGame;

import utils.MapBricks;
import utils.SurfaceAreaBox;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import java.util.*;

public class TextureManager implements MapBricks, SurfaceAreaBox{
    private final MyGdxGame app;

    private TextureAtlas bg, boss, food, player, tile;

    private static AtlasRegion bigCloud, sky, skyline, smallCloud;
    private static AtlasRegion bossWalk, bossDeath;
    private static AtlasRegion cabbage, soda;
    private static AtlasRegion playerWalk, playerHurt, playerIdle, playerDeath;
    private static AtlasRegion brick, podium;

    public static List<AtlasRegion> bgTextures;
    public static List<AtlasRegion> bossWalkTextures, bossDeathTextures;
    public static List<AtlasRegion> foodTextures;
    public static List<AtlasRegion> playerWalkTextures, playerHurtTextures, playerIdleTextures, playerDeathTextures;
    public static List<AtlasRegion> tileTextures, podiumTextures, brickTextures;
    // public static List<TextureRegion> playerIdleTextures;
    public static AtlasRegion test1, test2;


    public TextureManager(final MyGdxGame app) {
        this.app = app;

        // unload assets
        bg = this.app.getGenericAssetsManager().getAsset("Bg/bg.atlas", TextureAtlas.class);
        boss = this.app.getGenericAssetsManager().getAsset("Boss/boss.atlas", TextureAtlas.class);
        food = this.app.getGenericAssetsManager().getAsset("Food/food.atlas", TextureAtlas.class);
        player = this.app.getGenericAssetsManager().getAsset("Player/player.atlas", TextureAtlas.class);
        tile = this.app.getGenericAssetsManager().getAsset("Tile/tile.atlas", TextureAtlas.class);

        loadBackgroundTextures();
        loadBossTextures();
        loadFoodTextures();
        loadPlayerTextures();
        loadTileTextures();
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
                // bossWalkDup = null;
            }
            x = 0;
            y = 0;
            w = 0;
            h = 0;
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
                // bossDeathDup = null;
            }
            x = 0;
            y = 0;
            w = 0;
            h = 0;
        }
        
        

    }

    private void loadFoodTextures() {
        if (foodTextures == null) {
            foodTextures = new ArrayList<AtlasRegion>();
            cabbage = food.findRegion("Cabbage");
            soda = food.findRegion("Soda");
            foodTextures.add(cabbage);
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
                // System.out.print("height: ");
                // System.out.println(copy.getRegionHeight());
                playerWalkTextures.add(playerWalkDup);
                x += 48;
            }
            x = 0;
            y = 0;
            w = 0;
            h = 0;
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
            x = 0;
            y = 0;
            w = 0;
            h = 0;
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
            x = 0;
            y = 0;
            w = 0;
            h = 0;
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
            x = 0;
            y = 0;
            w = 0;
            h = 0;
        }
        
        
    }

    private void loadTileTextures() {
        // if (tileTextures == null) {
        //     tileTextures = new ArrayList<AtlasRegion>();
        //     brick = tile.findRegion("Brick");
        //     podium = tile.findRegion("Podium");
        //     tileTextures.add(brick);
        //     tileTextures.add(podium); 
        // }
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

}
