package app.oracleextractor;

import app.oracleextractor.model.Machine;
import app.oracleextractor.model.exceptions.NoPendingInput;
import app.oracleextractor.model.utils.Utilities;
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
import java.util.ResourceBundle;

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

    Machine machineOfInterest, // The 'current' machine.
            cloneOfInterest; // The clone of the main machine that is being debugged.

    WebEngine engine;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeUIElements();
        machineOfInterest = Utilities.getDefaultMachine();
        cloneOfInterest = machineOfInterest.makeMachineCopy();
        updateMachineView(machineOfInterest);
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
            var input = Utilities.stringToList(sendingInputField.getText());
            sendingInputField.setText(""); // Empty the input field.
            if (input.size() > 0) {
                machineOfInterest.setInputSequence(input);
                // Enable the button bar
                buttonBar.setDisable(false);
                log(LOGTYPE.NEW_INPUT_ENTRY); // Log the new input
                updateUI();
            }
        });

        stepMachineButton.setOnAction(actionEvent -> {
            try {
                machineOfInterest.nonDeterministicallyConsumeToken();
                log(LOGTYPE.STEP_ENTRY);
                updateUI();
            } catch (NoPendingInput e) {
                e.printStackTrace();
                Utilities.getPopup("No tokens left","There are no tokens left to consume").showAndWait();
            }

        });

        runMachineButton.setOnAction(actionEvent -> {
            machineOfInterest.nonDeterministicConsume();
            log(LOGTYPE.RUN_ENTRY);
            updateUI();
        });

        resetMachineButton.setOnAction(actionEvent -> {
            machineOfInterest = cloneOfInterest.makeMachineCopy();
            log(LOGTYPE.RESET_MACHINE_ENTRY);
            updateUI();
        });
    }

    public void log(LOGTYPE type) {
        String existingContent = execLogTextArea.getText();
        String newContent = "";
        switch (type) {
            case NEW_INPUT_ENTRY -> newContent = "New input to the machine: " + machineOfInterest.getInputAsString() + '\n';
            case STEP_ENTRY -> newContent = machineOfInterest.getMachineTrace().getLastChange().toString() + '\n';
            case RUN_ENTRY -> newContent = "Complete machine execution.\n" + machineOfInterest.getMachineTrace().toString() + '\n';
            case RESET_MACHINE_ENTRY -> newContent = "Machine reset to initial state.\n";
        }
        execLogTextArea.setText(existingContent + newContent);
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
     * Updates the labels above the <code>Machine</code>'s view, as well as the <code>Machine</code> view itself.
     */
    public void updateUI() {
        lblInput.setText("Input : " + machineOfInterest.getInputAsString());
        lblCurrentState.setText("Current State: " + machineOfInterest.getCurrentState().getName());
        if (machineOfInterest.getLastOutput() != null) {
            lblLastOutput.setText("Last Output: " + machineOfInterest.getLastOutput());
        }
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

    public enum LOGTYPE {
        NEW_INPUT_ENTRY,
        STEP_ENTRY,
        RUN_ENTRY,
        RESET_MACHINE_ENTRY,

    }
}
