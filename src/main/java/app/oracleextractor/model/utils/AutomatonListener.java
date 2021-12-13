package app.oracleextractor.model.utils;

import app.oracleextractor.model.Machine;
import app.oracleextractor.model.State;
import app.oracleextractor.reader.*;

import java.util.ArrayList;

public class AutomatonListener extends automatonBaseListener{

    @Override public void enterInitArrow(automatonParser.InitArrowContext ctx) {
        String initialStateName = ctx.STATE_NAME().getText();
        State initial = State.getState(initialStateName);
        System.out.println(initial);
    }

    @Override public void exitInitArrow(automatonParser.InitArrowContext ctx) { }
}
