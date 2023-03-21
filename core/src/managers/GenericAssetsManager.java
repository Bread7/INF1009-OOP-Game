package managers;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GenericAssetsManager{

    public AssetManager manager;

    //public static final AssetDescriptor<Skin> uiskin = new AssetDescriptor<Skin>("uiskin.json", Skin.class,new SkinLoader.SkinParameter("uiskin.atlas"));

    public GenericAssetsManager(AssetManager manager) {
        this.manager = manager;
    }

    public <T> void loadAsset(String fileName, Class<T> type) {
        manager.load(fileName, type);
    }

    public <T> void loadAsset(String fileName, Class<T> type, Object parameter) {
        if (type == Texture.class) {
            manager.load(fileName, type);
        } else if (type == TextureAtlas.class) {
            if (parameter instanceof String) {
                manager.load(fileName, type);
            } else if (parameter instanceof TextureAtlas.AtlasRegion) {
                TextureAtlas.AtlasRegion region = (TextureAtlas.AtlasRegion) parameter;
                manager.load(region.name, Texture.class);
            }
        } else if (type == Skin.class) {
            if (parameter instanceof SkinLoader.SkinParameter) {
                manager.load(fileName, type, (AssetLoaderParameters<T>) parameter);
            } else {
                manager.load(fileName, type);
            }
        } else if (type == TiledMap.class) {
            manager.load(fileName, type);
        } else if (type == Sound.class) {
            manager.load(fileName, type);
        } else if (type == Music.class) {
            manager.load(fileName, type);
        }
    }

    public boolean isLoaded(String fileName) {
        return manager.isLoaded(fileName);
    }

    public <T> T getAsset(String fileName, Class<T> type) {
        return manager.get(fileName, type);
    }
    public void loadAll()
    {
       // manager.load(uiskin);
        //manager.load("theme_music.ogg", Music.class);
    }

    public void load_gameOver()
    {
        manager.load("skins/clean-crispy-ui.json", Skin.class);
        manager.load("Sound/gameOver.mp3",Sound.class);
    }

    public void load_help()
    {
        manager.load("skins/clean-crispy-ui.json", Skin.class);
        manager.load("images/help-1.png",Texture.class);
        manager.load("images/help-2.png",Texture.class);
        manager.load("images/help-3.png",Texture.class);
    }
    public void dispose()
    {
        manager.dispose();
    }
}
