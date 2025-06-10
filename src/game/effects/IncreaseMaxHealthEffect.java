package game.effects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * An {@link Effect} that increases an {@link Actor}'s maximum health points.
 * <p>
 * When this effect is applied, it calls the
 * {@link Actor#modifyAttributeMaximum(Enum, ActorAttributeOperations, int)} method on the target
 * actor, using {@link BaseActorAttributes#HEALTH} as the attribute and
 * {@link ActorAttributeOperations#INCREASE} as the operation. This permanently raises the actor's
 * maximum health by the specified amount. The actor's current health is typically set to the new
 * maximum health as part of the {@code modifyAttributeMaximum} logic in the engine's
 * {@link edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes}.
 * </p>
 */
public class IncreaseMaxHealthEffect implements Effect {

    /**
     * The amount by which the actor's maximum health will be increased.
     */
    private final int increaseAmount;

    /**
     * Constructs an {@code IncreaseMaxHealthEffect}.
     *
     * @param increaseAmount The amount to add to the actor's maximum health. Should be a
     *                       non-negative integer.
     */
    public IncreaseMaxHealthEffect(int increaseAmount) {
        this.increaseAmount = increaseAmount;
    }

    /**
     * Applies the effect to the specified actor, increasing their maximum health.
     *
     * @param actor The {@link Actor} whose maximum health will be increased. Must not be null and
     *              should have the {@link BaseActorAttributes#HEALTH} attribute.
     * @param map   The {@link GameMap} where the effect occurs. Currently not used by this specific
     *              effect but is part of the {@link Effect} interface.
     */
    public void applyEffect(Actor actor, GameMap map) {
        // Assuming Actor has a method to increase max health,
        // which delegates to modifyAttributeMaximum for the HEALTH attribute.
        actor.modifyAttributeMaximum(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE,
                increaseAmount);
    }
}
