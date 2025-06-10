package game.plants;

/**
 * Represents a specific type of {@link Seed} item used to grow a {@link Bloodrose} plant.
 * This seed has a defined name and stamina cost associated with planting it.
 */
public class BloodroseSeed extends Seed {

    /**
     * The constant name for the Bloodrose Seed, used for identification and display.
     */
    public static final String NAME = "Bloodrose Seed";

    /**
     * Constructor for the BloodroseSeed.
     * Initializes the seed with its specific name using the superclass constructor.
     */
    public BloodroseSeed() {
        super(BloodroseSeed.NAME);
    }

    /**
     * Creates and returns a new instance of the {@link Bloodrose} plant.
     * This method is called when the seed is successfully planted.
     *
     * @return A new {@link Bloodrose} object.
     */
    @Override
    public Plant createPlant() {
        return new Bloodrose();
    }

    /**
     * Gets the stamina cost required for an actor to plant this specific seed.
     *
     * @return The integer value representing the stamina cost (25) for planting a Bloodrose Seed.
     */
    @Override
    public int getStaminaCost() {
        return 75;
    }
}