package app.oracleextractor.model;

import java.util.ArrayList;
import java.util.Arrays;
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
    ArrayList<Transition> machineTransitions;

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
        this.machineTransitions = new ArrayList<>();
    }

    /**
     * <code>Machine</code> Constructor. <br />
     *
     * @param states             The <code>State</code>s that belong to this <code>Machine</code>.
     * @param initialState       The initial <code>State</code>.
     * @param inputAlphabet      The <code>Arraylist</code> of <code>Character</code>s representing the input alphabet.
     * @param outputAlphabet     The <code>Arraylist</code> of <code>Character</code>s representing the output alphabet.
     * @param machineTransitions The <code>Arraylist</code> of <code>Transition</code>s in this machine.
     */
    public Machine(ArrayList<State> states, State initialState, Set<Character> inputAlphabet, Set<Character> outputAlphabet, ArrayList<Transition> machineTransitions) {
        this.states = states;
        this.initialState = initialState;
        this.currentState = initialState; // Notice that the current state is always initialized to the initial state.
        this.inputAlphabet = inputAlphabet;
        this.outputAlphabet = outputAlphabet;
        this.machineTransitions = machineTransitions;
        this.pendingInput = false;
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
        this.machineTransitions = new ArrayList<>();
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
        this.machineTransitions = new ArrayList<>();
    }

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

    public static void main(String[] args) {
        // Creating the states;
        Machine sample = getDefaultMachine();
        System.out.println(sample.toDot());
        sample.nonDeterministicConsume(stringToList("baabababba"));
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
        if (retVal == null) {
            System.err.println("State not found: getStateByName: " + name);
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
     * @param argStates Array of <code>State</code>s
     */
    public void setStates(ArrayList<State> argStates) {
        states = new ArrayList<>();
        for (State s : argStates) { // These state do not have a machine as an owner, so we set it for them.
            s.setMach(this);
            states.add(s);
        }
        // TODO: Test this functionality!
        //  And I assume people that will come after me will (hopefully) make a proper testing suite.
    }

    /**
     * Setter for the states that utilizes the var-arg capabilities to allow for ease of construction of <code>State</code>s.
     * <br />
     * This setter then gives all the passed states the calling instantiated <code>Machine</code> as the 'owner' machine.
     *
     * @param argStates Array of <code>State</code>s
     */
    public void setStates(State... argStates) {
        states = new ArrayList<>(); // Not sure this is even needed ... EDIT: It's crucial, let it be

        for (State s : argStates) {
            s.setMach(this);
            states.add(s);
        }
    }

    public State getInitialState() {
        return initialState;
    }

    /**
     * This sets the initial state of the machine; it also sets the machine's current state;*
     *
     * @param argInitialState Argument <code>State</code>
     */
    public void setInitialState(State argInitialState) {
        argInitialState.mach = this;
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

    /**
     * Sets the <code>Machine</code>'s input sequence.
     *
     * @param argInputSequence <code>Arraylist</code> representing the input.
     */
    public void setInputSequence(ArrayList<Character> argInputSequence) {
        inputSequence = argInputSequence;
        pendingInput = true; // Input is now pending since we just set it!
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

    /**
     * Returns whether there is still input to be processed by the machine.
     *
     * @return the boolean value that represents the availability of input.
     */
    public Boolean isPending() {
        return getPendingInput();
    }

    public void nonDeterministicConsume(ArrayList<Character> input) {
        setInputSequence(input);
        nonDeterministicConsume(); // Embarrassing deduplication of logic ...
    }

    public void nonDeterministicConsume() {
        while (isPending()) {
            var possibleTransitions = currentState.getApplicableTransitions();
            Transition actualTransition = chooseTransition(possibleTransitions);
            takeTransition(actualTransition);
        }
    }
glkjdflgjlkdsjg;lkdsfjg;lkfdjg;lsdkfjg;ldkfjgs;lfdkjg;lfdkjg
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
        if (possibleTransitions.size() == 0) {
            System.err.println("No transitions found from the current state, State:" + currentState.getName() + "possibleTransitions: " + possibleTransitions);
        }
        if (possibleTransitions.size() == 1){
            return possibleTransitions.get(0);
        }
        else{
            return possibleTransitions.get(randomStream.nextInt(possibleTransitions.size()));
        }
        // Todo: Implement the transition chooser here!
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
            var stateTranses = getTransitionsFromState(s); // The transintions that come from this state.
            for (Transition t : stateTranses) {
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

    private ArrayList<Transition> getTransitionsFromState(State s) {
        ArrayList<Transition> transitions = new ArrayList<>();
        for (Transition t : machineTransitions) {
            if (t.getSourceState().equals(s)) {
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
            newStates.add(new State(s.getName())); // Adding the new states based on the name of the old ones.
        }
        retVal.setStates(newStates); // Setting the new States in the new machine.
        // Setting the new machine's initial state.
        retVal.setInitialState(getStateByName(newStates, getInitialState().getName()));

        // Now we will create the transitions, this might get hairy, but i will try to
        // compactify the operations by making use of the constructors that allow me to set the source and destinaton
        // states

        var newTransitions = new ArrayList<Transition>();
        for (Transition t : machineTransitions) {
            newTransitions.add(new Transition(t.getTransitionTrigger(),
                    t.getTransitionOutput(),
                    getStateByName(newStates, t.getSourceState().getName()),
                    getStateByName(newStates, t.getDestinationState().getName())));
        } // Transitions added, now comes the dicey part ...adding the correct transitions to the correct states ...

        retVal.setMachineTransitions(newTransitions);
        System.err.println(retVal);
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
}
