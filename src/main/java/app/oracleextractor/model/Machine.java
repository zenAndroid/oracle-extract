package app.oracleextractor.model;

import app.oracleextractor.model.exceptions.*;
import app.oracleextractor.model.utils.Trace;
import app.oracleextractor.model.utils.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;

public class Machine {

    private ArrayList<State> states;
    private State initialState;
    private State currentState;
    private ArrayList<Character> inputSequence;
    private ArrayList<Character> producedOutput;
    private Character lastOutput;
    private Set<Character> inputAlphabet;
    private Set<Character> outputAlphabet;
    private Boolean pendingInput;
    private ArrayList<Transition> machineTransitions;
    private Trace machineTrace;

    public Machine(ArrayList<State> states, State initialState, ArrayList<Character> inputSequence, Set<Character> inputAlphabet, Set<Character> outputAlphabet) {
        this.states = states;
        this.initialState = initialState;
        this.currentState = initialState; // Notice that the current state is always initialized to the initial state.
        this.inputSequence = inputSequence;
        this.pendingInput = true; // Just set up the input sequence, so obviously there IS pending input.
        this.inputAlphabet = inputAlphabet;
        this.outputAlphabet = outputAlphabet;
        this.producedOutput = new ArrayList<>();
        this.machineTransitions = new ArrayList<>();
        this.machineTrace = new Trace();
    }

    public Machine(ArrayList<State> states, State initialState, Set<Character> inputAlphabet, Set<Character> outputAlphabet, ArrayList<Transition> machineTransitions) {
        this.states = states;
        this.initialState = initialState;
        this.currentState = initialState; // Notice that the current state is always initialized to the initial state.
        this.inputAlphabet = inputAlphabet;
        this.outputAlphabet = outputAlphabet;
        this.machineTransitions = machineTransitions;
        this.pendingInput = false;
        this.producedOutput = new ArrayList<>();
        this.machineTrace = new Trace();
    }

    public Machine(ArrayList<State> states, State initialState, Set<Character> inputAlphabet, Set<Character> outputAlphabet) {
        this.states = states;
        this.initialState = initialState;
        this.currentState = initialState;// Notice that the current state is always initialized to the initial state.
        this.inputAlphabet = inputAlphabet;
        this.outputAlphabet = outputAlphabet;
        this.inputSequence = new ArrayList<>(); // This is to avoid NullPointerExceptions
        this.pendingInput = false; /* Set to false because no inputSequence was passed in, so there is no input */
        this.producedOutput = new ArrayList<>();
        this.machineTransitions = new ArrayList<>();
        this.machineTrace = new Trace();
    }

    public Machine() {
        this.pendingInput = false;
        states = new ArrayList<>();
        initialState = State.getState();
        currentState = initialState;
        inputSequence = new ArrayList<>();
        inputAlphabet = Set.of();
        outputAlphabet = Set.of();
        producedOutput = new ArrayList<>();
        this.machineTransitions = new ArrayList<>();
        this.machineTrace = new Trace();
    }

    /*
     * TODO: Create a function that separately initializes all of the ad-hoc member variable, instead of duplicating code/work.
     */

    public static void main(String[] args) {
        // Creating the states;
        Machine sample = Utilities.getDefaultMachine();
        Machine simpleMachine = new Machine();
        State one = State.getState("1");
        State two = State.getState("2");
        Transition temp = Transition.getInstance('a', '1', one, two);
        Transition temp1 = Transition.getInstance('a', '2', one, two);
        Transition temp2 = Transition.getInstance('b', '2', two, one);
        Transition temp3 = Transition.getInstance('b', '1', two, one);
        simpleMachine.setInitialState(one);

        simpleMachine.setInputAlphabet(Set.of('a', 'b'));

        simpleMachine.setOutputAlphabet(Set.of('1', '2'));

        simpleMachine.setStates(one, two);

        simpleMachine.setMachineTransitions(temp, temp1, temp2, temp3);

        // System.out.println(sample.toDot());
        try {
            // simpleMachine.nonDeterministicConsume(Utilities.stringToList("ab"));
            System.out.println(Utilities.evalMachine(simpleMachine, Utilities.stringToList("aba")).size());
        } catch (/*BadInputException | */StuckMachineException e) {
            e.printStackTrace();
        }
    }

    public Trace getMachineTrace() {
        return machineTrace;
    }

    public void setMachineTrace(Trace machineTrace) {
        this.machineTrace = machineTrace;
    }


    public ArrayList<State> getStates() {
        return states;
    }


    public void setStates(ArrayList<State> argStates) {
        states = new ArrayList<>();
        for (State s : argStates) { // These state do not have a machine as an owner, so we set it for them.
            states.add(s);
        }
        // TODO: Test this functionality!
        //  And I assume people that will come after me will (hopefully) make a proper testing suite.
    }


    public void setStates(State... argStates) {
        states = new ArrayList<>(); // Not sure this is even needed ... EDIT: It's crucial, let it be
        for (State s : argStates) {
            states.add(s);
        }
    }

    public State getInitialState() {
        return initialState;
    }


    public void setInitialState(State argInitialState) {
        initialState = argInitialState;
        currentState = argInitialState;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State argState) {
        currentState = argState;
    }

    public ArrayList<Character> getInputSequence() {
        return inputSequence;
    }


    public void setInputSequence(ArrayList<Character> argInputSequence) throws BadInputException {
        if (inputAlphabet.containsAll(argInputSequence)) {
            inputSequence = argInputSequence;
            pendingInput = true; // Input is now pending since we just set it!
        } else {
            throw new BadInputException("Input sequence contains element(s) not in input alphabet, argInputSequence: " +
                    argInputSequence + ", inputAlphabet: " + inputAlphabet);
        }
    }

    public Set<Character> getInputAlphabet() {
        return inputAlphabet;
    }

    public void setInputAlphabet(Set<Character> argInputAlphabet) {
        inputAlphabet = argInputAlphabet;
    }

    public Set<Character> getOutputAlphabet() {
        return outputAlphabet;
    }

    public void setOutputAlphabet(Set<Character> argOutputAlphabet) {
        outputAlphabet = argOutputAlphabet;
    }

    public Boolean getPendingInput() {
        return pendingInput;
    }

    public void setPendingInput(Boolean argPendingInput) {
        pendingInput = argPendingInput;
    }

    public Character getLastOutput() {
        return lastOutput;
    }

    public ArrayList<Transition> getMachineTransitions() {
        return machineTransitions;
    }

    public void setMachineTransitions(ArrayList<Transition> transitions) {
        machineTransitions = transitions;
    }

    public void setMachineTransitions(Transition... transitions) {
        machineTransitions = new ArrayList<>(Arrays.asList(transitions));
        // Boy this syntax is ... interesting ... i think i almost prefer the old natural way ...
    }


    public Boolean isPending() {
        return getPendingInput();
    }

    public void nonDeterministicConsume(ArrayList<Character> input) throws BadInputException {
        setInputSequence(input);
        nonDeterministicConsume();
    }

    public void nonDeterministicConsume() {
        machineTrace.clear(); // Clear the trace.
        while (isPending()) {
            try {
                ArrayList<Transition> possibleTransitions = currentState
                        .getApplicableTransitions(getNextInputToken(), machineTransitions);
                Transition actualTransition;

                actualTransition = chooseTransition(possibleTransitions, TranSelection_Policy.RANDOM_SELECTION);
                takeTransition(actualTransition);
            } catch (NoTransitionFound | TransitionNotApplicable e) {
                // TODO: Is there something else todo here?
                e.printStackTrace();
            }
        }
    }

    public void nonDeterministicallyConsumeToken() throws NoPendingInput {
        if (isPending()) {
            ArrayList<Transition> possibleTransitions = currentState
                    .getApplicableTransitions(getNextInputToken(), machineTransitions);
            Transition actualTransition;
            try {
                actualTransition = chooseTransition(possibleTransitions, TranSelection_Policy.INTERACTIVE_SELECTION);
                takeTransition(actualTransition);
            } catch (NoTransitionFound | TransitionNotApplicable e) {
                // TODO: Is there something else todo here?
                e.printStackTrace();
            }
        } else {
            throw new NoPendingInput("Cannot consume token: machine not pending");
        }
    }


    private Transition chooseTransition(ArrayList<Transition> possibleTransitions,
                                        TranSelection_Policy selectionPolicy) throws NoTransitionFound {
        Random randomStream = new Random();
        Transition chosenTransition = null;
        if (possibleTransitions.isEmpty()) {
            throw new NoTransitionFound("No transitions found from the current state, State:"
                    + currentState.stateName() + " possibleTransitions: " + possibleTransitions);
        } else if (possibleTransitions.size() == 1) {
            return possibleTransitions.get(0);
        } else {
            if (selectionPolicy == TranSelection_Policy.INTERACTIVE_SELECTION) {
                chosenTransition = Utilities.getTransitionChooserPopup(possibleTransitions);
            } else if (selectionPolicy == TranSelection_Policy.RANDOM_SELECTION) {
                chosenTransition = possibleTransitions.get(randomStream.nextInt(possibleTransitions.size()));
            }
        }
        return chosenTransition;
    }


    public Character getNextInputToken() {
        // Removes also returns the removed element, so we can use it to achieve two actions;
        // (knowing about the next input token, and removing it as well)
        Character tok = getInputSequence().remove(0);

        if (getInputSequence().isEmpty()) {
            setPendingInput(false); // Set to false given the fact the inputSequence is empty
        }
        return tok;
    }

    public ArrayList<Character> getProducedOutput() {
        return producedOutput;
    }

    /**
     * Method to simulate taking this transition; also adds the transition to the running machine's trace.
     *
     * @param transition The <code>Transition</code> that will be traversed by the machine.
     */
    public void takeTransition(Transition transition) throws TransitionNotApplicable {
        if (transition.sourceState().stateName().equals(getCurrentState().stateName())) {
            machineTrace.addTransition(transition);
            transition.setTaken();
            processOutput(transition.transitionOutput());
            setCurrentState(transition.destinationState());
        } else {
            throw new TransitionNotApplicable("Transition not applicable from this state. arg: "
                    + transition
                    + " , Current State: "
                    + getCurrentState()
                    + " , Source state: "
                    + transition.sourceState());
        }
    }

    /**
     * Methods implementing the business logic that deals with incoming input.
     * Depending on the program/application being developed, the machine's reaction to a transition can change.
     * This method allows the description of such a reaction.
     * For our test case right here, we only want to print it out.
     * Example: Maybe on transition, our machine
     * <ol>
     *     <li>contacts an API and sends it a specific header request</li>
     *     <li>appends an Object to a Collection</li>
     *     <li>prints it out to a specific stream (file/StringBuilder/socket/etc.)</li>
     *     <li>or some other operation ...</li>
     * </ol>
     *
     * @param sourceTransition The output of the machine, given its global state.
     */
    public void processOutput(Character sourceTransition) {
        lastOutput = sourceTransition; // This is the lastoutput
        producedOutput.add(sourceTransition); // Add the output to the list of outputs we've outputted.
        System.out.println(sourceTransition); // Print it; todo: this is bad and hardcoded, is there even a fix?
        // The issue here is that a machine might do something else with this output *other* than outputting it,
        // wonder if i can use the Function class to achieve some flexibility, but that for after i get it working.
    }

    /**
     * @return A <a href="http://www.research.att.com/sw/tools/graphviz/" target="_top">Graphviz Dot</a>
     * representation of this automaton.
     */
    public String toDot() { // TODO: honestly, this can go to utilities man
        StringBuilder b = new StringBuilder("digraph Automaton {\n");
        b.append("    node [shape=point] INIT;\n");
        b.append("    ").append(currentState.stateName()).append(" ").append("[shape=\"doublecircle\"];\n");
        b.append("    node [shape=circle];\n");
        b.append("    rankdir = LR;\n");
        b.append("    INIT -> ").append(getInitialState().stateName()).append(";\n");
        ArrayList<State> machineStates = getStates();
        for (State s : machineStates) {
            var stateTranses = getTransitionsFromState(s); // The transintions that come from this state.
            for (Transition t : stateTranses) {
                b.append("    ").append(s.stateName()).append(" -> ").append(t.destinationState().stateName()).append(" ");
                b.append("[label=\"").append(t.transitionTrigger())
                        .append("/")
                        .append(t.transitionOutput())
                        .append(t.wasTaken() ? "\", color=red];" : "\"];")
                        .append("\n");
            }
        }
        return b.append("}\n").toString();
    }

    private ArrayList<Transition> getTransitionsFromState(State s) {
        ArrayList<Transition> transitions = new ArrayList<>();
        for (Transition t : machineTransitions) {
            if (t.sourceState().equals(s)) {
                // This transition comes from this state, so OK to add it
                transitions.add(t);
            }
        }
        return transitions;
    }

    /**
     * This function returns a clone of this machine.
     * NOTE that you can only use this function sensibly <i>AFTER</i> the machine has been properly defined;
     * E.g: the machine has valid <code>State</code>s, a valid initial <code>State</code>, etc.<br />
     * Not doing this results in UB.
     *
     * @return <code>retval</code>, a clone of this machine.
     */
    public Machine makeMachineCopy() {
        var retVal = new Machine(); // The new clone of this machine.
        retVal.setInputAlphabet(getInputAlphabet()); // Yeah yeah
        retVal.setOutputAlphabet(getOutputAlphabet()); // Yeah yeah
        var newStates = new ArrayList<State>(); // The clones of the states.
        for (State s : getStates()) {
            newStates.add(State.getState(s.stateName())); // Adding the NEW states based on the name of the old ones.
            // Based on the NAMES of the OLD states, NOT THEIR REFERENCES!!!
        }
        retVal.setStates(newStates); // Setting the new States in the new machine.
        // Setting the new machine's initial state.
        try {
            retVal.setInitialState(Utilities.getStateByName(newStates, getInitialState().stateName()));
            retVal.setCurrentState(Utilities.getStateByName(newStates, getCurrentState().stateName()));
        } catch (StateNotFound e) {
            // Something went terribly wrong ...
            e.printStackTrace();
        }

        // Now we will create the transitions, this might get hairy, but i will try to
        // compactify the operations by making use of the constructors that allow me to set the source and destinaton
        // states

        var newTransitions = new ArrayList<Transition>();
        for (Transition t : machineTransitions) {
            try {
                newTransitions.add(Transition.getInstance(t.transitionTrigger(),
                        t.transitionOutput(),
                        Utilities.getStateByName(newStates, t.sourceState().stateName()),
                        Utilities.getStateByName(newStates, t.destinationState().stateName())));
            } catch (StateNotFound e) {
                // Todo: some thing to do?
                e.printStackTrace();
            }
        } // Transitions added, now comes the dicey part ...adding the correct transitions to the correct states ...
        // ToDo: Future me: what de he mean by that last comment?

        retVal.setMachineTransitions(newTransitions);
        return retVal;
    }

    @Override
    public String toString() {
        return "Machine{" +
                "states=" + states +
                ", initialState=" + initialState +
                ", currentState=" + currentState +
                ", inputSequence=" + inputSequence +
                ", producedOutput=" + producedOutput +
                ", lastOutput=" + lastOutput +
                ", inputAlphabet=" + inputAlphabet +
                ", outputAlphabet=" + outputAlphabet +
                ", pendingInput=" + pendingInput + '\n' +
                ", machineTransitions=" + machineTransitions +
                '}';
    }

    private enum TranSelection_Policy {
        RANDOM_SELECTION,
        INTERACTIVE_SELECTION
    }
}
