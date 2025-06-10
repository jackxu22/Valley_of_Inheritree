package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.GeneralCapability;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A behaviour that allows an {@link Actor} to follow a target {@link Actor}. The target is
 * dynamically found if not already set or if the current target becomes invalid (e.g., moves off
 * map, becomes unconscious, or loses the {@link GeneralCapability#FOLLOWABLE} capability). This
 * behaviour will attempt to move the actor one step closer to the target if a path that reduces the
 * Manhattan distance is available and the actor is not already adjacent to the target.
 */
public class FollowBehaviour implements Behaviour {

    /**
     * The current target Actor being followed. This can be null if no target is set or found.
     */
    private Actor currentTarget = null; // The actor being currently followed

    /**
     * Determines an action for the actor to follow its target.
     * <p>
     * The logic is as follows: 1. Validate the {@link #currentTarget}: - If a target exists, check
     * if it's still on the map, conscious, and has the {@link GeneralCapability#FOLLOWABLE}
     * capability. If not, the target is set to null. 2. Find a new target if none exists: - Scans
     * adjacent locations (exits from the actor's current location). - If an actor is found in an
     * adjacent location who is conscious and has the {@link GeneralCapability#FOLLOWABLE}
     * capability, they become the {@link #currentTarget}. The first such actor found is chosen. 3.
     * Move towards the target if one exists: - Calculates the Manhattan distance to the target. -
     * If the actor is already adjacent to the target, no move action is returned (returns null). -
     * Otherwise, it evaluates all possible moves to adjacent, enterable locations. - It selects a
     * move that strictly reduces the Manhattan distance to the target. - To avoid biased movement
     * when multiple paths offer the same best distance, exits are shuffled. - Returns a
     * {@link MoveActorAction} for the preferred move.
     * <p>
     * If no target is found, or no move can reduce the distance to the current target, or the actor
     * is already adjacent, this method returns null.
     *
     * @param actor the {@link Actor} enacting the behaviour.
     * @param map   the {@link GameMap} containing the actor.
     * @return a {@link MoveActorAction} to move closer to the target, or null if no suitable action
     * is found.
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        // 1. Validate current target
        if (currentTarget != null && (!map.contains(currentTarget) || !currentTarget.isConscious()
                || !currentTarget.hasCapability(GeneralCapability.FOLLOWABLE))) {
            currentTarget = null; // Invalidate target
        }

        // 2. If no current target, try to find a new one in the surroundings
        if (currentTarget == null) {
            Location here = map.locationOf(actor);
            // Check adjacent tiles for a followable actor
            for (Exit exit : here.getExits()) {
                Location destination = exit.getDestination();
                if (map.isAnActorAt(destination)) {
                    Actor potentialTarget = map.getActorAt(destination);
                    if (potentialTarget.hasCapability(GeneralCapability.FOLLOWABLE)
                            && potentialTarget.isConscious()) {
                        currentTarget = potentialTarget; // Found a new target
                        break; // Follow the first one found
                    }
                }
            }
        }

        // 3. If a valid target exists, attempt to move closer
        if (currentTarget != null) {
            Location here = map.locationOf(actor);
            Location there = map.locationOf(currentTarget);

            int currentDistance = distance(here, there);
            // Stop trying to move closer if already next to target or at a distance of 1.
            // Does not move if on top of the target (here.equals(there) would be true).
            if (currentDistance <= 1 && !here.equals(there)) {
                return null;
            }

            Action preferredAction = null;
            int bestDistance = currentDistance;

            // Shuffle exits to avoid biased movement when multiple paths have the same best distance
            List<Exit> exits = new ArrayList<>(here.getExits());
            Collections.shuffle(exits); // Randomize order of checking exits

            for (Exit exit : exits) {
                Location destination = exit.getDestination();
                if (destination.canActorEnter(
                        actor)) { // Check if the actor can move to the destination
                    int newDistance = distance(destination, there);
                    if (newDistance < bestDistance) { // Strictly move closer
                        bestDistance = newDistance;
                        // Use MoveActorAction from engine.actions
                        preferredAction = new MoveActorAction(destination, exit.getName());
                    }
                }
            }
            return preferredAction; // This will be null if no path strictly reduces distance
        }

        return null; // No target or no way to move closer
    }

    /**
     * Compute the Manhattan distance between two locations. The Manhattan distance is the sum of
     * the absolute differences of their Cartesian coordinates.
     *
     * @param a the first {@link Location}.
     * @param b the second {@link Location}.
     * @return the number of steps between a and b if movement is restricted to the four cardinal
     * directions (horizontal and vertical).
     */
    private int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

}