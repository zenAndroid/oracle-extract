package app.oracleextractor.model;

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
