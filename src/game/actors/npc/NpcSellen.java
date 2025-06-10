package game.actors.npc;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.creatures.GoldenBeetle;
import game.actors.creatures.OmenSheep;
import game.buying.MerchantOffer;
import game.capabilities.GeneralCapability;
import game.conditions.Condition;
import game.conditions.DefaultCondition;
import game.effects.Effect;
import game.effects.HealEffect;
import game.effects.IncreaseMaxHealthEffect;
import game.effects.IncreaseMaxStaminaEffect;
import game.effects.SpawnActorEffect;
import game.weapons.Broadsword;
import game.weapons.DragonslayerGreatsword;
import game.weapons.Katana;
import java.util.ArrayList;

/**
 * A representation of the "Sellen" NPC in the game. Sellen is a character associated with
 * glintstone magic and the academy. She functions as a merchant, offering magical or powerful
 * weapons for sale, and shares philosophical thoughts about the academy and magic.
 *
 * <p>Sellen can sell items if she has the {@link GeneralCapability#CAN_SELL} capability.
 * Her monologues are generally about the nature of magic and the academy, triggered by a
 * {@link DefaultCondition}.</p>
 */
public class NpcSellen extends Npc {

    /**
     * Display the character representing Sellen on the game map.
     */
    private final static char DISPLAY_CHAR = 's';

    /**
     * Initial hit points (health) of Sellen.
     */
    private final static int HIT_POINTS = 150;

    /**
     * The name of this NPC.
     */
    private final static String NAME = "Sellen";

    /**
     * Constructor for the NpcSellen class.
     *
     * <p>This constructor sets up Sellen with her name, display character, and initial health.
     * It assigns her the {@link GeneralCapability#CAN_SELL} capability and defines her merchant
     * offers:
     * <ul>
     * <li>A {@link Broadsword} for 100 runes, which also applies an {@link IncreaseMaxHealthEffect} of 20.</li>
     * <li>A {@link DragonslayerGreatsword} for 1500 runes, which also applies a {@link SpawnActorEffect} to spawn a {@link GoldenBeetle}.</li>
     * <li>A {@link Katana} for 500 runes, which also applies a {@link SpawnActorEffect} to spawn an {@link OmenSheep} near Sellen herself,
     * a {@link HealEffect} of 10, and an {@link IncreaseMaxStaminaEffect} of 20.</li>
     * </ul>
     * Her monologues provide insight into glints tone sorcery and the academy.
     */
    public NpcSellen() {
        super(NpcSellen.NAME, NpcSellen.DISPLAY_CHAR, NpcSellen.HIT_POINTS);
        this.addCapability(GeneralCapability.CAN_SELL); // Sellen can sell items

        // Define offers
        // Broadsword
        ArrayList<Effect> broadswordEffects = new ArrayList<>();
        broadswordEffects.add(new IncreaseMaxHealthEffect(20)); // Buying this increases max health
        offers.add(new MerchantOffer(new Broadsword(), 100, broadswordEffects));

        // Dragonslayer Greatsword
        ArrayList<Effect> dragonslayerEffects = new ArrayList<>();
        // Spawns a GoldenBeetle near the buyer
        dragonslayerEffects.add(new SpawnActorEffect(GoldenBeetle::new));
        offers.add(new MerchantOffer(new DragonslayerGreatsword(), 1500, dragonslayerEffects));

        // Katana
        ArrayList<Effect> katanaEffects = new ArrayList<>();
        // Spawns an OmenSheep near Sellen (the merchant)
        katanaEffects.add(new SpawnActorEffect(OmenSheep::new, this));
        katanaEffects.add(new HealEffect(10)); // Buying this increases max health
        katanaEffects.add(new IncreaseMaxStaminaEffect(20));
        offers.add(new MerchantOffer(new Katana(), 500, katanaEffects));
    }

    /**
     * Returns a list of {@link Monologue}s that Sellen can say. Sellen's monologues are generally
     * about the academy and glintstone magic, and are available under a {@link DefaultCondition}.
     * <ul>
     * <li>"The academy casts out those it fears. Yet knowledge, like the stars, cannot be bound forever."</li>
     * <li>"You sense it too, don’t you? The Glintstone hums, even now."</li>
     * </ul>
     *
     * @param listener The {@link Actor} who is listening to Sellen.
     * @param map      The current {@link GameMap}.
     * @return An {@link ArrayList} of {@link Monologue} objects.
     */
    @Override
    public ArrayList<Monologue> getMonologues(Actor listener, GameMap map) {
        // Define conditions for triggering specific monologues
        Condition defaultCondition = new DefaultCondition();

        ArrayList<Monologue> monologues = new ArrayList<>();
        monologues.add(new Monologue(defaultCondition,
                "The academy casts out those it fears. Yet knowledge, " +
                        "like the stars, cannot be bound forever."));
        monologues.add(new Monologue(defaultCondition,
                "You sense it too, don’t you? The Glintstone hums, even now."));

        return monologues;
    }
}