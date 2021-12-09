package app.oracleextractor.model.utils;

import app.oracleextractor.model.State;
import app.oracleextractor.model.Transition;

/**
 * I think this class was a mistake actually, will remove later ... TODO: REMOVE THIS INEFFICIENT WASTE OF SPACE
 */
public class StateTransition {

    State beforeState;
    State afterState;
    Transition transition;

    public StateTransition(State beforeState, State afterState, Transition transition) {
        this.beforeState = beforeState;
        this.afterState = afterState;
        this.transition = transition;
    }

    public State getBeforeState() {
        return beforeState;
    }

    public State getAfterState() {
        return afterState;
    }

    public Transition getTransition() {
        return transition;
    }

    @Override
    public String toString() {
        String retVal = "Transition from " + getBeforeState().getName()
                + " to "
                + getAfterState().getName()
                + " reacting to token "
                + getTransition().getTransitionTrigger()
                + ", yielding as output "
                + getTransition().getTransitionOutput();
        return "StateTransition{" +
                retVal +
                '}';
    }
}
