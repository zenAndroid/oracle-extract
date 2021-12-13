package app.oracleextractor;

import app.oracleextractor.model.Machine;
import app.oracleextractor.model.utils.Reader;
import app.oracleextractor.model.utils.Utilities;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

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

    Machine machineOfInterest, // The 'current' machine.
            cloneOfInterest; // The clone of the main machine that is being debugged.


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        initializeUIElements();
        machineOfInterest = Utilities.getAltMachine();
        cloneOfInterest = machineOfInterest.makeMachineCopy();
        updateMachineView(machineOfInterest);

    }


    public void initializeUIElements() {
        /*
        @FXML
        Button loadImpreciseButton, loadTestSuiteButton, startMiningButton, stopMiningButton, clearLogButton, proceedButton;
        @FXML
        Button zoomInImpreciseButton, zoomOutImpreciseButton, zoomResetImpreciseButton;
        @FXML
        Button zoomInPartialButton, zoomOutPartialButton, zoomResetPartialButton;
        */
        zoomInImpreciseButton.setOnAction(actionEvent -> zoomFunctionality(ZOOM.IN, impreciseOracleView));
        zoomOutImpreciseButton.setOnAction(actionEvent -> zoomFunctionality(ZOOM.OUT, impreciseOracleView));
        zoomResetImpreciseButton.setOnAction(actionEvent -> zoomFunctionality(ZOOM.RESET, impreciseOracleView));
        zoomInPartialButton.setOnAction(actionEvent -> zoomFunctionality(ZOOM.IN, partialOracleView));
        zoomOutPartialButton.setOnAction(actionEvent -> zoomFunctionality(ZOOM.OUT, partialOracleView));
        zoomResetPartialButton.setOnAction(actionEvent -> zoomFunctionality(ZOOM.RESET, partialOracleView));
        clearLogButton.setOnAction(actionEvent -> clearLog());
        loadImpreciseButton.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose imprecise oracle location");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt"));
            Node sauce = (Node) actionEvent.getSource();
            Window theStage = sauce.getScene().getWindow();
            File out = fileChooser.showOpenDialog(theStage);
            try {
                machineOfInterest = Reader.parseMachine(out);
                cloneOfInterest = machineOfInterest.makeMachineCopy();
                log("New oracle loaded.");
                updateUI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    public void log(String content) {
        executionLogTextArea.appendText(content + "\n");
    }

    public void updateUI() {

        updateMachineView(machineOfInterest);

    }

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
            engine = partialOracleView.getEngine();
            engine.loadContent(result);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void zoomFunctionality(ZOOM zoomDir, WebView targetView) {
        switch (zoomDir) {
            case IN -> targetView.setZoom(targetView.getZoom() + 0.1);
            case OUT -> targetView.setZoom(targetView.getZoom() - 0.1);
            case RESET -> targetView.setZoom(1.0);
        }
    }

    public void startMining() {
        // TODO Auto-generated method stub
        System.out.println("Controller.startMining()");
        executionLogTextArea.appendText("Started Mining\n");
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

    public void clearLog() {
        executionLogTextArea.setText("Cleared execution log...\n");
    }

    public enum ZOOM {IN, OUT, RESET}
}