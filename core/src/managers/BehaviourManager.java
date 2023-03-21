package managers;

import com.mygdx.game.MyGdxGame;

import Behaviours.NPCBehaviour;
import Behaviours.PlayerBehaviour;

public class BehaviourManager {

    private final MyGdxGame app;
    public static PlayerBehaviour playerBehaviour;
    public static NPCBehaviour npcBehaviour;

    public BehaviourManager(final MyGdxGame app) {
        this.app = app;

        playerBehaviour = new PlayerBehaviour(this.app);
        npcBehaviour = new NPCBehaviour(this.app);
    }

    public PlayerBehaviour getPlayerBehaviour() {
        return playerBehaviour;
    }

    public NPCBehaviour getNPCBehaviour() {
        return npcBehaviour;
    }


}
