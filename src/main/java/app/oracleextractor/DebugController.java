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
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class DebugController implements Initializable {
    @FXML
    TextArea execLogTextArea;
    @FXML
    Text lblInput, lblCurrentState, lblLastOutput, lblOutput;

    @FXML
    WebView webView;

    @FXML
    Button zoomOutButton, zoomResetButton, zoomInButton, sndInputButton, runMachineButton, stepMachineButton, resetMachineButton;

    @FXML
    TextField sendingInputField;

    @FXML
    ButtonBar buttonBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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

        s1.setStateTransitions(t1,t2);
        s2.setStateTransitions(t3,t4);
        s3.setStateTransitions(t5,t6);
        s4.setStateTransitions(t7,t8);

        debug.setInitialState(s1);

        debug.setInputAlphabet(Set.of('a','b'));

        debug.setOutputAlphabet(Set.of('1','2'));

        debug.setStates(s1,s2,s3,s4);

        debug.consume(new ArrayList<>(List.of('a','a','b','b','a','a')));

        // 1 2 2 2 2 1


    }
}
