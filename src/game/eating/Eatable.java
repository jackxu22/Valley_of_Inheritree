package game.eating;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Interface for items that can be eaten by an Actor.
 */
public interface Eatable {
    /**
     * Performs the action of being eaten by an actor.
     * @param eater The actor eating the item.
     * @param map The map the actor is on.
     * @return A string describing the result of being eaten.
     */
    String eatenBy(Actor eater, GameMap map);

    /**
     * A short description of the eat action for display in a menu.
     * @param actor The actor who might eat this item.
     * @return String, e.g., "eats Omen Sheep Egg".
     */
    String getEatMenuDescription(Actor actor);
}