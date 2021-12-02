package app.oracleextractor.model;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

/**
 * Class represents a Mealy Machine, a kind of finite-state-machine.
 * The model used in this program is such that: <br />
 * A machine is a class that has:
 * <ul>
 *     <li>A set of states (Implemented as an Arraylist)</li>
 *     <li>An initial <code>State</code></li>
 *     <li>A current <code>State</code></li>
 *     <li>An <code>inputSequence</code>, representing the input sequence fed to the machine.</li>
 *     <li>A <code>producedOutput</code>, representing the output produced so far.</li>
 *     <li><code>lastOutput</code>, a variable that holds the last output produced in this machine.</li>
 *     <li>Two non-modifiable sets: (whether these will remain in the implementation as is, remains to be seen)
 *          <ul> <li><code>inputAlphabet</code>, A set of Character representing the input alphabet</li>
 *               <li><code>outputAlphabet</code>, A set of Character representing the output alphabet</li>
 *          </ul>
 *     </li>
 *     <li><code>pendingInput</code>, a boolean flag representing whether there is still input.</li>
 * </ul>
 *
 * @author zenAndroid
 */
public class Machine {
    ArrayList<State> states;
    State initialState;
    State currentState;
    ArrayList<Character> inputSequence;
    ArrayList<Character> producedOutput;
    Character lastOutput;
    Set<Character> inputAlphabet;
    Set<Character> outputAlphabet;
    Boolean pendingInput;

    /**
     * Constructor for an object of the <code>Machine</code> class.
     * <br />
     * Note that this constructor sets the <code>pendingInput</code> flag to true, since the input sequence is passed in as an argument to this constructor.
     *
     * @param states         The states.
     * @param initialState   The initial state of this machine.
     * @param inputSequence  The current sequence of inputs.
     * @param inputAlphabet  The input alphabet.
     * @param outputAlphabet The output alphabet.
     */
    public Machine(ArrayList<State> states, State initialState, ArrayList<Character> inputSequence, Set<Character> inputAlphabet, Set<Character> outputAlphabet) {
        this.states = states;
        this.initialState = initialState;
        this.currentState = initialState; // Notice that the current state is always initialized to the initial state.
        this.inputSequence = inputSequence;
        this.pendingInput = true; // Just set up the input sequence, so obviously there IS pending input.
        this.inputAlphabet = inputAlphabet;
        this.outputAlphabet = outputAlphabet;
        this.producedOutput = new ArrayList<>();
    }


    /**
     * Constructor for an object of the <code>Machine</code> class, when the input sequence isn't known at creation-time.
     * <p>
     * Note that this constructor sets the <code>pendingInput</code> flag to <code>false</code>.
     *
     * @param states         The states.
     * @param initialState   The initial state of this machine.
     * @param inputAlphabet  The input alphabet.
     * @param outputAlphabet The output alphabet.
     */
    public Machine(ArrayList<State> states, State initialState, Set<Character> inputAlphabet, Set<Character> outputAlphabet) {
        this.states = states;
        this.initialState = initialState;
        this.currentState = initialState;// Notice that the current state is always initialized to the initial state.
        this.inputAlphabet = inputAlphabet;
        this.outputAlphabet = outputAlphabet;
        this.inputSequence = new ArrayList<>(); // This is to avoid NullPointerExceptions
        this.pendingInput = false; /* Set to false because no inputSequence was passed in, so there is no input */
        this.producedOutput = new ArrayList<>();
    }

    /**
     * Default constructor, initializes no field except the <code>pendingInput</code> flag to <code>false</code>.
     */
    public Machine() {
        this.pendingInput = false;
        states = new ArrayList<>();
        initialState = new State();
        currentState = initialState;
        inputSequence = new ArrayList<>();
        inputAlphabet = Set.of();
        outputAlphabet = Set.of();
        producedOutput = new ArrayList<>();
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

        s1.setStateTransitions(t1, t2, tnd2);
        s2.setStateTransitions(t3, t4);
        s3.setStateTransitions(t5, t6);
        s4.setStateTransitions(t7, t8, tnd9);

        debug.setInitialState(s1);

        debug.setInputAlphabet(Set.of('a', 'b'));

        debug.setOutputAlphabet(Set.of('1', '2'));

        debug.setStates(s1, s2, s3, s4);

        return debug;
    }


    public static void main(String[] args) {
        // Creating the states;
        Machine sample = getDefaultMachine();
        System.out.println(sample.toDot());
        var input = new ArrayList<Character>();
        for (Character ch : "ba".toCharArray()) {
            input.add(ch);
        }
        sample.nonDeterministicConsume(input);
    }

    /**
     * Method for getting a state given its name and and array of states.
     *
     * @param newStates The array of <code>State</code>s to be looked through.
     * @param name      The name of the <code>State</code>.
     * @return The targeted <code>State</code>.
     */
    private static State getStateByName(ArrayList<State> newStates, String name) {
        State retVal = null;
        for (State s : newStates) {
            if (s.getName().equals(name)) {
                retVal = s;
            }
        }
        return retVal;
    }

    /**
     * Standard getter for the <code>State</code>s of this machine.
     *
     * @return <code>State</code>s of the machine.
     */
    public ArrayList<State> getStates() {
        return states;
    }

    /**
     * Standard setter for the Array of <code>State</code>s.
     * <br />
     * This setter then gives all the passed states the calling instantiated <code>Machine</code> as the 'owner' machine.
     *
     * @param states Array of <code>State</code>s
     */
    public void setStates(ArrayList<State> states) {
        this.states = new ArrayList<>();
        for (State s : states) { // These state do not have a machine as an owner, so we set it for them.
            s.setMach(this);
            this.states.add(s);
        }
        // TODO: Test this functionality!
        //  And I assume people that will come after me will (hopefully) make a proper testing suite.
    }

    /**
     * Setter for the states that utilizes the var-arg capabilities to allow for ease of construction of <code>State</code>s.
     * <br />
     * This setter then gives all the passed states the calling instantiated <code>Machine</code> as the 'owner' machine.
     *
     * @param states Array of <code>State</code>s
     */
    public void setStates(State... states) {
        this.states = new ArrayList<>(); // Not sure this is even needed ... EDIT: It's crucial, let it be

        for (State s : states) {
            s.setMach(this);
            this.states.add(s);
        }
    }

    public State getInitialState() {
        return initialState;
    }

    public void setInitialState(State initialState) {
        // This sets the initial state of the machine; it also sets the machine's current state;
        initialState.mach = this;
        this.initialState = initialState;
        this.currentState = initialState;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public ArrayList<Character> getInputSequence() {
        return inputSequence;
    }

    public void setInputSequence(ArrayList<Character> inputSequence) {
        this.inputSequence = inputSequence;
        this.pendingInput = true; // Input is now pending since we just set it!
    }

    public Set<Character> getInputAlphabet() {
        return inputAlphabet;
    }

    public void setInputAlphabet(Set<Character> inputAlphabet) {
        this.inputAlphabet = inputAlphabet;
    }

    public Set<Character> getOutputAlphabet() {
        return outputAlphabet;
    }

    public void setOutputAlphabet(Set<Character> outputAlphabet) {
        this.outputAlphabet = outputAlphabet;
    }

    public Boolean getPendingInput() {
        return pendingInput;
    }

    public void setPendingInput(Boolean pendingInput) {
        this.pendingInput = pendingInput;
    }

    public Character getLastOutput() {
        return lastOutput;
    }

    /**
     * Method to change the current state of the machine.
     *
     * @param destState Next state to change into.
     */
    public void changeState(State destState) {
        setCurrentState(destState);
    }

    /**
     * Returns whether there is still input to be processed by the machine.
     *
     * @return the boolean value that represents the availability of input.
     */
    public boolean isPending() {
        return getPendingInput(); // Auto-boxing helps us out!
    }

    public void nonDeterministicConsume(ArrayList<Character> input) {
        setInputSequence(input);
        while (isPending()) {
            var possibleTransitions = currentState.getApplicableTransitions();
            Transition actualTransition = chooseTransition(possibleTransitions);
            takeTransition(actualTransition);

        }
    }

    public void nonDeterministicConsume() {
        while (isPending()) {
            var possibleTransitions = currentState.getApplicableTransitions();
            Transition actualTransition = chooseTransition(possibleTransitions);
            takeTransition(actualTransition);

        }
    }

    public void nonDeterministicallyConsumeToken() {
        if (isPending()) {
            var possibleTransitions = currentState.getApplicableTransitions();
            Transition actualTransition = chooseTransition(possibleTransitions);
            takeTransition(actualTransition);

        } else {
            System.err.println("Cannot consume token: machine not pending");
        }
    }

    /**
     * To allow a <code>Transition</code> to be chosen, abstracted to its own method to allow for: <br />
     * a) random selection, <i>and</i> b) interactive selection.
     *
     * @param possibleTransitions the list of <code>Transition</code>s
     * @return the chosen <code>Transition</code>
     */
    private Transition chooseTransition(ArrayList<Transition> possibleTransitions) {
        var randomStream = new Random();
        return possibleTransitions.get(randomStream.nextInt(possibleTransitions.size()));
    }

    public void consumeToken() {
        // Offload the drudgery upon the current state and tell it to change things depending on its output
        // todo: might have to check this implementation out when trying to change to non-determinism
        if (isPending())
            currentState.consumeInputToken();

    }

    public void consumeEntirely() {
        nonDeterministicConsume(inputSequence);
    }

    /**
     * This method reads the next token and passes it down to its current state. <br />
     * The method removes also returns the removed object, so it can be used for this purpose.
     * todo: What if there IS no input? deal with this case. EDIT: DONE (I think ...)
     *
     * @return Next input token.
     */
    public Character getNextInputToken() {
        // Removes also returns the removed element, so we can use it to achieve two actions;
        // (knowing about the next input token, and removing it as well)
        Character tok = getInputSequence().remove(0);

        if (getInputSequence().isEmpty()) {
            setPendingInput(false); // Set to false given the fact the inputSequence is empty
        }
        return tok;
    }

    /**
     * Method to extract all of the output produced by this machine.
     *
     * @return A string containing the entire output.
     */
    public String getProducedOutput() {
        StringBuilder retVal = new StringBuilder(producedOutput.size());
        for (Character ch : producedOutput) {
            retVal.append(ch);
        }
        return retVal.toString();
    }

    /**
     * Method to get the input Sequence as a string.
     *
     * @return String representing output
     */
    public String getInputAsString() {
        StringBuilder retVal = new StringBuilder(inputSequence.size());
        for (Character ch : inputSequence) {
            retVal.append(ch);
        }
        return retVal.toString();
    }

    /**
     * Method to simulate taking this transition;
     *
     * @param transition The <code>Transition</code> that will be traversed by the machine.
     */
    public void takeTransition(Transition transition) {
        if (transition.getSourceState().equals(getCurrentState())) {
            transition.setTaken();
            processOutput(transition.getTransitionOutput());
            setCurrentState(transition.getDestinationState());
        } else {
            System.err.println("Transition not applicable: takeTransition " + transition);
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
    public String toDot() {
        StringBuilder b = new StringBuilder("digraph Automaton {\n");
        b.append("    node [shape=point] INIT;\n");
        b.append("    ").append(currentState.getName()).append(" ").append("[shape=\"doublecircle\"];\n");
        b.append("    node [shape=circle];\n");
        b.append("    rankdir = LR;\n");
        b.append("    INIT -> ").append(getInitialState().getName()).append(";\n");
        ArrayList<State> machineStates = getStates();
        for (State s : machineStates) {
            var stateTranses = s.getStateTransitions();
            for (Transition t : stateTranses) { // IF transition.visited() { append ("quelquechose syntax dot pour afficher le arrow en rouge") }
                b.append("    ").append(s.getName()).append(" -> ").append(t.getDestinationState().getName()).append(" ");
                b.append("[label=\"").append(t.getTransitionTrigger())
                        .append("/")
                        .append(t.getTransitionOutput())
                        .append(t.wasTaken() ? "\", color=red];" : "\"];")
                        .append("\n");
            }
        }
        return b.append("}\n").toString();
    }

    /**
     * To get access to all of the transitions linked to the machine.
     *
     * @return <code>Transition</code>s of the machine.
     */
    public ArrayList<Transition> getAllTransitions() {
        ArrayList<Transition> returnValue = new ArrayList<>();
        for (State state : getStates()) {
            returnValue.addAll(state.getStateTransitions());
        }
        return returnValue;
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
            newStates.add(new State(s.getName())); // Adding the new states based on the name of the old ones.
        }
        retVal.setStates(newStates); // Setting the new States in the new machine.
        // Setting the new machine's initial state.
        retVal.setInitialState(getStateByName(newStates, getInitialState().getName()));

        // Now we will create the transitions, this might get hairy, but i will try to
        // compactify the operations by making use of the constructors that allow me to set the source and destinaton
        // states

        var newTransitions = new ArrayList<Transition>();
        for (Transition t : getAllTransitions()) {
            newTransitions.add(new Transition(t.getTransitionTrigger(),
                    t.getTransitionOutput(),
                    getStateByName(newStates, t.getSourceState().getName()),
                    getStateByName(newStates, t.getDestinationState().getName())));
        } // Transitions added, now comes the dicey part ...adding the correct transitions to the correct states ...

        for (State s : retVal.getStates()) {
            // For every state of this new machine, we have to add to it the transition that come from it
            var newStateTransitions = new ArrayList<Transition>();
            // We go through the transitions and see the ones
            // that have the same states this one on the sourceState field ...
            for (Transition t : newTransitions) {
                if (t.getSourceState().getName().equals(s.getName())) {
                    newStateTransitions.add(t);
                }
            }
            s.setStateTransitions(newStateTransitions);
        }
        return retVal;
    }
}
