package game.capabilities;

/**
 * An enumeration representing general capabilities or states that game entities
 * ({@link edu.monash.fit2099.engine.GameEntity}) can possess. These capabilities are used
 * throughout the game to determine interactions, resistances, specific behaviours, or to flag
 * entities for certain systems.
 * <p>
 * Examples of use:
 * <ul>
 * <li>{@code HOSTILE_TO_ENEMY}: Indicates an actor will attack other actors considered enemies.</li>
 * <li>{@code CAN_CURED}: Indicates an entity (actor or ground) can be targeted by a curing effect
 * (e.g., via {@link game.healing.CureAction}).</li>
 * <li>{@code FOLLOWABLE}: Indicates an actor can be a target for {@link game.behaviours.FollowBehaviour}.</li>
 * <li>{@code CONSUMABLE_ON_MAP}: Indicates an actor can be eaten directly from the map by another actor
 * with the {@code CONSUMER} capability.</li>
 * <li>{@code CONSUMER}: Indicates an actor has the ability to eat entities marked as {@code CONSUMABLE_ON_MAP}
 * or {@link game.eating.Eatable} items.</li>
 * <li>{@code CURSED}: Indicates an entity (actor or ground) is afflicted by a curse, potentially
 * triggering specific interactions or effects.</li>
 * <li>{@code CAN_SELL}: Indicates an NPC can offer items for sale.</li>
 * <li>{@code CAN_BUY}: Indicates an actor (typically the player) can purchase items from NPCs.</li>
 * <li>{@code BLESSED}: Indicates an entity possesses a blessed status, which might interact with
 * production behaviours (e.g., {@link game.actors.creatures.SpiritGoat}).</li>
 * </ul>
 *
 * @author Riordan D. Alfredo Modified by: GoeyQiHang
 */
public enum GeneralCapability {
    /**
     * Capability indicating an actor will attack actors designated as enemies.
     */
    HOSTILE_TO_ENEMY,
    /**
     * Capability indicating an entity (actor or ground) can be targeted by a cure effect and likely
     * implements the {@link game.healing.Curable} interface.
     */
    CAN_CURED,

    /**
     * Capability indicating an entity is blessed, which may influence certain game mechanics like
     * creature production.
     */
    BLESSED,

    /**
     * Capability indicating an actor can be followed by other actors (e.g., by an actor with
     * {@link game.behaviours.FollowBehaviour}).
     */
    FOLLOWABLE,
    /**
     * Capability indicating an actor can be consumed (eaten) directly from the game map by an actor
     * with the {@link #CONSUMER} capability.
     */
    CONSUMABLE_ON_MAP,

    /**
     * Capability indicating an actor can consume/eat other entities or items (e.g., those marked
     * {@link #CONSUMABLE_ON_MAP} or implementing {@link game.eating.Eatable}).
     */
    CONSUMER,
    /**
     * Capability indicating an entity (actor or ground) is cursed, which may trigger specific
     * interactions or environmental effects.
     */
    CURSED,
    /**
     * Capability indicating an NPC can sell items to other actors. Works in conjunction with
     * {@link #CAN_BUY}.
     */
    CAN_SELL,
    /**
     * Capability indicating an actor (usually the player) can buy items from NPCs who have the
     * {@link #CAN_SELL} capability.
     */
    CAN_BUY,
    /**
     * Capability indicating an actor (usually the player) can listen to monologues from NPCs.
     */
    CAN_LISTEN,
    /**
     * Capability indicating an actor can use teleportation, such as through a gate or spell.
     */
    CAN_TELEPORT,
    /**
     * Capability indicating an item or actor enables fishing actions.
     */
    CAN_FISH,

    /**
     * Capability indicating an item or actor enables digging actions.
     */
    CAN_DIG
}