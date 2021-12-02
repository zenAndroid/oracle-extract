package app.oracleextractor.model;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * Representing a state in the context of a Mealy Machine. <br />
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
    String stateName;
    ArrayList<Transition> stateTransitions;

    /**
     * <code>State</code> constructor.
     */
    public State() {
        stateTransitions = new ArrayList<>();
        stateName = "q" + id++;
    }

    public State(String name) {
        stateTransitions = new ArrayList<>();
        stateName = name;
    }

    public static int getId() {
        return id;
    }

    public String getName() {
        return stateName;
    }

    public void setName(String name) {
        this.stateName = name;
    }

    public ArrayList<Transition> getStateTransitions() {
        return stateTransitions;
    }

    /**
     * Custom setter function that uses the var-arg syntax to allow easier transition creation/addition to the <code>State</code>.
     * <br /> <br />
     * <p>
     * Might remove the @NotNull annotation since it adds an unnecessary dependency though.
     *
     * @param argTransitions The transitions intended for addition into the <code>State</code>.
     */
    public void setStateTransitions(Transition... argTransitions) {
        // Edit: done (prior tasks to be done) : figure out if this is even necessary.
        //       it is, leave it be, else you get NullPointerException
        //       which makes sense as the field would be empty
        stateTransitions = new ArrayList<>();
        // Have to tell the transitions what their source transition is.
        for (Transition t : argTransitions) {
            t.setSourceState(this);
        }
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
     * Method that triggers the state's consumption of the next input token.<br /> <br />
     * todo: make sure that this respects representation independence. (stateTransitions or by getter?)<br />
     *  Will turn it into a getter function given that it will/should make it easier to change the implementation,
     *  although i am not too convinced by this argument, since it is very unlikely that stateTransitions are ever changed,
     *  .... yeah actually i don't think i'll change this. <br />
     *  Also, This is the point you will need to change in order to handle non-determinism. MARK AS BOOKMARK
     */
    public void consumeInputToken() {
        Character currTrigger = mach.getNextInputToken();

        for (Transition tr : stateTransitions) { // For every transition,
            // We check that the transition is a) valid and b) compatible with this trigger.
            if (tr.isValid() && tr.isTriggeredBy(currTrigger)) {
                // If so, then we a) mark the transition as having been visited,
                tr.setTaken();
                // b) emit the output marked by this transition;
                emitOutput(tr.getTransitionOutput());
                // c) change the machine's current state to the transition's destination State.
                changeMachineState(tr.getDestinationState());
            }
        }
    }

    /**
     * Alternative way to run the machine. <br />
     * Just testing a couple of things ...
     */
    public ArrayList<Transition> getApplicableTransitions(){
        Character currTrigger = mach.getNextInputToken();

        var applicableTransitions = new ArrayList<Transition>(); // Possible transitions from this state-trigger combo
        for (Transition t : stateTransitions){ // For every transition this state has ...
            if (t.isValid() && t.isTriggeredBy(currTrigger)){ // Check if the transition is valid && is the correct response to this character ...
               // ... if so, add it to the list of possible transitions
               applicableTransitions.add(t);
            }
        }
        // At this stage we have the collection of transitions that can be taken from here on out, we return this for now
        return applicableTransitions;
    }

    /**
     * Method used to change machine's current state.
     *
     * @param destinationState State to change to.
     */
    public void changeMachineState(State destinationState) {
        mach.changeState(destinationState);
        /*
         mach.currentState = destinationState; // I was actually surprised this works at first since
         // we are accessing a class's field directly and the default initializer isn't public
         // It turns out the default initializer is package-private, meaning classes defined in the same package can access the fields.
         // Will change this though.
        */
    }

    /**
     * Method that allows a <code>State</code> to communicate a <code>Transition</code>'s output to its <code>Machine</code>.
     *
     * @param transitionOutput The output.
     */
    public void emitOutput(Character transitionOutput) {
        mach.processOutput(transitionOutput);
    }

}

