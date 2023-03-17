package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Settings {
    private static String moveLeftKey;
    private static String moveRightKey;
    private static String jumpKey;
    private static String downKey;
    private static float musicVolume;
    private static int highestScore;
    private static String shootKey;

    private static Preferences prefs = Gdx.app.getPreferences("Warrior Preferences");

    static {
        moveLeftKey = prefs.getString("moveLeftKey", "A");
        moveRightKey = prefs.getString("moveRightKey", "D");
        jumpKey = prefs.getString("jumpKey", "W");
        downKey = prefs.getString("moveDownKey","S");
        highestScore = prefs.getInteger("highestScore", 0);
        musicVolume = prefs.getFloat("musicVolume", 0.5f);
    }

    private Settings() {}

    public static float getMusicVolume() {
        return musicVolume;
    }

    public static void changeMusicVolume(float newMusicVolume) {
        musicVolume = newMusicVolume;
        prefs.putFloat("musicVolume", newMusicVolume);
        prefs.flush();
    }

    public static String getMoveLeftKey() {
        return moveLeftKey;
    }

    public static void setMoveLeftKey(String newMoveLeftKey) {
        moveLeftKey = newMoveLeftKey.toUpperCase();
        prefs.putString("moveLeftKey", moveLeftKey);
        prefs.flush();
    }

    public static String getMoveRightKey() {
        return moveRightKey;
    }

    public static void setMoveRightKey(String newMoveRightKey) {
        moveRightKey = newMoveRightKey.toUpperCase();
        prefs.putString("moveRightKey", moveRightKey);
        prefs.flush();
    }

    public static String getJumpKey() {
        return jumpKey;
    }

    public static void setDownKey(String newDownKey) {
        downKey = newDownKey.toUpperCase();
        prefs.putString("downKey", downKey);
        prefs.flush();
    }

    public static String getDownKey() {
        return downKey;
    }

    public static void setJumpKey(String newJumpKey) {
        jumpKey = newJumpKey.toUpperCase();
        prefs.putString("jumpKey", jumpKey);
        prefs.flush();
    }


    public static int getHighestScore() {
        return highestScore;
    }

    public static void setHighestScore(int newHighestScore) {
        highestScore = newHighestScore;
        // only when the current score higher than the Pref's higherscore it will replace it
        if(highestScore < newHighestScore) {
            prefs.putInteger("highestScore", newHighestScore);
            prefs.flush();
        }
    }
}