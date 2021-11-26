package app.oracleextractor;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class DebugController implements Initializable {
    @FXML
    Text lblInput, lblCurrentState, lblLastOutput, lblOutput;

    @FXML
    WebView webView;

    @FXML
    Button zoomOutButton, zoomResetButton, zoomInButton, sndInputButton, runMachineButton, stepMachineButton, resetMachineButton;

    @FXML
    TextField sendingInputField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
