package game.healing.items;

import edu.monash.fit2099.engine.items.Item;
import game.capabilities.GeneralCapability;
import game.healing.Curable;
import game.healing.CureAction;

/**
 * A class representing a Talisman item. This item is portable (can be picked up and dropped) and is
 * used to cure adjacent actors or ground tiles that possess the
 * {@link GeneralCapability#CAN_CURED} capability and implement the {@link Curable} interface. It
 * grants the {@link CureAction} to its owner. Represented by 'o' on the map.
 *
 * @author Adrian Kristanto Modified by: Goey Qi Hang
 */
public class Talisman extends Item {

    /**
     * Constructor for the Talisman. Initializes the item with the name "Talisman", display
     * character 'o', and makes it portable.
     */
    public Talisman() {
        super("Talisman", 'o', true);
        this.addCapability(GeneralCapability.CAN_CURED);
    }

}