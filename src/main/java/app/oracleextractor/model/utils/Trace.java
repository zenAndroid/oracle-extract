package app.oracleextractor.model.utils;

import app.oracleextractor.model.Transition;
import app.oracleextractor.model.exceptions.NoLastChange;

import java.util.ArrayList;

public class Trace {
    ArrayList<Transition> theTrace;

    public Trace() {
        theTrace = new ArrayList<>();
    }

    public Trace(Trace oldTrace){
        theTrace = new ArrayList<>();
        for (Transition t: oldTrace.theTrace){
            addTransition(t);
        }
    }

    public void addTransition(Transition t) {
        theTrace.add(t);
    }

    public Transition getLastChange() throws NoLastChange {
        int lastElementIndex = theTrace.size() - 1;
        if(lastElementIndex != -1) {
            return theTrace.get(lastElementIndex);
        }
        else {
            throw new NoLastChange("Fresh execution: there is no transition in the trace.");
        }
    }

    public Character getLastOutput() throws NoLastChange {
        return getLastChange().transitionOutput();
    }

    public void clear() {
        theTrace.clear();
    }

    @Override
    public String toString() {
        return "Trace{" +
                "theTrace=" + theTrace +
                '}';
    }
}
