package app.oracleextractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import app.oracleextractor.model.*;

public class MainController implements Initializable {

    @FXML
    Button loadImpreciseButton, loadTestSuiteButton, startMiningButton, stopMiningButton, clearLogButton, proceedButton;
    @FXML
    Button zoomInImpreciseButton, zoomOutImpreciseButton, zoomResetImpreciseButton;
    @FXML
    Button zoomInPartialButton, zoomOutPartialButton, zoomResetPartialButton;
    @FXML
    WebView impreciseOracleView, partialOracleView;

    @FXML
    ListView<String> outputSeqList;

    @FXML
    Label inSeq;

    @FXML
    TextArea executionLogTextArea;

    WebEngine engine;

    // Temp string: amine remove this after you test it, this is only for
    // confirmation so NEED TO REMOVE:
    Path fooPath;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        System.out.println("Controller.initialize()");
        Machine MM = new Machine(); // Think i'm gonna model the machine in the article.

        // 1- States.
        // 2- Transitions.
        // 3- Giving states their transitions.
        // 4- Set the initial state.
        // 5- Set the input alphabet (Still sus)
        // 6- Set the output alphabet (still sus)
        // 7- Set the machine states
        // 8- Set the input sequence.
        // 9- CONSUME !!!

        // 1- States.
        State q1 = new State(), q2 = new State(), q3 = new State(), q4 = new State();
        // 2- Transitions
        Transition tr1 = new Transition('b', '0', q1); // q1
        Transition tr2 = new Transition('a', '0', q2); // q1
        Transition tr3 = new Transition('a', '1', q2); // q2
        Transition tr4 = new Transition('b', '0', q3); // q2
        Transition tr5 = new Transition('a', '1', q4); // q3
        Transition tr6 = new Transition('b', '0', q3); // q3
        Transition tr7 = new Transition('a', '1', q4); // q4
        Transition tr8 = new Transition('b', '1', q4); // q4
        // 3- Giving states their transitions.
        q1.setStateTransitions(tr1, tr2);
        q2.setStateTransitions(tr3, tr4);
        q3.setStateTransitions(tr5, tr6);
        q4.setStateTransitions(tr7, tr8);
        // 4- Set the initial State
        MM.setInitialState(q1);
        // 5- Set the input alphabet (Still sus)
        MM.setInputAlphabet(Set.of('a', 'b'));
        // 6- Set the output alphabet (still sus)
        MM.setOutputAlphabet(Set.of('0', '1'));
        // 7- Set the machine states
        MM.setStates(q1, q2, q3, q4);
        // 8- Set the input sequence. and consume
        // ----------------------------- MM.consume(new
        // ArrayList<>(List.of('a','b','b','a','b','b','b','a')));

        // This should give
        // 0 0 0 1 1 1 1 1

        System.out.println("Actually works, i cant believe it");

        // System.out.println(MM.toDot());

        try {
            FileWriter dotfile = new FileWriter("input.dot");
            dotfile.write(MM.toDot());
            dotfile.close();
            System.out.println("Aut.main()");
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
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            String result = builder.toString();
            engine = impreciseOracleView.getEngine();
            engine.loadContent(result);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    public static String fileReaderHelper(String fileName) {
        StringBuilder b = new StringBuilder();
        try {
            File myObj = new File(fileName);
            Scanner fileReader = new Scanner(myObj);
            while (fileReader.hasNextLine()) {
                b.append(fileReader.nextLine());
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return b.toString();
    }

    public void startMining() {
        // TODO Auto-generated method stub
        System.out.println("Controller.startMining()");
        executionLogTextArea.appendText("Started Mining\n");
        String contentOfFile = null;
        try {
            contentOfFile = Files.readString(fooPath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("COntents of the file: " + fileReaderHelper("automaton.svg"));
        engine = impreciseOracleView.getEngine();
        engine.loadContent(contentOfFile);
    }

    public void stopMining() {
        // TODO Auto-generated method stub
        System.out.println("Controller.stopMining()");
        executionLogTextArea.appendText("Stopped Mining\n");
    }

    public void loadImpreciseOracle() {
        // TODO Auto-generated method stub
        System.out.println("Controller.loadImpreciseOracle()");
        executionLogTextArea.appendText("Loaded Imprecise Oracle\n");
    }

    public void loadTestSuite() {
        // TODO Auto-generated method stub
        System.out.println("Controller.loadTestSuite()");
        executionLogTextArea.appendText("Loaded Test Suite\n");
    }

    public void zoomInOnImprecise() {
        // TODO Auto-generated method stub
        System.out.println("Controller.zoomInOnImprecise()");

    }

    public void zoomOutOnImprecise() {
        // TODO Auto-generated method stub
        System.out.println("Controller.zoomOutOnImprecise()");
    }

    public void zoomResetOnImprecise() {
        // TODO Auto-generated method stub
        System.out.println("Controller.zoomResetOnImprecise()");
    }

    public void zoomInOnPartial() {
        // TODO Auto-generated method stub
        System.out.println("Controller.zoomInOnPartial()");

    }

    public void zoomOutOnPartial() {
        // TODO Auto-generated method stub
        System.out.println("Controller.zoomOutOnPartial()");

    }

    public void zoomResetOnPartial() {
        // TODO Auto-generated method stub
        System.out.println("Controller.zoomResetOnPartial()");
    }

    public void proceedWithOutputSeq() {
        // TODO Auto-generated method stub
        System.out.println("Controller.proceedWithOutputSeq()");
        executionLogTextArea.appendText("Proceeding with selected output sequence\n");

    }

    public void clearLog() {
        // TODO Auto-generated method stub
        System.out.println("Controller.clearLog()");
        executionLogTextArea.setText("Cleared execution log...\n");

    }
}