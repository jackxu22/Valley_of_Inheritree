package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.GeneralCapability;
import game.healing.Curable;
import game.healing.CureAction;

/**
 * A class representing a blight, a type of cursed ground found in the valley. Blight is represented
 * by 'x', is considered cursed, and can be cured (transformed into Soil). It implements the
 * {@link Curable} interface to define its curing behavior.
 *
 * @author Adrian Kristanto Modified by: Goey Qi Hang
 */
public class Blight extends Ground implements Curable {

    /**
     * Constructor for the Blight class.
     * <p>
     * Sets the display character to 'X' and adds the capabilities indicating that seeds can
     * be planted here and that the ground can be burned.
     */
    public Blight() {
        super('x', "Blight");
        this.addCapability(GeneralCapability.CURSED);
        this.addCapability(GroundCapability.CAN_BURNED);
     }

    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = super.allowableActions(actor, location, direction);

        for (Item item : actor.getItemInventory()){
            if (item.hasCapability(GeneralCapability.CAN_CURED)){
                actions.add(new CureAction(this,location,item));
            }
        }

        return actions;
    }

    /**
     * Cures the Blight at the specified location, transforming it into {@link Soil}. This action is
     * triggered by an actor using a compatible healing item.
     *
     * @param healer       The actor performing the cure action.
     * @param cureLocation The location of the Blight ground to be cured.
     * @param healingItem  The item used to perform the cure.
     * @return A descriptive string indicating that the Blight has been converted to Soil.
     */
    @Override
    public String cure(Actor healer, Location cureLocation, Item healingItem) {
        cureLocation.setGround(new Soil());
        return "Blight has been converted by " + healingItem + " at " + cureLocation + ".";
    }

    /**
     * Returns the stamina cost required for an actor to cure this Blight.
     *
     * @return The stamina cost (currently 50) required to cure the Blight.
     */
    @Override
    public int getCureStaminaCost() {
        return 50;
    }

}