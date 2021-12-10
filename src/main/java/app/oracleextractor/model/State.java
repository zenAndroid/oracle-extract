package app.oracleextractor.model;


import java.util.ArrayList;

public record State(String stateName, ArrayList<Transition> outGoing, ArrayList<Transition> inComing) {
    static int id = 0;

    public static State getState() {
        String stateName = "q" + id;
        id = id + 1;
        ArrayList<Transition> out = new ArrayList<>();
        ArrayList<Transition> in = new ArrayList<>();
        return new State(stateName, out, in);

    }

    public static State getState(String stateName) {
        ArrayList<Transition> out = new ArrayList<>();
        ArrayList<Transition> in = new ArrayList<>();
        return new State(stateName, out, in);

    }

    public ArrayList<Transition> getApplicableTransitions(Character trigger) {

        var applicableTransitions = new ArrayList<Transition>(); // Possible transitions from this state-trigger combo
        for (Transition t : outGoing) { // For EVERY transition in this machine ...
            if (t.sourceState().equals(this) && t.isValid() && t.isTriggeredBy(trigger)) {
                // Check if the transition is valid && is the correct response to this character ...
                // ... if so, add it to the list of possible transitions
                applicableTransitions.add(t);
            }
        }
        // At this stage we have the collection of transitions that can be taken from here on out, we return this for now
        return applicableTransitions;
    }

    public void addIncomingTransition(Transition t) {
        inComing.add(t); // lmao, tfw the final modifier in java does JACK SHIT
    }

    public void addOutgoingTransition(Transition t) {
        outGoing.add(t);
    }
}

