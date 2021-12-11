package app.oracleextractor.model.exceptions;

import java.util.ArrayList;

public class StuckMachineException extends Exception {

    ArrayList<Character> producedOutput; // The output produced by the machine before it got stuck.
    ArrayList<Character> remainingInput; // The input that remains unprocessed by the machine.

    public StuckMachineException() {
    }

    public StuckMachineException(String message, ArrayList<Character> producedOutput, ArrayList<Character> remainingInput) {
        super(message);
        this.producedOutput = producedOutput;
        this.remainingInput = remainingInput;
    }

    public StuckMachineException(String msg) {
        super(msg);
    }
}
