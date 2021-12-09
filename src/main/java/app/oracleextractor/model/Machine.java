package app.oracleextractor.model;

import app.oracleextractor.model.exceptions.*;
import app.oracleextractor.model.utils.StateTransition;
import app.oracleextractor.model.utils.Trace;
import app.oracleextractor.model.utils.Utilities;

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
 *     <li><code>Trace</code>, a data structure that holds all of the <code>StateTransition</code>s that have occured</li>
 * </ul>
 *
 * @author zenAndroid
 */
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

    /**
     * Constructor for an object of the <code>Machine</code> class.
     * <br />
     * Note that this constructor sets the <code>pendingInput</code> flag to true, since the input sequence is passed in as an argument to this constructor. <br />
     * This also initializes the necessary member variables.
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
        this.machineTrace = new Trace();
    }

    /**
     * <code>Machine</code> Constructor. <br /> This also initializes the necessary member variables.
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
        this.machineTrace = new Trace();
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
        this.machineTrace = new Trace();
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
        this.machineTrace = new Trace();
    }

    /*
     * TODO: Create a function that separately initializes all of the ad-hoc member variable, instead of duplicating code/work.
     */


    public static void main(String[] args) {
        // Creating the states;
        Machine sample = Utilities.getDefaultMachine();
        Machine simpleMachine = new Machine();
        State one = new State("1");
        State two = new State("2");
        Transition temp = new Transition('a', '1', one, two);
        Transition temp1 = new Transition('a', '2', one, two);
        Transition temp2 = new Transition('b', '2', two, one);
        Transition temp3 = new Transition('b', '1', two, one);
        simpleMachine.setInitialState(one);

        simpleMachine.setInputAlphabet(Set.of('a', 'b'));

        simpleMachine.setOutputAlphabet(Set.of('1', '2'));

        simpleMachine.setStates(one, two);

        simpleMachine.setMachineTransitions(temp, temp1, temp2, temp3);

        // System.out.println(sample.toDot());
        try {
            // simpleMachine.nonDeterministicConsume(Utilities.stringToList("ab"));
            System.out.println(Utilities.evalMachine(simpleMachine, Utilities.stringToList("ab")));
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

    /**
     * Returns whether there is still input to be processed by the machine.
     *
     * @return the boolean value that represents the availability of input.
     */
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
            ArrayList<Transition> possibleTransitions = currentState.getApplicableTransitions();
            Transition actualTransition;
            try {
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
            ArrayList<Transition> possibleTransitions = currentState.getApplicableTransitions();
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

    /**
     * To allow a <code>Transition</code> to be chosen, abstracted to its own method to allow for: <br />
     * a) random selection, <i>and</i> b) interactive selection.
     *
     * @param possibleTransitions the list of <code>Transition</code>s
     * @return the chosen <code>Transition</code>
     */
    private Transition chooseTransition(ArrayList<Transition> possibleTransitions,
                                        Machine.TranSelection_Policy selectionPolicy) throws NoTransitionFound {
        Random randomStream = new Random();
        Transition chosenTransition = null;
        if (possibleTransitions.isEmpty()) {
            throw new NoTransitionFound("No transitions found from the current state, State:"
                    + currentState.getName() + " possibleTransitions: " + possibleTransitions);
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
    public ArrayList<Character> getProducedOutput() {
        return producedOutput;
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
     * Method to simulate taking this transition; also adds the transition to the running machine's trace.
     *
     * @param transition The <code>Transition</code> that will be traversed by the machine.
     */
    public void takeTransition(Transition transition) throws TransitionNotApplicable {
        if (transition.getSourceState().getName().equals(getCurrentState().getName())) {
            StateTransition newST = new StateTransition(currentState, transition.getDestinationState(), transition);
            machineTrace.addSTransition(newST);
            transition.setTaken();
            processOutput(transition.getTransitionOutput());
            setCurrentState(transition.getDestinationState());
        } else {
            throw new TransitionNotApplicable("Transition not applicable from this state. arg: "
                    + transition
                    + " , Current State: "
                    + getCurrentState()
                    + " , Source state: "
                    + transition.getSourceState());
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
            newStates.add(new State(s.getName())); // Adding the NEW states based on the name of the old ones.
            // Based on the NAMES of the OLD states, NOT THEIR REFERENCES!!!
        }
        retVal.setStates(newStates); // Setting the new States in the new machine.
        // Setting the new machine's initial state.
        try {
            retVal.setInitialState(Utilities.getStateByName(newStates, getInitialState().getName()));
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
                newTransitions.add(new Transition(t.getTransitionTrigger(),
                        t.getTransitionOutput(),
                        Utilities.getStateByName(newStates, t.getSourceState().getName()),
                        Utilities.getStateByName(newStates, t.getDestinationState().getName())));
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
