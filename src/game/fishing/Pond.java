package game.fishing;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.GeneralCapability;
import java.util.ArrayList;

/**
 * A type of ground that represents a pond where an actor can fish.
 * It contains a variety of {@link Fishable} items that can be caught.
 * Actors cannot enter the pond.
 */
public class Pond extends Ground {

    /**
     * A list of items that can be caught by fishing in this pond.
     */
    private final ArrayList<Fishable> fishableItems;

    /**
     * Constructor for Pond.
     * Initializes the ground with its display character and name, and populates the list of
     * fishable items.
     */
    public Pond() {
        super('~', "Pond");
        this.fishableItems = new ArrayList<>();
        fishableItems.add(new SalmonFish());
        fishableItems.add(new ToxicEel());
        fishableItems.add(new Shovel());
        fishableItems.add(new GoldenFish());
        fishableItems.add(new OldBoot());
    }

    /**
     * Returns a list of allowable actions an actor can perform at this location.
     * If the actor has an item with the {@link GeneralCapability#CAN_FISH} capability,
     * a {@link FishAction} is made available.
     *
     * @param actor     The actor at the location.
     * @param location  The current location.
     * @param direction The direction of the ground from the actor.
     * @return A list of allowable actions.
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = super.allowableActions(actor, location, direction);
        for (Item item : actor.getItemInventory()) {
            if (item.hasCapability(GeneralCapability.CAN_FISH)) {
                actions.add(new FishAction(item, this, location));
            }
        }
        return actions;
    }

    /**
     * Gets a copy of the list of fishable items available in this pond.
     *
     * @return A new ArrayList containing the fishable items.
     */
    public ArrayList<Fishable> getFishableItems() {
        return new ArrayList<>(fishableItems);
    }

    /**
     * Determines whether an actor can enter this ground.
     *
     * @param actor The actor to check.
     * @return false, as actors cannot enter a Pond.
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }
}