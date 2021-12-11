package app.oracleextractor.model.utils;

import app.oracleextractor.model.Transition;

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

    public Transition getLastChange() {
        int lastElementIndex = theTrace.size() - 1;
        return theTrace.get(lastElementIndex);
    }

    public Character getLastOutput(){
        // TODO
        return null;
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
