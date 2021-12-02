package app.oracleextractor;

import app.oracleextractor.model.Machine;
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
        machineOfInterest = Machine.getDefaultMachine();
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
            machineOfInterest.nonDeterministicallyConsumeToken();
            updateUI();
        });

        runMachineButton.setOnAction(actionEvent -> {
            machineOfInterest.nonDeterministicConsume();
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
