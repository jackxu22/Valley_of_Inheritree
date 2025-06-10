package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.positions.GameMap;
import game.capabilities.GeneralCapability;
import game.fishing.FishingRod;
import game.fishing.Shovel;
import game.spells.FireSpell;
import game.spells.HealSpell;
import game.spells.PoisonSpell;
import game.spells.TeleportSpell;
import game.weapons.BareFist;

/**
 * Class representing the Player character in the game. The player can interact with the game world,
 * has health and stamina attributes, and is controlled by the user via a menu.
 *
 * @author Adrian Kristanto
 */
public class Player extends Actor {

    /**
     * Maximum stamina point the player can have.
     */
    private static final int MAXIMUM_STAMINA_POINT = 200;
    private static final int MAXIMUM_MANA_POINT = 200;

    /**
     * Constructor. Initializes the player with a name, display character, hit points, sets them as
     * hostile to enemies, gives them a BareFist intrinsic weapon, and adds a Stamina attribute.
     *
     * @param name        Name to call the player in the UI
     * @param displayChar Character to represent the player in the UI
     * @param hitPoints   Player's starting number of hitpoints
     */
    public Player(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.addCapability(GeneralCapability.HOSTILE_TO_ENEMY);
        this.addCapability(GeneralCapability.FOLLOWABLE);
        this.addCapability(GeneralCapability.CONSUMER);
        this.addCapability(GeneralCapability.CAN_LISTEN);
        this.addCapability(GeneralCapability.CAN_BUY);
        this.addCapability(GeneralCapability.CAN_TELEPORT);
        this.setIntrinsicWeapon(new BareFist());
        // Initialize Stamina attribute
        this.addAttribute(BaseActorAttributes.STAMINA,
                new BaseActorAttribute(Player.MAXIMUM_STAMINA_POINT)); // Example starting stamina
        this.addAttribute(BaseActorAttributes.MANA,
                new BaseActorAttribute(Player.MAXIMUM_MANA_POINT)); // Added mana attribute

        this.addBalance(10000);
        this.addItemToInventory(new PoisonSpell());
        this.addItemToInventory(new HealSpell());
        this.addItemToInventory(new TeleportSpell());
        this.addItemToInventory(new FireSpell());

        this.addItemToInventory(new FishingRod());
        this.addItemToInventory(new Shovel());
    }

    /**
     * Selects and returns an action to perform on the current turn. Prints the player's current
     * health and stamina status. Handles multi-turn actions if any are in progress. Otherwise,
     * displays a menu of available actions to the user and returns their choice.
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Used to handle multi-turn actions.
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written and the menu displayed
     * @return the Action selected by the player from the menu
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        display.print(this + " Health: (" + this.getAttribute(BaseActorAttributes.HEALTH) + "/"
                + this.getAttributeMaximum(BaseActorAttributes.HEALTH) + ")" + " Stamina: ("
                + this.getAttribute(BaseActorAttributes.STAMINA) + "/" + this.getAttributeMaximum(
                BaseActorAttributes.STAMINA) + ")" + " Mana: (" // Added Mana display
                + this.getAttribute(BaseActorAttributes.MANA) + "/" + this.getAttributeMaximum(
                BaseActorAttributes.MANA) + ")" + " Runes: " + this.getBalance() + "\n");
        // Handle multi-turn Actions first
        if (lastAction.getNextAction() != null) {
            return lastAction.getNextAction();
        }

        // Show the menu and get the player's choice
        Menu menu = new Menu(actions);
        return menu.showMenu(this, display);
    }

    /**
     * Returns the name of the player. Overrides the default Actor toString to provide a simpler
     * representation.
     *
     * @return The player's name.
     */
    @Override
    public String toString() {
        return this.name;
    }


}