package app.oracleextractor.model;

import java.util.ArrayList;

public class Trace {
    ArrayList<StateTransition> theTrace;

    public Trace() {
        theTrace = new ArrayList<>();
    }

    public void addTrace(State before, State after, Transition transition) {
        theTrace.add(new StateTransition(before, after, transition));
    }

    public StateTransition getLastChange() {
        int lastElementIndex = theTrace.size() - 1;
        return theTrace.get(lastElementIndex);
    }

    @Override
    public String toString() {
        return "Trace{" +
                "theTrace=" + theTrace +
                '}';
    }
}
