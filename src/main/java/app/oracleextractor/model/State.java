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

    /**
     * <code>State</code> constructor.
     */
    public State() {
        stateName = "q" + id++;
    }

    public State(String name) {
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
        for (Transition t : mach.getMachineTransitions()) { // For EVERY transition in this machine ...
            if (t.getSourceState().equals(this) && t.isValid() && t.isTriggeredBy(currTrigger)) {
                // Check if the transition is valid && is the correct response to this character ...
                // ... if so, add it to the list of possible transitions
                applicableTransitions.add(t);
            }
        }
        // At this stage we have the collection of transitions that can be taken from here on out, we return this for now
        return applicableTransitions;
    }

    @Override
    public String toString() {
        return "State{stateName='" + stateName + '\'' + '}';
    }
}

