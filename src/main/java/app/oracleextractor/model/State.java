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

    public void addIncomingTransition(Transition t) {
        inComing.add(t); // lmao, tfw the final modifier in java does JACK SHIT
    }

    public void addOutgoingTransition(Transition t) {
        outGoing.add(t);
    }
}

