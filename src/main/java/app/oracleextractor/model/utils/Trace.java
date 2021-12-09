package app.oracleextractor.model.utils;

import app.oracleextractor.model.Transition;

import java.util.ArrayList;

public class Trace {
    ArrayList<Transition> theTrace;

    public Trace() {
        theTrace = new ArrayList<>();
    }

    public void addTransition(Transition t) {
        theTrace.add(t);
    }

    public Transition getLastChange() {
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
