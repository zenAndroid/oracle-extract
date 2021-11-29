package app.oracleextractor.model;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * Representing a state in the context of a Mealy Machine.
 * A <code>State</code> has:
 * <ul>
 *     <li>A `name`.</li>
 *     <li>A `mach` variable that represents the machine this state belongs to.</li>
 *     <li>A set of transitions: represents the transitions that flow-out of this <code>State</code></li>
 * </ul>
 * <p>
 * This class also has a static member variable, `id`, which assigns each new <code>State</code> a new, unique ID.
 * These IDs are not used currently, but they might replace the naming scheme for this model, or they may simply be a last naming resort.
 *
 * @author zenAndroid
 */
public class State {
    static int id = 0;
    Machine mach;
    String name;
    ArrayList<Transition> stateTransitions;

    /**
     * Standard constructor, needs a name.
     *
     * @param name Name used for the <code>State</code>
     */
    public State(String name) {
        this.name = name;
        // id = id++;
    }

    public static int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Transition> getStateTransitions() {
        return stateTransitions;
    }

    /**
     * Custom setter function that uses the var-arg syntax to allow easier transition creation/addition to the <code>State</code>.
     * <br />
     * <p>
     * Might remove the @NotNull annotation since it adds an unnecessary dependency though.
     *
     * @param argTransitions The transitions intended for addition into the <code>State</code>.
     */
    public void setStateTransitions(Transition... argTransitions) {
        // Edit: done (prior taks to be done) : figure out if this is even necessary.
        //       it is, leave it be, else you get NullPointerException
        //       which makes sense as the field would be empty
        stateTransitions = new ArrayList<>();
        stateTransitions.addAll(Arrays.asList(argTransitions)); // Offered as an alternative syntax by Idea, will test!
    }

    public void setStateTransitions(ArrayList<Transition> argTransitions) {
        stateTransitions = argTransitions;
    }

    public Machine getMach() {
        return mach;
    }

    public void setMach(Machine mach) {
        this.mach = mach;
    }

    /**
     * Method that triggers the state's consumption of the next input token.
     * todo: make sure that this respects representation independence. (stateTransitions or by getter?)
     *  Will turn it into a getter function given that it will/should make it easier to change the implementation,
     *  although i am not too convinced by this argument, since it is very unlikely that stateTransitions are ever changed,
     *  .... yeah actually i don't think i'll change this.
     */
    public void consumeInputToken() {
        Character currTrigger = mach.getNextInputToken();

        for (Transition tr : stateTransitions) { // For every transition,
            // We check that the transition is a) valid and b) compatible with this trigger.
            if (tr.isValid() && tr.isTriggeredBy(currTrigger)) {
                // If so, then we a) emit the output marked by this transition;
                emitOutput(tr.getTransitionOutput());
                // b) change the machine's current state to the transition's destination State.
                changeMachineState(tr.getDestinationState());
            }
        }
    }

    /**
     * Method used to change machine's current state.
     *
     * @param destinationState State to change to.
     *
     */
    private void changeMachineState(State destinationState) {
        mach.changeState(destinationState);
        // mach.currentState = destinationState; // I was actually surprised this works at first since
        // // we are accessing a class's field directly and the default initializer isn't public
        // // It turns out the default initializer is package-private, meaning classes defined in the same package can access the fields.
        // // Will change this though.
    }

    /**
     * Method that allows a <code>State</code> to communicate a <code>Transition</code>'s output to its <code>Machine</code>.
     *
     * @param transitionOutput The output.
     */
    private void emitOutput(Character transitionOutput) {
        mach.processOutput(transitionOutput);
    }

}

