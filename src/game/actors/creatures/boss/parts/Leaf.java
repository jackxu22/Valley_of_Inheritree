package game.actors.creatures.boss.parts;

import edu.monash.fit2099.engine.actors.Actor;
import java.util.List;

/**
 * Represents a leaf part of a boss.
 * A Leaf contributes a small amount to the boss's damage and provides a healing effect to the boss
 * during its growth phase. A Leaf is a terminal part and does not grow further.
 *
 * @see BossPart
 * @see game.actors.creatures.boss.BedOfChaos
 */
public class Leaf implements BossPart {

    /**
     * Gets the damage contribution of this leaf.
     *
     * @return The fixed damage value of 1.
     */
    @Override
    public int getDamageContribution() {
        return 1;
    }

    /**
     * Executes the "growth" logic for this leaf.
     * Instead of growing new parts, a Leaf heals the main body of the boss.
     *
     * @param actor       The main body of the boss to be healed.
     * @param directParts The list of parts attached to the boss (not modified by this method).
     * @return A string describing the healing effect.
     */
    @Override
    public String grow(Actor actor, List<BossPart> directParts) {
        actor.heal(5);
        return actor + "is healed\n";
    }
}