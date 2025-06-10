package game.actors.creatures;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.behaviours.behaviourselectors.BehaviourSelector;
import game.behaviours.behaviourselectors.PriorityBehaviourSelector;
import java.util.Map;
import java.util.TreeMap;

/**
 * An abstract base class for creatures within the game world. This class extends the base
 * {@link Actor} class and provides a common framework for managing creature behaviors using a
 * priority-based system. Subclasses are expected to define their specific characteristics and add
 * relevant behaviours.
 */
public abstract class Creature extends Actor {

    /**
     * A map storing the behaviours assigned to this creature, keyed by priority. Lower integer
     * values indicate higher priority. Uses TreeMap to potentially iterate in priority order if
     * keys represent priority directly, otherwise HashMap is fine.
     */
    private final Map<Integer, Behaviour> behaviours;

    /**
     * The strategy used to select which behaviour to execute from the available behaviours.
     */
    private final BehaviourSelector behaviourSelector;

    /**
     * Constructor for the Creature class. Initializes basic actor properties. Calls the constructor
     * of the superclass {@link Actor}.
     *
     * @param name        the name of the Creature
     * @param displayChar the character that will represent the Creature in the display
     * @param hitPoints   the Creature's starting hit points
     */
    public Creature(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.behaviourSelector = new PriorityBehaviourSelector();
        this.behaviours = new TreeMap<>();
        initializeBehaviours(); // Template method - subclasses implement this
    }

    /**
     * Constructor with custom behaviour selector.
     *
     * @param name              the name of the creature
     * @param displayChar       the character used to represent the creature
     * @param hitPoints         the creature's hit points
     * @param behaviourSelector the strategy for selecting behaviours
     */
    public Creature(String name, char displayChar, int hitPoints,
            BehaviourSelector behaviourSelector) {
        super(name, displayChar, hitPoints);
        this.behaviours = new TreeMap<>();
        this.behaviourSelector = behaviourSelector;
        initializeBehaviours(); // Template method - subclasses implement this
    }


    /**
     * Selects and returns an action to perform on the current turn based on assigned behaviours and
     * the configured behaviour selection strategy. The BehaviourSelector determines which behaviour
     * to execute from the available behaviours. Template method for subclasses to initialize their
     * specific behaviours. This method should be implemented by each creature type to add their
     * specific behaviours to the behaviours list.
     */
    protected abstract void initializeBehaviours();

    /**
     * Add a behaviour to this creature's behaviour list.
     *
     * @param priority the priority of behavior
     * @param behaviour the behavior to add
     */
    protected void addBehaviour(int priority, Behaviour behaviour) {
        behaviours.put(priority, behaviour);
    }

    /**
     * Selects and returns an action to perform on the current turn based on assigned behaviours and
     * the configured behaviour selection strategy. The BehaviourSelector determines which behaviour
     * to execute from the available behaviours.
     *
     * @param actions    collection of possible Actions for this Actor (typically not used directly
     *                   here, but provided by the engine)
     * @param lastAction The Action this Actor took the last turn. Can do interesting things in
     *                   conjunction with Action.getNextAction()
     * @param map        the {@link GameMap} containing the Actor
     * @param display    the {@link Display} I/O object to which messages may be written
     * @return the Action to be performed, determined by the behaviour selector, or DoNothingAction
     * if no valid action is available.
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        // Use the behaviour selector to choose an action from available behaviours
        Action selectedAction = behaviourSelector.selectBehaviour(behaviours, this, map);

        if (selectedAction != null) {
            return selectedAction;
        }

        // If no behaviour produces a valid action, do nothing
        return new DoNothingAction();
    }
}