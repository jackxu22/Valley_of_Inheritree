package game.spells;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.teleport.TeleportAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A SpellBook that allows the caster to teleport to a random location.
 * <p>
 * When cast, this spell scans the entire current map for valid, unoccupied tiles
 * that the caster can enter. It then teleports the caster to one of these locations
 * at random. The spell is always available for self-casting.
 *
 * @see SpellBook
 * @see CastSpellAction
 * @see TeleportAction
 */
public class TeleportSpell extends SpellBook {

    /**
     * The name of the spell.
     */
    private static final String NAME = "Teleport Spell";

    /**
     * The mana cost required to cast this spell.
     */
    private static final int MANA_COST = 20;

    /**
     * A description of the spell's effects for display in the game.
     */
    private static final String DESCRIPTION = "Teleports the caster to a random valid location on the current map.";

    /**
     * A static random number generator to select a random teleport location.
     */
    private static final Random random = new Random();

    /**
     * Constructor for the TeleportSpell.
     * Initializes the spell with its predefined name, display character, mana cost, and description.
     */
    public TeleportSpell() {
        super(NAME, 't', MANA_COST, DESCRIPTION);
    }

    /**
     * Activates the teleport spell's effect.
     * <p>
     * This method finds all possible locations on the map that the caster can legally move to.
     * If valid locations are found, it selects one at random and executes a {@link TeleportAction}
     * to move the caster there. The 'target' parameter is expected to be the caster.
     *
     * @param caster The actor casting the spell.
     * @param map    The GameMap where the spell is being cast.
     * @param target The target of the spell (which should be the caster for this spell).
     * @return A string describing the outcome of the teleport attempt.
     */
    @Override
    public String activate(Actor caster, GameMap map, Actor target) {
        Location currentLocation = map.locationOf(target);
        if (currentLocation == null) {
            return target + " is not on any map to teleport from.";
        }
        GameMap currentMap = map; // The map the actor is currently on.

        // Find all possible locations the actor can teleport to.
        List<Location> validTeleportLocations = new ArrayList<>();
        for (int y : currentMap.getYRange()) {
            for (int x : currentMap.getXRange()) {
                Location potentialLocation = currentMap.at(x, y);
                // A location is valid if it's not the current spot and the actor can enter it.
                if (potentialLocation != currentLocation && potentialLocation.canActorEnter(target)) {
                    validTeleportLocations.add(potentialLocation);
                }
            }
        }

        if (validTeleportLocations.isEmpty()) {
            return target + " could not find a valid new location to teleport to.";
        }

        // Pick a random destination from the list of valid locations.
        Location randomDestinationLocation = validTeleportLocations.get(random.nextInt(validTeleportLocations.size()));

        // Create and execute a TeleportAction to move the actor.
        TeleportAction internalTeleportAction = new TeleportAction(randomDestinationLocation);
        return internalTeleportAction.execute(target, currentMap);
    }

    /**
     * Provides a list of actions that can be performed with this spell.
     * <p>
     * This method always adds a {@link CastSpellAction} to the list, with the caster
     * set as the target. This ensures the Teleport Spell is always available for self-use.
     *
     * @param caster The actor holding the spellbook.
     * @param map    The current GameMap.
     * @return An ActionList containing the action to cast the teleport spell on oneself.
     */
    @Override
    public ActionList allowableActions(Actor caster, GameMap map) {
        ActionList actions = super.allowableActions(caster, map); // Gets DropItemAction etc. from Item class
        actions.add(new CastSpellAction(this, caster));
        return actions;
    }
}