package game.actors.npc;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import java.util.ArrayList;

/**
 * Interface for actors that can provide monologues.
 */
public interface Speakable {

    /**
     * Gets a list of monologues that this Speakable entity can say, potentially based on the
     * listener and the current game map state.
     *
     * @param listener The actor who is listening to.
     * @param map      The game map.
     * @return An ArrayList of Monologue objects.
     */
    ArrayList<Monologue> getMonologues(Actor listener, GameMap map);
}