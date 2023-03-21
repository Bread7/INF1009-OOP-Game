package managers;

import Screens.*;
import com.mygdx.game.MyGdxGame;

import java.util.HashMap;

public class GameScreenManager {
    private final MyGdxGame app;
    private HashMap<STATE, BaseScreen> GameScreens;
    private STATE activeScreen;
    public enum STATE{
        MAIN_MENU,
        GAME_SCREEN,
        GAME_OVER,
        SETTING_SCREEN,
        HELP_SCREEN
    }
    public GameScreenManager(final MyGdxGame app)
    {
        this.app = app;
        initGameScreen(); //set up screens For Enum
        setScreen(STATE.MAIN_MENU); // starting screen will be menu
    }

    private void initGameScreen(){
        this.GameScreens = new HashMap<STATE, BaseScreen>();
        this.GameScreens.put(STATE.GAME_SCREEN, new GameScreen(app));
        this.GameScreens.put(STATE.MAIN_MENU, new MenuScreen(app));
        this.GameScreens.put(STATE.GAME_OVER, new GameOverScreen(app));
        this.GameScreens.put(STATE.SETTING_SCREEN, new SettingScreen(app));
        this.GameScreens.put(STATE.HELP_SCREEN, new HelpScreen(app));
    }

    public void setScreen(STATE nextScreen){
        app.setScreen(GameScreens.get(nextScreen));
        activeScreen = nextScreen;
    }

    public STATE getActiveScreen() {
        return activeScreen;
    }

    public void restartGameScreen() {
        this.GameScreens.remove(STATE.GAME_SCREEN);
        // this.GameScreens.remove(STATE.GAME_SCREEN, GameScreen(app));
        this.GameScreens.put(STATE.GAME_SCREEN, new GameScreen(app));
    }


    public void dispose(){
        for(BaseScreen screen : GameScreens.values()) {
            if (screen != null) {
                screen.dispose();
            }
        }
    }
}