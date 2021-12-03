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

    public void setMach(Machine argMach) {
        mach = argMach;
    }

    /**
     * Alternative way to run the machine. <br />
     * Just testing a couple of things ...
     */
    public ArrayList<Transition> getApplicableTransitions() {
        Character currTrigger = mach.getNextInputToken();

        var applicableTransitions = new ArrayList<Transition>(); // Possible transitions from this state-trigger combo
        for (Transition t : stateTransitions) { // For every transition this state has ...
            if (t.isValid() && t.isTriggeredBy(currTrigger)) { // Check if the transition is valid && is the correct response to this character ...
                // ... if so, add it to the list of possible transitions
                applicableTransitions.add(t);
            }
        }
        // At this stage we have the collection of transitions that can be taken from here on out, we return this for now
        return applicableTransitions;
    }

}

