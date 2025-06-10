package game.weapons;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.weapons.actions.AttackAction;
import java.util.Random;

/**
 * An abstract class representing items that can be used as a weapon.
 * It extends {@link Item} and implements the
 * {@link Weapon} interface from the game engine.
 * <p>
 * This class provides a base implementation for weapon items, including attributes
 * for damage, hit rate, and a verb for attack descriptions. It also includes a
 * default damage multiplier. Concrete weapon items should extend this class and
 * provide specific values for these attributes.
 * <p>
 * While this class provides an {@link #allowableActions(Actor, Location)} method,
 * standard melee attacks with a carried {@code WeaponItem} are often initiated
 * through the game's main loop )
 * by checking adjacent actors and then creating an {@link AttackAction} that can utilize
 * this weapon. The {@code allowableActions} method here is more for special cases where
 * the weapon itself might confer a directly targetable action (e.g., a ranged attack or a special skill).
 *
 * @author Adrian Kristanto (from the base engine)
 */
public abstract class WeaponItem extends Item implements Weapon {
    /**
     * The default damage multiplier if none is specifically set or modified.
     */
    private static final float DEFAULT_DAMAGE_MULTIPLIER = 1.0f;
    /**
     * The base damage this weapon inflicts on a successful hit.
     */
    private final int damage;
    /**
     * The probability (out of 100) that this weapon will hit its target.
     */
    private final int hitRate;
    /**
     * The verb used to describe this weapon's attack in messages (e.g., "hits", "slashes", "zaps").
     */
    private final String verb;
    /**
     * A multiplier applied to the base damage. Can be used for buffs, debuffs, or special effects.
     * Initialized to {@link #DEFAULT_DAMAGE_MULTIPLIER}.
     */
    private final float damageMultiplier;

    /**
     * Constructor for WeaponItem.
     * All weapon items are portable by default.
     *
     * @param name        The name of the weapon item (e.g., "Broadsword", "Katana").
     * @param displayChar The character used to represent this item when it's on the ground.
     * @param damage      The base amount of damage this weapon inflicts.
     * @param verb        The verb describing the attack (e.g., "slashes", "strikes").
     * @param hitRate     The chance (percentage, 0-100) for this weapon to hit its target.
     */
    public WeaponItem(String name, char displayChar, int damage, String verb, int hitRate) {
        super(name, displayChar, true); // Portable is true by default for WeaponItems
        this.damage = damage;
        this.verb = verb;
        this.hitRate = hitRate;
        this.damageMultiplier = DEFAULT_DAMAGE_MULTIPLIER;
    }

    /**
     * Performs an attack from an {@code attacker} Actor on a {@code target} Actor using this weapon.
     * <p>
     * The outcome of the attack is determined by the weapon's {@link #hitRate}.
     * If a random number (0-99) is less than the hit rate, the attack hits.
     * The damage dealt is calculated as {@code Math.round(damage * damageMultiplier)}.
     * The target's health is reduced using {@link Actor#hurt(int)}.
     * </p>
     *
     * @param attacker The {@link Actor} performing the attack.
     * @param target   The {@link Actor} being attacked.
     * @param map      The {@link GameMap} where the attack occurs (not directly used in this default implementation).
     * @return A string describing the outcome of the attack (e.g., "Attacker slashes Target for X damage."
     * or "Attacker misses Target.").
     */
    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {
        Random rand = new Random();
        if (!(rand.nextInt(100) < this.hitRate)) {
            return attacker + " misses " + target + ".";
        }

        // Calculate effective damage using the multiplier
        int effectiveDamage = Math.round(damage * damageMultiplier);
        target.hurt(effectiveDamage);

        // The damage in the message string refers to the base damage.
        return String.format("%s %s %s for %d damage", attacker, verb, target, damage);
    }

    /**
     * Returns a list of allowable actions that can be performed *by this weapon itself*
     * on {@code otherActor} at the given {@code location}.
     * <p>
     * This method is intended for weapons that might have special abilities or skills
     * that can be directly activated and targeted (e.g., a magic staff casting a spell,
     * or a ranged weapon firing at a specific location).
     * By default, it adds an {@link AttackAction} targeting the {@code otherActor} using this weapon.
     * The direction string in this {@link AttackAction} is empty, implying a direct targeting
     * rather than an attack initiated through an adjacent {@link edu.monash.fit2099.engine.positions.Exit}.
     * </p><p>
     * For standard melee attacks by an actor carrying this weapon, the {@link AttackAction}
     * is typically generated elsewhere, often by the game's main turn processing logic,
     * checks adjacent actors and uses the actor's equipped weapon or intrinsic weapon.
     * </p>
     *
     * @param otherActor The target {@link Actor}.
     * @param location   The {@link Location} of the {@code otherActor}. This parameter
     * is crucial if the weapon has actions that can target actors
     * at a specific location.
     * @return An {@link ActionList} which, by default, contains an {@link AttackAction}
     * allowing this weapon to be used against the {@code otherActor}.
     * Subclasses can override this to add more specific actions.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, Location location) {
        ActionList actions = new ActionList();
        // This allows the weapon, if it has such a direct use-case (e.g., a skill, or a ranged attack),
        // to initiate an attack on 'otherActor' at 'location'.
        // The direction is "" because this context isn't necessarily about attacking through an exit.
        actions.add(new AttackAction(otherActor, "", this));
        return actions;
    }
}
