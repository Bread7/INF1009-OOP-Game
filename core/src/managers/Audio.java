package managers;

public interface Audio {

    void playSound(String fileName);

    void stopSound(String fileName);

    void setMusicVolume(float volume);


    void playMusic(String fileName);

    void stopMusic();
}
