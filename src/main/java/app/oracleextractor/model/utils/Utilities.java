package app.oracleextractor.model.utils;

import app.oracleextractor.model.Machine;
import app.oracleextractor.model.State;
import app.oracleextractor.model.Transition;
import app.oracleextractor.model.exceptions.NoTransitionFound;
import app.oracleextractor.model.exceptions.StateNotFound;
import app.oracleextractor.model.exceptions.TransitionNotFound;
import app.oracleextractor.reader.automatonLexer;
import app.oracleextractor.reader.automatonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Set;

public class Utilities {

    /**
     * Makes a Popup appear with an OK button, and nothing else.
     *
     * @param title   The title.
     * @param message The message.
     * @return The JavaFX stage.
     */
    public static Stage getPopup(String title, String message) {
        Stage retVal = new Stage();
        retVal.initModality(Modality.APPLICATION_MODAL);
        retVal.setTitle(title);
        Label text = new Label(message);
        Button okButton = new Button("Ok");
        okButton.setOnAction(actionEvent -> retVal.close());
        BorderPane borderPane = new BorderPane(text);
        BorderPane.setAlignment(okButton, Pos.CENTER);
        borderPane.setBottom(okButton);
        borderPane.setPadding(new Insets(8));
        Scene scene = new Scene(borderPane, 302, 75);
        retVal.setScene(scene);
        return retVal;
    }

    public static Transition getTransitionChooserPopup(ArrayList<Transition> transitions) {

        final Transition[] chosenTransition = new Transition[1];

        Stage popUp = new Stage();
        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.setTitle("Transition Chooser");


        // Label setup
        Label chooseLbl = new Label("Choose transition");
        chooseLbl.setFont(new Font(24));


        // Vbox setup; ListView Setup + Label setup.
        VBox centerDisplay = new VBox(10);

        //// ListView
        ObservableList<Transition> transitionObservableList = FXCollections.observableArrayList(transitions);
        ListView<Transition> transitionListView = new ListView<>(transitionObservableList);
        VBox.setVgrow(transitionListView, Priority.ALWAYS); // Setting the grow property, GREEDY node !

        ////// Setting the action to be taken in the event of clicking on one of the Transitions.

        //// Label
        Label tranLbl = new Label("Transition");
        tranLbl.setFont(new Font(24));
        centerDisplay.getChildren().addAll(transitionListView, tranLbl);

        transitionListView.getSelectionModel()
                .selectedItemProperty()
                .addListener((observableValue, transition, t1) -> {
                    chosenTransition[0] = t1;
                    tranLbl.setText(transitionListView.getSelectionModel().getSelectedItem().toString());
                });

        // Button Bar Setup
        ButtonBar buttonBar = new ButtonBar();
        Button choose = new Button("Choose transition");
        choose.setOnAction(actionEvent -> {
            chosenTransition[0] = transitionListView.getSelectionModel().getSelectedItem();
            popUp.close();
        });
        buttonBar.getButtons().add(choose);
        BorderPane.setMargin(buttonBar, new Insets(4));

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(5));
        borderPane.setTop(chooseLbl);
        borderPane.setCenter(centerDisplay);
        borderPane.setBottom(buttonBar);

        Scene scene = new Scene(borderPane, 600, 400);
        popUp.setScene(scene);

        popUp.showAndWait();

        return chosenTransition[0];
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

    public static Machine getDefaultMachine() {
        Machine debug = new Machine();

        /*
        The state are created, with their uniques names;
         */
        State s1 = State.getState();
        State s2 = State.getState();
        State s3 = State.getState();
        State s4 = State.getState();
        /*
        The transitions are created with their trigger, output, source states and destination states.
         */
        Transition t1 = Transition.getInstance('b', '2', s1, s2);
        Transition tnd2 = Transition.getInstance('b', '8', s1, s4);
        Transition t2 = Transition.getInstance('a', '1', s1, s4);
        Transition t3 = Transition.getInstance('a', '3', s2, s1);
        Transition t4 = Transition.getInstance('b', '2', s2, s3);
        Transition t5 = Transition.getInstance('a', '1', s3, s2);
        Transition t6 = Transition.getInstance('b', '2', s3, s1);
        Transition t7 = Transition.getInstance('b', '1', s4, s1);
        Transition t8 = Transition.getInstance('a', '2', s4, s3);
        Transition tnd9 = Transition.getInstance('a', '2', s4, s2); // Non-determinism test

        debug.setInitialState(s1);

        debug.setInputAlphabet(Set.of('a', 'b'));

        debug.setOutputAlphabet(Set.of('1', '2'));

        debug.setStates(s1, s2, s3, s4);

        debug.setMachineTransitions(t1, tnd2, t2, t3, t4, t5, t6, t7, t8, tnd9);

        return debug;
    }

    public static Machine getAltMachine() {
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
        return simpleMachine;
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
            if (s.stateName().equals(name)) {
                retVal = s;
            }
        }
        if (retVal == null) {
            throw new StateNotFound("State not found: getStateByName: " + name);
        }
        return retVal;
    }

    public static ArrayList<Trace> evalMachine(Machine argMach, ArrayList<Character> input) {
        ArrayList<Trace> allPossibleTraces = new ArrayList<>();
        if (input.isEmpty()) {
            allPossibleTraces.add(argMach.getMachineTrace());
            return allPossibleTraces;
        } else {
            try {
                argMach.setInputSequence(new ArrayList<>(input));
                ArrayList<Transition> transitions = Utilities.getApplicableTransitions(argMach.getCurrentState()
                        , argMach.getNextInputToken());
                for (Transition t : transitions) {
                    Machine clone = argMach.makeCurrentMachineCopy();
                    clone.takeTransition(Utilities.findTransition(t, clone));
                    ArrayList<Trace> trace = evalMachine(clone, argMach.getInputSequence());
                    allPossibleTraces.addAll(trace);
                }
            } catch (Exception e) {
                e.printStackTrace(); // ToDo: Too general, at least specify, even if they have the same catch block.
            }
        }
        return allPossibleTraces;
    }

    public static Transition findTransition(Transition t, Machine mach) throws TransitionNotFound {
        for (Transition transition : mach.getMachineTransitions()) {
            Boolean sameTrigger = transition.transitionTrigger().equals(t.transitionTrigger());
            Boolean sameOutput = transition.transitionOutput().equals(t.transitionOutput());
            Boolean sameSource = transition.sourceState().stateName()
                    .equals(t.sourceState().stateName());
            Boolean sameDest = transition.destinationState().stateName()
                    .equals(t.destinationState().stateName());
            if (sameTrigger && sameOutput && sameSource && sameDest) {
                return transition;
            }
        }
        throw new TransitionNotFound("No equivalent transition found !");
    }

    public static ArrayList<Transition> getApplicableTransitions(State state, Character trigger) throws NoTransitionFound {

        var applicableTransitions = new ArrayList<Transition>(); // Possible transitions from this state-trigger combo
        for (Transition t : state.outGoing()) {
            if (t.sourceState().equals(state) && t.isValid() && t.isTriggeredBy(trigger)) {
                // Check if the transition is valid && is the correct response to this character ...
                // ... if so, add it to the list of possible transitions
                applicableTransitions.add(t);
            }
        }
        if (applicableTransitions.isEmpty()) {
            throw new NoTransitionFound("No transition found from this state. State: "
                    + state + ", trigger: " + trigger + ".");
        } else {
            // At this stage we have the collection of transitions that can be taken from here on out, we return this for now
            return applicableTransitions;
        }
    }

    public static String getInputAsString(Machine argMach) {
        StringBuilder retVal = new StringBuilder(argMach.getInputSequence().size());
        for (Character ch : argMach.getInputSequence()) {
            retVal.append(ch);
        }
        return retVal.toString();
    }

    public static Machine parseMachine(File file) throws IOException {
        Path filePath = file.toPath();
        CharStream fileName = CharStreams.fromPath(filePath);
        automatonLexer automatonLexer = new automatonLexer(fileName);
        CommonTokenStream commonTokenStream = new CommonTokenStream(automatonLexer);
        automatonParser automatonParser = new automatonParser(commonTokenStream);
        ParseTree parseTree = automatonParser.automatonGraph(); // Start this rule!
        ParseTreeWalker parseTreeWalker = new ParseTreeWalker(); // Tree walker!
        AutomatonListener automatonListener = new AutomatonListener(); // Tree listener!
        parseTreeWalker.walk(automatonListener, parseTree); // Walk the parseTree with the listener!

        return automatonListener.getParsedMachine();
    }
}
