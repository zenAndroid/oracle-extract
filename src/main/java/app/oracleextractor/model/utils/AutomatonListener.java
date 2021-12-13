package app.oracleextractor.model.utils;

import app.oracleextractor.model.State;
import app.oracleextractor.model.Transition;
import app.oracleextractor.model.exceptions.StateNotFound;
import app.oracleextractor.reader.automatonBaseListener;
import app.oracleextractor.reader.automatonParser;

import java.util.ArrayList;
import java.util.Set;

public class AutomatonListener extends automatonBaseListener {

    /*

    Sample file of an automaton:

    digraph Automaton {
        node [shape=point] INIT;
        q1 [shape="doublecircle"];
        node [shape=circle];
        rankdir = LR;
        INIT -> q1;
        q1 -> q2 [label="b/2"];
        q1 -> q4 [label="b/8"];
        q1 -> q4 [label="a/1"];
        q2 -> q1 [label="a/3"];
        q2 -> q3 [label="b/2"];
        q3 -> q2 [label="a/1"];
        q3 -> q1 [label="b/2"];
        q4 -> q1 [label="b/1"];
        q4 -> q3 [label="a/2"];
        q4 -> q2 [label="a/2"];
    }
    */
    ArrayList<State> listenerStates = new ArrayList<>();
    ArrayList<Transition> listenerTransitions= new ArrayList<>();


    @Override
    public void enterStatement(automatonParser.StatementContext ctx) {
        // q2 -> q3 [label="b/2"];
        State source, dest; // The two states.
        String sourceName = ctx.STATE_NAME(0).getText();
        String destName = ctx.STATE_NAME(1).getText();
        char trigger = ctx.trigger_output().TRIGGER().getText().charAt(0);
        char output = ctx.trigger_output().OUTPUT().getText().charAt(0);
        try {
            source = Utilities.getStateByName(listenerStates, sourceName);
        } catch (StateNotFound e) {
            source = State.getState(sourceName);
            listenerStates.add(source);
        }
        try {
            dest = Utilities.getStateByName(listenerStates, destName);
        } catch (StateNotFound e) {
            dest = State.getState(destName);
            listenerStates.add(dest);
        }
        // At this point, we've got both states, and so now we can add the transition to the transition array.
        Transition thisTransition = Transition.getInstance(trigger, output, source, dest);
        listenerTransitions.add(thisTransition);
        // Need to test
        System.out.println(thisTransition);
    }

    @Override
    public void exitStatement(automatonParser.StatementContext ctx) {
    }

    @Override
    public void enterInitArrow(automatonParser.InitArrowContext ctx) {
        String initialStateName = ctx.STATE_NAME().getText();
        State initial = State.getState(initialStateName);
        System.out.println(initial);
    }

    @Override public void exitAutomatonGraph(automatonParser.AutomatonGraphContext ctx) {
        System.out.println(listenerStates.size());
        System.out.println(listenerTransitions.size());
    }
}
