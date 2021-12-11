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
        // Machine sample = Utilities.getDefaultMachine();
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

        try {
            simpleMachine.setInputSequence(Utilities.stringToList("abab"));
        } catch (BadInputException e) {
            e.printStackTrace();
        }

        // try {
        //     System.out.println("Size of the trace: " + Utilities.evalMachine(simpleMachine, Utilities.stringToList("ab")).size());
        // } catch (StuckMachineException e) {
        //     e.printStackTrace();
        // }
        System.out.println("Hello");
        simpleMachine.nonDeterministicConsume(false);
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
        states.addAll(argStates);
        // TODO: Test this functionality!
        //  And I assume people that will come after me will (hopefully) make a proper testing suite.
    }

    public void setStates(State... argStates) {
        states.addAll(Arrays.asList(argStates));
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

    public Character getLastOutput() throws NoLastChange {
        return machineTrace.getLastOutput();
    }

    public ArrayList<Transition> getMachineTransitions() {
        return machineTransitions;
    }

    public void setMachineTransitions(ArrayList<Transition> transitions) {
        for (Transition t : transitions) {
            machineTransitions.add(t);
            t.sourceState().addOutgoingTransition(t);
            t.destinationState().addIncomingTransition(t);
        }
    }

    public void setMachineTransitions(Transition... transitions) {
        setMachineTransitions(new ArrayList<>(Arrays.asList(transitions)));
    }

    public Boolean isPending() {
        return getPendingInput();
    }

    public void nonDeterministicConsume(ArrayList<Character> input, boolean graphicalContext) throws BadInputException {
        setInputSequence(input);
        nonDeterministicConsume(graphicalContext);
    }

    public void nonDeterministicConsume(boolean graphicalContext) {
        machineTrace.clear(); // Clear the trace.
        while (isPending()) {
            try {
                ArrayList<Transition> possibleTransitions = Utilities.getApplicableTransitions(currentState, getNextInputToken());
                TranSelection_Policy selection_policy = graphicalContext ? TranSelection_Policy.INTERACTIVE_SELECTION
                        : TranSelection_Policy.RANDOM_SELECTION;
                Transition actualTransition = chooseTransition(possibleTransitions, selection_policy);
                takeTransition(actualTransition);
            } catch (NoTransitionFound | TransitionNotApplicable e) {
                // TODO: Is there something else todo here?
                e.printStackTrace();
            }
        }
    }

    public void nonDeterministicallyConsumeToken(boolean graphicalContext) throws NoPendingInput {
        if (isPending()) {
            try {
                ArrayList<Transition> possibleTransitions = Utilities.getApplicableTransitions(currentState, getNextInputToken());
                TranSelection_Policy selection_policy = graphicalContext ? TranSelection_Policy.INTERACTIVE_SELECTION
                        : TranSelection_Policy.RANDOM_SELECTION;
                Transition actualTransition = chooseTransition(possibleTransitions, selection_policy);
                takeTransition(actualTransition);
            } catch (NoTransitionFound | TransitionNotApplicable e) {
                // TODO: Is there something else todo here?
                e.printStackTrace();
            }
        } else {
            throw  new NoPendingInput("Cannot consume token: machine not pending.");
        }
    }

    private Transition chooseTransition(ArrayList<Transition> possibleTransitions,
                                        TranSelection_Policy selectionPolicy) throws NoTransitionFound {
        Random randomStream = new Random();
        Transition chosenTransition = null;
        if (possibleTransitions.isEmpty()) {
            throw new NoTransitionFound("No transitions found from the current state, State: "
                    + currentState.stateName() + ", possibleTransitions: " + possibleTransitions);
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

    public void setProducedOutput(ArrayList<Character> producedOutput) {
        this.producedOutput = producedOutput;
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
            var stateTranses = s.outGoing(); // The transintions that come from this state.
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

    /**
     * This function returns a clone of this machine.
     * NOTE that you can only use this function sensibly <i>AFTER</i> the machine has been properly defined;
     * E.g: the machine has valid <code>State</code>s, a valid initial <code>State</code>, etc.<br />
     * Not doing this results in UB.
     *
     * @return <code>retval</code>, a clone of this machine.
     */
    public Machine makeMachineCopy() {
        var retVal = new Machine();
        retVal.setInputAlphabet(getInputAlphabet());
        retVal.setOutputAlphabet(getOutputAlphabet());
        var newStates = new ArrayList<State>();
        for (State s : getStates()) {
            newStates.add(State.getState(s.stateName()));
        }
        retVal.setStates(newStates);
        try {
            retVal.setInitialState(Utilities.getStateByName(newStates, getInitialState().stateName()));
        } catch (StateNotFound e) {
            e.printStackTrace();
        }

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
        }
        retVal.setMachineTransitions(newTransitions);
        return retVal;
    }

    public Machine makeCurrentMachineCopy() throws BadInputException, StateNotFound {
        Machine retVal = new Machine();
        // private ArrayList<State> states;
        // private State initialState;
        // private State currentState;
        // private ArrayList<Character> inputSequence;
        // private ArrayList<Character> producedOutput;
        // private Character lastOutput;
        // private Set<Character> inputAlphabet;
        // private Set<Character> outputAlphabet;
        // private Boolean pendingInput;
        // private ArrayList<Transition> machineTransitions;
        // private Trace machineTrace;
        ArrayList<State> newMachineStates = new ArrayList<>(states.size());
        for (State state : states) {
            newMachineStates.add(State.getState(state.stateName()));
        }
        ArrayList<Transition> newMachineTransitions = new ArrayList<>();
        for (Transition t : machineTransitions) {
            newMachineTransitions.add(Transition.getInstance(t.transitionTrigger(), t.transitionOutput(),
                    Utilities.getStateByName(newMachineStates, t.sourceState().stateName()),
                    Utilities.getStateByName(newMachineStates, t.destinationState().stateName())));
        }
        State newMachineInitialState = Utilities.getStateByName(newMachineStates, getInitialState().stateName());
        State newMachineCurrentState = newMachineInitialState;
        ArrayList<Character> newMachineInputSequence = new ArrayList<>(inputSequence);
        ArrayList<Character> newMachineProducedOutput = new ArrayList<>(producedOutput);
        Set<Character> newMachineInputAlphabet = inputAlphabet;
        Set<Character> newMachineOutputAlphabet = outputAlphabet;
        Boolean newMachinePendingInput = pendingInput;
        Trace newMachineTrace = new Trace(machineTrace);
        retVal.setInputAlphabet(newMachineInputAlphabet);
        retVal.setOutputAlphabet(newMachineOutputAlphabet);
        retVal.setStates(newMachineStates);
        retVal.setInitialState(newMachineInitialState);
        retVal.setCurrentState(newMachineCurrentState);
        retVal.setInputSequence(newMachineInputSequence);
        retVal.setProducedOutput(newMachineProducedOutput);
        retVal.setPendingInput(newMachinePendingInput);
        retVal.setMachineTransitions(newMachineTransitions);
        retVal.setMachineTrace(newMachineTrace);

        return retVal;
    }

    private enum TranSelection_Policy {
        RANDOM_SELECTION,
        INTERACTIVE_SELECTION
    }
}
