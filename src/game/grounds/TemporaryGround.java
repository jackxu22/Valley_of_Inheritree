package game.grounds;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;

/**
 * A wrapper class that allows a type of Ground to exist temporarily at a location.
 * <p>
 * This class holds a reference to an 'original' ground type and a 'temporary' ground type.
 * For a set duration, it will present itself and behave as the temporary ground.
 * Each turn, it calls the tick method of the temporary ground and counts down its duration.
 * Once the duration expires, it replaces itself at its location with the original ground.
 * This is useful for effects like fires that burn out or magical terrain that fades.
 *
 * @see Ground
 */
public class TemporaryGround extends Ground {
    /**
     * The original ground that the location will revert to after the effect ends.
     */
    private final Ground originalGround;

    /**
     * The temporary ground whose appearance and behavior are used during the effect's duration.
     */
    private final Ground temporaryground;

    /**
     * The remaining number of turns for this temporary effect.
     */
    private int duration;

    /**
     * Constructor for TemporaryGround.
     *
     * @param originalGround  The Ground type that the location will revert to when the duration expires.
     * @param temporaryground The Ground type that will be active temporarily. This object's display
     * character and tick behavior will be used.
     * @param duration        The number of game turns this temporary ground should last.
     */
    public TemporaryGround(Ground originalGround, Ground temporaryground, int duration) {
        // Inherit the display character from the temporary ground so it appears correctly on the map.
        super(temporaryground.getDisplayChar(), temporaryground.toString());
        this.originalGround = originalGround;
        this.temporaryground = temporaryground;
        this.duration = duration;
    }

    /**
     * Called once per turn, this method handles the logic for the temporary ground.
     * <p>
     * It performs two main functions:
     * 1. It calls the {@code tick} method of the underlying temporary ground, allowing it to
     * perform its own per-turn actions (e.g., BurningGround dealing damage).
     * 2. It decrements its own duration timer. If the timer reaches zero, it replaces
     * itself at the given location with the original ground type.
     *
     * @param location The location of the TemporaryGround instance.
     */
    @Override
    public void tick(Location location) {
        // Delegate the tick behaviour to the active temporary ground.
        temporaryground.tick(location);

        if (this.duration > 0) {
            this.duration--;
            if (this.duration <= 0) {
                // When duration expires, replace this ground with the original one.
                location.setGround(this.originalGround);
            }
        }
    }
}