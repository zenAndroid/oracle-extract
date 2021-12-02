package app.oracleextractor;

import app.oracleextractor.model.Machine;
import app.oracleextractor.model.State;
import app.oracleextractor.model.Transition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;

public class DebugController implements Initializable {
    @FXML
    TextArea execLogTextArea;
    @FXML
    Text lblInput, lblCurrentState, lblLastOutput, lblOutput;

    @FXML
    WebView webView;

    WebEngine engine;

    @FXML
    Button zoomOutButton, zoomResetButton, zoomInButton, sndInputButton, runMachineButton, stepMachineButton, resetMachineButton;

    @FXML
    TextField sendingInputField;

    @FXML
    ButtonBar buttonBar;

    Machine machineOfInterest, // The 'current' machine.
            cloneOfInterest; // The clone of the main machine that is being debugged.

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeUIElements();
        machineOfInterest = getDefaultMachine();
        cloneOfInterest = machineOfInterest.makeMachineCopy();
        updateMachineView(machineOfInterest);
        // 1 2 2 2 2 1
    }

    public void initializeUIElements() {

        // Initialize zoom button behavior.
        zoomOutButton.setOnAction(actionEvent -> zoomFunctionality(ZOOM.OUT)); // The lambda syntax is quite neat, ngl.
        zoomInButton.setOnAction(actionEvent -> zoomFunctionality(ZOOM.IN));
        zoomResetButton.setOnAction(actionEvent -> zoomFunctionality(ZOOM.RESET));

        // Make sure the buttonBar is disabled at first.

        buttonBar.setDisable(true);

        // Give the 'Send Input' button its action handler

        sndInputButton.setOnAction(actionEvent -> {
            // Set the text as input.
            var input = stringToList(sendingInputField.getText());
            sendingInputField.setText(""); // Empty the input field.
            if (input.size() > 0) {
                machineOfInterest.setInputSequence(input);
                // Enable the button bar
                buttonBar.setDisable(false);
                updateUI();
            }
        });

        stepMachineButton.setOnAction(actionEvent -> {
            machineOfInterest.consumeToken();
            updateUI();
        });

        runMachineButton.setOnAction(actionEvent -> {
            machineOfInterest.consumeEntirely();
            updateUI();
        });

        resetMachineButton.setOnAction(actionEvent -> {
            machineOfInterest = cloneOfInterest.makeMachineCopy();
            updateUI();
        });
    }

    /**
     * Returns an Arraylist of Chars from a string
     *
     * @param argString The argument string
     * @return the array
     */
    public ArrayList<Character> stringToList(String argString) {
        var retVal = new ArrayList<Character>();
        for (Character chara : argString.toCharArray()) {
            retVal.add(chara);
        }
        return retVal;
    }

    /**
     * @return A default machine, hardcoded and used for testing purposes.
     */
    public Machine getDefaultMachine() {

        Machine debug = new Machine();

        // digraph Automaton {
        //     // labelloc = "t"
        //     // label = "Consumed input: baaa\nProduced output: 2212"
        //     INIT [shape=point];
        //     s1 [shape="doublecircle"];
        //     node [shape=circle];
        //     rankdir = LR;
        //     INIT -> s1;
        //     s1 -> s2 [label="b/2", color=red];
        //     s1 -> s4 [label="a/1"];
        //     s2 -> s1 [label="a/2"];
        //     s2 -> s3 [label="b/2"];
        //     s3 -> s2 [label="a/1"];
        //     s3 -> s1 [label="b/2"];
        //     s4 -> s1 [label="b/1"];
        //     s4 -> s3 [label="a/2"];
        // }


        // 1- States.
        // 2- Transitions.
        // 3- Giving states their transitions.
        // 4- Set the initial state.
        // 5- Set the input alphabet (Still sus)
        // 6- Set the output alphabet (still sus)
        // 7- Set the machine states
        // 8- Set the input sequence.
        // 9- CONSUME !!!

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
        Transition t2 = new Transition('a', '1', s1, s4);
        Transition t3 = new Transition('a', '2', s2, s1);
        Transition t4 = new Transition('b', '2', s2, s3);
        Transition t5 = new Transition('a', '1', s3, s2);
        Transition t6 = new Transition('b', '2', s3, s1);
        Transition t7 = new Transition('b', '1', s4, s1);
        Transition t8 = new Transition('a', '2', s4, s3);

        s1.setStateTransitions(t1, t2);
        s2.setStateTransitions(t3, t4);
        s3.setStateTransitions(t5, t6);
        s4.setStateTransitions(t7, t8);

        debug.setInitialState(s1);

        debug.setInputAlphabet(Set.of('a', 'b'));

        debug.setOutputAlphabet(Set.of('1', '2'));

        debug.setStates(s1, s2, s3, s4);

        return debug;
    }

    /**
     * @param argMachine The machine that will be displayed on the webView.
     */
    public void updateMachineView(Machine argMachine) {

        try {
            FileWriter dotfile = new FileWriter("input.dot");
            dotfile.write(argMachine.toDot());
            dotfile.close();
        } catch (IOException e1) {
            // TODO Semble marcher pour l'instant
            e1.printStackTrace();
        }

        try {
            ProcessBuilder pb = new ProcessBuilder("dot.exe", "input.dot", "-Tsvg");
            /*
             * File foo = new File("automaton.svg"); fooPath = Path.of(foo.getPath());
             * pb.redirectOutput(foo);
             */
            BufferedReader reader = new BufferedReader(new InputStreamReader(pb.start().getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            String result = builder.toString();
            engine = webView.getEngine();
            engine.loadContent(result);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void updateUI() {
        lblInput.setText("Input : " + machineOfInterest.getInputAsString());
        lblCurrentState.setText("Current State: " + machineOfInterest.getCurrentState().getName());
        lblLastOutput.setText("Last Output: " + machineOfInterest.getLastOutput());
        lblOutput.setText("Output accumulator: " + machineOfInterest.getProducedOutput());
        updateMachineView(machineOfInterest);
    }

    /**
     * @param zoomDir The direction of the zoom. (In/Out/Reset)
     */
    public void zoomFunctionality(ZOOM zoomDir) {
        switch (zoomDir) {
            case IN -> webView.setZoom(webView.getZoom() + 0.1);
            case OUT -> webView.setZoom(webView.getZoom() - 0.1);
            case RESET -> webView.setZoom(1.0);
        }
    }

    public enum ZOOM {IN, OUT, RESET}
}
