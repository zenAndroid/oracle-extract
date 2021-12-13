package app.oracleextractor.model.utils;

import app.oracleextractor.model.State;
import app.oracleextractor.reader.automatonBaseListener;
import app.oracleextractor.reader.automatonParser;
import org.antlr.v4.runtime.ParserRuleContext;

public class AutomatonListener extends automatonBaseListener {

    @Override
    public void enterAutomatonGraph(automatonParser.AutomatonGraphContext ctx) {
    }

    @Override
    public void exitAutomatonGraph(automatonParser.AutomatonGraphContext ctx) {
    }

    @Override
    public void enterStatements(automatonParser.StatementsContext ctx) {
    }

    @Override
    public void exitStatements(automatonParser.StatementsContext ctx) {
    }

    @Override
    public void enterTrigger_output(automatonParser.Trigger_outputContext ctx) {
    }

    @Override
    public void exitTrigger_output(automatonParser.Trigger_outputContext ctx) {
    }

    @Override
    public void enterInitArrow(automatonParser.InitArrowContext ctx) {
        String initialStateName = ctx.STATE_NAME().getText();
        State initial = State.getState(initialStateName);
        System.out.println(initial);
    }

    @Override
    public void exitInitArrow(automatonParser.InitArrowContext ctx) {
    }
}
