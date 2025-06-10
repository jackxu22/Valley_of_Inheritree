package game.behaviours.behaviourselectors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;
import java.util.Map;

/**
 * Strategy interface for selecting behaviours for creatures. Different implementations can provide
 * different selection strategies.
 */
public interface BehaviourSelector {

    /**
     * Select and execute a behaviour from the given list of behaviours.
     *
     * @param behaviours the list of available behaviours
     * @param actor      the actor performing the behaviour
     * @param map        the current game map
     * @return the Action to be performed, or null if no valid action is found
     */
    Action selectBehaviour(Map<Integer, Behaviour> behaviours, Actor actor, GameMap map);
}