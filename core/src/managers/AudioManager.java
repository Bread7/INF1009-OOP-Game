package managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.ObjectMap;
import utils.Settings;

public class AudioManager implements Audio {
    private final AssetManager assetManager;
    private final ObjectMap<String, Sound> sounds;
    private final ObjectMap<String, Music> musics;
    private Music music;
    private float musicVolume = Settings.getMusicVolume();

    public AudioManager(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.sounds = new ObjectMap<>();
        this.musics = new ObjectMap<>();
    }


    @Override
    public void playSound(String fileName) {
        Sound sound = sounds.get(fileName);
        if (sound != null) {
            sound.play(musicVolume);
        }
    }

    @Override
    public void stopSound(String fileName) {
        Sound sound = sounds.get(fileName);
        if (sound != null) {
            sound.stop();
        }
    }

    @Override
    public void setMusicVolume(float volume) {
        Settings.changeMusicVolume(volume);
    }

    @Override
    public void playMusic(String fileName) {
        if (music != null) {
            music.stop();
            music.dispose();
        }
        music = assetManager.get(fileName, Music.class);
        music.setLooping(true);
        music.setVolume(musicVolume);
        music.play();
    }

    @Override
    public void stopMusic() {
        if (music != null) {
            music.stop();
            music.dispose();
            music = null;
        }
    }
}
