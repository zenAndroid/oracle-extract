package app.oracleextractor;

import app.oracleextractor.model.Machine;
import app.oracleextractor.model.exceptions.BadInputException;
import app.oracleextractor.model.exceptions.NoLastChange;
import app.oracleextractor.model.exceptions.NoPendingInput;
import app.oracleextractor.model.utils.Reader;
import app.oracleextractor.model.utils.Utilities;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class DebugController implements Initializable {
    @FXML
    MenuItem loadMachMenuItem;
    @FXML
    TextArea execLogTextArea;

    @FXML
    Text lblInput, lblCurrentState, lblLastOutput, lblOutput;

    @FXML
    WebView webView;

    @FXML
    Button zoomOutButton, zoomResetButton, zoomInButton, sndInputButton,
            runMachineButton, stepMachineButton, resetMachineButton, saveLogButton;

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
        machineOfInterest = Utilities.getAltMachine();
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
                try {
                    machineOfInterest.setInputSequence(input);
                    // Enable the button bar
                    buttonBar.setDisable(false);
                    log(LOGTYPE.NEW_INPUT_ENTRY); // Log the new input
                    updateUI();
                } catch (BadInputException e) {
                    e.printStackTrace();
                    Utilities.getPopup("Bad input",
                            "THe input does not conform to the machine's input alphabet").showAndWait();
                }

            }
        });

        stepMachineButton.setOnAction(actionEvent -> {
            try {
                machineOfInterest.nonDeterministicallyConsumeToken(true);
                log(LOGTYPE.STEP_ENTRY);
                updateUI();
            } catch (NoPendingInput e) {
                Utilities.getPopup("No tokens left", "There are no tokens left to consume").showAndWait();
            }

        });

        runMachineButton.setOnAction(actionEvent -> {
            machineOfInterest.nonDeterministicConsume(true);
            log(LOGTYPE.RUN_ENTRY);
            updateUI();
        });

        resetMachineButton.setOnAction(actionEvent -> {
            machineOfInterest = cloneOfInterest.makeMachineCopy();
            log(LOGTYPE.RESET_MACHINE_ENTRY);
            buttonBar.setDisable(true); // disable the button bar after resetting the machine
            updateUI();
        });

        saveLogButton.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose logfile save location");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt"));
            Node sauce = (Node) actionEvent.getSource();
            Window theStage = sauce.getScene().getWindow();
            File out = fileChooser.showSaveDialog(theStage);
            try {
                FileWriter outWriter = new FileWriter(out);
                outWriter.write(execLogTextArea.getText());
                outWriter.close();
            } catch (IOException e) {
                Utilities.getPopup("File exception", "IOException: A problem occurred while saving the file").showAndWait();
                e.printStackTrace();
            }
        });

        loadMachMenuItem.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose machine file location");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt"));
            Node sauce = loadMachMenuItem.getParentPopup().getOwnerNode();
            Window theStage = sauce.getScene().getWindow();
            File out = fileChooser.showOpenDialog(theStage);
            if (out == null) {
                System.err.println("File issue: out is null so there was an I/O problem.");
            } else {
                try {
                    machineOfInterest = Reader.parseMachine(out);
                    cloneOfInterest = machineOfInterest.makeMachineCopy();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                log(LOGTYPE.NEW_MACHINE_LOADED);
                updateUI();
            }
        });
    }

    public void log(LOGTYPE type) {
        String existingContent = execLogTextArea.getText();
        String newContent = "";
        switch (type) {
            case NEW_INPUT_ENTRY -> newContent = "New input to the machine: " + Utilities.getInputAsString(machineOfInterest) + '\n';
            case STEP_ENTRY -> {
                try {
                    newContent = machineOfInterest.getMachineTrace().getLastChange().toString() + '\n';
                } catch (NoLastChange e) {
                    newContent = "";
                }
            }
            case RUN_ENTRY -> newContent = "Complete machine execution.\n" + machineOfInterest.getMachineTrace().toString() + '\n';
            case RESET_MACHINE_ENTRY -> newContent = "Machine reset to initial state.\n";
            case NEW_MACHINE_LOADED -> newContent = "New machine loaded.\n";
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
        lblInput.setText("Input : " + Utilities.getInputAsString(machineOfInterest));
        lblCurrentState.setText("Current State: " + machineOfInterest.getCurrentState().stateName());
        try {
            lblLastOutput.setText("Last Output: " + machineOfInterest.getLastOutput());
        } catch (NoLastChange e) {
            lblLastOutput.setText("Last Output: NONE");
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
        NEW_MACHINE_LOADED

    }
}
