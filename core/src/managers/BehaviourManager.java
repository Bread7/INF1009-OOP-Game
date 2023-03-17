package managers;

import com.mygdx.game.MyGdxGame;

import Behaviours.NPCBehaviour;
import Behaviours.PlayerBehaviour;
import Behaviours.ScreenBehaviour;

public class BehaviourManager {

    private final MyGdxGame app;
    public static PlayerBehaviour playerBehaviour;
    public static NPCBehaviour npcBehaviour;
    public static ScreenBehaviour screenBehaviour;

    public BehaviourManager(final MyGdxGame app) {
        this.app = app;

        playerBehaviour = new PlayerBehaviour(this.app);
        npcBehaviour = new NPCBehaviour(this.app);
        // screenBehaviour = new ScreenBehaviour(this.app);
    }

    public PlayerBehaviour getPlayerBehaviour() {
        return playerBehaviour;
    }

    public NPCBehaviour getNPCBehaviour() {
        return npcBehaviour;
    }

    public ScreenBehaviour getScreenBehaviour() {
        return screenBehaviour;
    }


}
