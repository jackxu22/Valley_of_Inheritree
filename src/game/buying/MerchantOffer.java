package game.buying;

import game.effects.Effect;
import java.util.ArrayList;


/**
 * Represents an offer made by a merchant (an {@link edu.monash.fit2099.engine.actors.Actor} with
 * {@link game.capabilities.GeneralCapability#CAN_SELL} capability). This class encapsulates a
 * {@link Purchasable} item, its price in runes, and a list of additional {@link Effect}s that are
 * applied to the buyer upon purchasing this specific offer.
 * <p>
 * The {@link Purchasable#getBasePurchaseEffects()} from the item itself are always applied, and
 * these {@code additionalEffects} are applied on top of those.
 */
public class MerchantOffer {

    /**
     * The item that is being offered for sale. Must implement the {@link Purchasable} interface.
     */
    private final Purchasable purchasableItem;
    /**
     * The price of the item in runes.
     */
    private final int price;
    /**
     * A list of additional {@link Effect}s that are applied to the purchasing actor specifically
     * for this offer, beyond any base effects of the item itself.
     */
    private final ArrayList<Effect> additionalEffects;

    /**
     * Constructs a new MerchantOffer.
     *
     * @param purchasableItem   The item to be offered for sale.
     * @param price             The price of the item in runes.
     * @param additionalEffects A list of effects that are specific to this offer and will be
     *                          applied to the buyer upon purchase, in addition to the item's base
     *                          purchase effects.
     */
    public MerchantOffer(Purchasable purchasableItem, int price,
            ArrayList<Effect> additionalEffects) {
        this.purchasableItem = purchasableItem;
        this.price = price;
        this.additionalEffects = additionalEffects;
    }

    /**
     * Gets the {@link Purchasable} item associated with this offer.
     *
     * @return The purchasable item.
     */
    public Purchasable getItem() {
        return purchasableItem;
    }

    /**
     * Gets the price of the item in runes.
     *
     * @return The rune price.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Gets the list of additional {@link Effect}s specific to this merchant offer. These effects
     * are applied to the buyer on top of any base effects defined by the {@link Purchasable} item
     * itself.
     *
     * @return An {@link ArrayList} of additional effects.
     */
    public ArrayList<Effect> getEffects() {
        return additionalEffects;
    }

}