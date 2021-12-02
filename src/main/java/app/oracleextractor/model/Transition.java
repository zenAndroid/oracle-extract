package app.oracleextractor.model;


/**
 * Represents a transition between two states.
 * This differs significantly from the mathematical model, which can ba said of the entire model to be honest.
 * But the way it differs is that the mathematical model assumes that a singular transition holds both the source <code>State</code>
 * and the destination <code>State</code>, whereas in this model the transition BELONGS to a <i>specific</i> <code>State</code>.
 * <p>
 * Other than that, each <code>Transition</code> has:
 * <ul>
 *     <li>A transitionTrigger: The input that launches/triggers this transition.</li>
 *     <li>A transitionOutput: The output that results for this transition being taken.</li>
 *     <li>A transitionValid: The boolean flag that determines if this transition is valid and applicable.</li>
 *     <li>A destinationState: The <code>State</code> taken after this transition takes place.</li>
 *     <li>A destinationState: The <code>State</code> that generates this transition.</li>
 *     <li>A boolean flag that determines is this transition has been taken in the execution of this machine.</li>
 * </ul>
 *
 * @author zenAndroid
 */
public class Transition {
    Character transitionTrigger;
    Character transitionOutput;
    Boolean transitionValid;
    State sourceState;
    State destinationState;
    Boolean wasVisited;

    /**
     * <code>Transition</code> constructor, takes the trigger and the output resulting from this transition.
     *
     * @param transitionTrigger Input token that triggers the transition.
     * @param transitionOutput  Output emitted when this transition takes place.
     */
    public Transition(Character transitionTrigger, Character transitionOutput) {
        commonInit();
        this.transitionTrigger = transitionTrigger;
        this.transitionOutput = transitionOutput;
        sourceState = new State();
        destinationState = new State();
    }

    /**
     * <code>Transition</code> constructor, take the trigger, the output, and the destination <code>State</code>
     *
     * @param transitionTrigger Input token that triggers the transition.
     * @param transitionOutput  Output emitted when this transition takes place.
     * @param destinationState  The resulting <code>State</code> after this <code>Transition</code>.
     */
    public Transition(Character transitionTrigger, Character transitionOutput, State destinationState) {
        commonInit();
        this.transitionTrigger = transitionTrigger;
        this.transitionOutput = transitionOutput;
        sourceState = new State();
        this.destinationState = destinationState;
    }

    /**
     * <code>Transition</code> constructor that accepts also the <code>sourceState</code> to hold a reference to the source state;
     *
     * @param transitionTrigger Input token that triggers the transition.
     * @param transitionOutput  Output emitted when this transition takes place.
     * @param sourceState       The <code>State</code> that 'emits' this <code>Transition</code>.
     * @param destinationState  The resulting <code>State</code> after this <code>Transition</code>.
     */
    public Transition(Character transitionTrigger, Character transitionOutput, State sourceState, State destinationState) {
        commonInit();
        this.transitionTrigger = transitionTrigger;
        this.transitionOutput = transitionOutput;
        this.sourceState = sourceState;
        this.destinationState = destinationState;
    }

    /**
     * Method for holding the common operations that occur when creating a <code>Transition</code>
     */
    public void commonInit() {

        /* All transitions start out valid */
        transitionValid = true; // Autoboxing is automatic.
        wasVisited = false; // always keep forgetting to initialize these ...
    }

    public Character getTransitionTrigger() {
        return transitionTrigger;
    }

    public void setTransitionTrigger(Character transitionTrigger) {
        this.transitionTrigger = transitionTrigger;
    }

    public Character getTransitionOutput() {
        return transitionOutput;
    }

    public void setTransitionOutput(Character transitionOutput) {
        this.transitionOutput = transitionOutput;
    }

    public Boolean getTransitionValid() {
        return transitionValid;
    }

    public void setTransitionValid(Boolean transitionValid) {
        this.transitionValid = transitionValid;
    }

    public State getDestinationState() {
        return destinationState;
    }

    public void setDestinationState(State destinationState) {
        this.destinationState = destinationState;
    }

    public State getSourceState() {
        return sourceState;
    }

    public void setSourceState(State sourceState) {
        this.sourceState = sourceState;
    }

    /**
     * Essentially a getter for <code>transitionValid</code>.
     *
     * @return <code>transitionValid</code>
     */
    public Boolean isValid() {
        return transitionValid;
    }

    /**
     * Sets the <code>wasVisited</code> flag to true.
     */
    public void setTaken() {
        wasVisited = true;
    }

    /**
     * Getter for the visited flag;
     * @return <code>wasVisited</code>
     */
    public Boolean wasTaken(){
        return wasVisited;
    }

    /**
     * Method for testing whether the passed argument is equal to this <code>Transition</code>'s trigger.
     *
     * @param trigger Input token being tested for (value) equality.
     * @return If the character (in this case) is the same as this <code>Transition</code>'s trigger.
     */
    public Boolean isTriggeredBy(Character trigger) {
        return getTransitionTrigger().equals(trigger);
    }
}
