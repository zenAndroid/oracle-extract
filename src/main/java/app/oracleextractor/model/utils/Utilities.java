package app.oracleextractor.model.utils;

import app.oracleextractor.model.Machine;
import app.oracleextractor.model.State;
import app.oracleextractor.model.Transition;
import app.oracleextractor.model.exceptions.StateNotFound;

import java.util.ArrayList;
import java.util.Set;

public class Utilities {
    /**
     * Temporary utility function to turn a string to an arraylist of characters.
     *
     * @param input Input <code>String</code>.
     * @return <code>ArrayList|Character|</code> representing the input.
     */
    public static ArrayList<Character> stringToList(String input) {
        var retVal = new ArrayList<Character>();
        for (Character ch : input.toCharArray()) {
            retVal.add(ch);
        }
        return retVal;
    }

    /**
     * @return A default machine, hardcoded and used for testing purposes.
     */
    public static Machine getDefaultMachine() {

        Machine debug = new Machine();

        /*
        The state are created, with their uniques names;
         */
        State s1 = new State();
        State s2 = new State();
        State s3 = new State();
        State s4 = new State();
        /*
        The transitions are created with their trigger, output, source states and destination states.
         */
        Transition t1 = new Transition('b', '2', s1, s2);
        Transition tnd2 = new Transition('b', '8', s1, s4);
        Transition t2 = new Transition('a', '1', s1, s4);
        Transition t3 = new Transition('a', '3', s2, s1);
        Transition t4 = new Transition('b', '2', s2, s3);
        Transition t5 = new Transition('a', '1', s3, s2);
        Transition t6 = new Transition('b', '2', s3, s1);
        Transition t7 = new Transition('b', '1', s4, s1);
        Transition t8 = new Transition('a', '2', s4, s3);
        Transition tnd9 = new Transition('a', '2', s4, s2); // Non-determinism test

        debug.setInitialState(s1);

        debug.setInputAlphabet(Set.of('a', 'b'));

        debug.setOutputAlphabet(Set.of('1', '2'));

        debug.setStates(s1, s2, s3, s4);

        debug.setMachineTransitions(t1, tnd2, t2, t3, t4, t5, t6, t7, t8, tnd9);

        return debug;
    }

    /**
     * Method for getting a state given its name and and array of states.
     *
     * @param newStates The array of <code>State</code>s to be looked through.
     * @param name      The name of the <code>State</code>.
     * @return The targeted <code>State</code>.
     */
    public static State getStateByName(ArrayList<State> newStates, String name) throws StateNotFound {
        State retVal = null;
        for (State s : newStates) {
            if (s.getName().equals(name)) {
                retVal = s;
            }
        }
        if (retVal == null) {
            throw new StateNotFound("State not found: getStateByName: " + name);
        }
        return retVal;
    }
}
