package app.oracleextractor.model;

import java.util.ArrayList;

public class Trace {
    ArrayList<StateTransition> theTrace;

    public Trace() {
        theTrace = new ArrayList<>();
    }

    /**
     * Method to add a <code>StateTransition</code> record to the list of <code>StateTransition</code>s.
     * @param before The <code>State</code> before taking the <code>Transition</code>
     * @param after The <code>State</code> after taking the <code>Transition</code>
     * @param transition The <code>Transition</code> itself.
     */
    public void addSTransition(State before, State after, Transition transition) {
        theTrace.add(new StateTransition(before, after, transition));
    }

    /**
     * Method to add a <code>StateTransition</code> record to the list of <code>StateTransition</code>s.
     * @param argStateTransition <code>StateTransition</code> to be added.
     */
    public void addSTransition(StateTransition argStateTransition) {
        theTrace.add(argStateTransition);
    }

    public StateTransition getLastChange() {
        int lastElementIndex = theTrace.size() - 1;
        return theTrace.get(lastElementIndex);
    }

    public void clear(){
        theTrace.clear();
    }

    @Override
    public String toString() {
        return "Trace{" +
                "theTrace=" + theTrace +
                '}';
    }
}
