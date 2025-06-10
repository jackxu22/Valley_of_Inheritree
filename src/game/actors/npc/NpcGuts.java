package game.actors.npc;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.behaviours.AttackBehaviour;
import game.behaviours.AttackConditionEvaluator;
import game.conditions.Condition;
import game.conditions.DefaultCondition;
import game.conditions.DynamicTargetHealthCondition;
import game.conditions.LowHealthCondition;
import game.weapons.BareFist;
import java.util.ArrayList;

/**
 * A representation of the "Guts" character in the game. This NPC is a powerful fighter
 * characterized by his aggressive {@link AttackBehaviour} and specific monologues, particularly
 * when a listening actor has low health. Guts uses {@link BareFist} as his intrinsic weapon.
 */
public class NpcGuts extends Npc implements AttackConditionEvaluator {

    /**
     * Display character representing Guts on the game map.
     */
    private final static char DISPLAY_CHAR = 'g';

    /**
     * Initial hit points (health) of Guts.
     */
    private final static int HIT_POINTS = 500;

    /**
     * The name of this NPC.
     */
    private final static String NAME = "Guts";

    /**
     * Priority for the {@link AttackBehaviour}. Lower numbers indicate higher priority.
     */
    private static final int PRIORITY_ATTACK = 5;

    private static final int ATTACK_HEALTH_THRESHOLD = 50;

    /**
     * Constructor for the NpcGuts class.
     *
     * <p>This constructor sets up the Guts NPC with its name, display character, and initial
     * health. It assigns {@link BareFist} as his intrinsic weapon and adds an
     * {@link AttackBehaviour} with a defined priority. His monologues are tailored to reflect his
     * aggressive nature, especially reacting to a low-health listener.</p>
     */
    public NpcGuts() {
        super(NpcGuts.NAME, NpcGuts.DISPLAY_CHAR, NpcGuts.HIT_POINTS);
        // Set the Intrinsic weapon BareFist for Guts
        this.setIntrinsicWeapon(new BareFist());

        // Define and add the Attack behaviour with high priority
        AttackBehaviour attackBehaviour = new AttackBehaviour(this);
        addBehaviour(PRIORITY_ATTACK, attackBehaviour);
    }
    /**
     * Implementation of the AttackConditionEvaluator interface.
     * Guts will decide to attack if the potential target's health is above his threshold.
     * This method now uses DynamicTargetHealthCondition for the check.
     */
    @Override
    public boolean evaluate(Actor attacker, Actor potentialTarget, GameMap map) {
        // 'attacker' is this NpcGuts instance.
        // The core logic is about the potentialTarget's health.
        Condition condition = new DynamicTargetHealthCondition(potentialTarget, ATTACK_HEALTH_THRESHOLD);
        return condition.check();
    }

    /**
     * Returns a list of {@link Monologue}s that Guts can say. Guts has a specific monologue ("WEAK!
     * TOO WEAK TO FIGHT ME!") if the {@code listener} is under a {@link LowHealthCondition}.
     * Otherwise, he has default aggressive monologues ("RAAAAGH!", "I’LL CRUSH YOU ALL!").
     *
     * @param listener The {@link Actor} who is listening to Guts.
     * @param map      The current {@link GameMap}.
     * @return An {@link ArrayList} of {@link Monologue} objects appropriate for the situation.
     */
    @Override
    public ArrayList<Monologue> getMonologues(Actor listener, GameMap map) {
        // Define conditions for triggering specific monologues
        Condition defaultCondition = new DefaultCondition();
        Condition lowHealthCondition = new LowHealthCondition(
                listener); // Condition based on listener's health

        ArrayList<Monologue> monologues = new ArrayList<>();
        monologues.add(new Monologue(lowHealthCondition, "WEAK! TOO WEAK TO FIGHT ME!"));
        monologues.add(new Monologue(defaultCondition, "RAAAAGH!"));
        monologues.add(new Monologue(defaultCondition, "I’LL CRUSH YOU ALL!"));

        return monologues;
    }
}