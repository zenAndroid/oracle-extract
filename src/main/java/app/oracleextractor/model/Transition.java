package app.oracleextractor.model;


import app.oracleextractor.model.utils.ToggleableBoolean;

public record Transition(Character transitionTrigger, Character transitionOutput,
                         State sourceState, State destinationState,
                         ToggleableBoolean wasVisited, ToggleableBoolean transitionValid) {

    public static Transition getInstance(Character transitionTrigger, Character transitionOutput,
                                         State sourceState, State destinationState) {
        return new Transition(transitionTrigger, transitionOutput,
                sourceState, destinationState,
                /* wasVisited set to its default initial value: */ new ToggleableBoolean(false),
                /* transitionValid set to its default initial value: */ new ToggleableBoolean(true));
    }

    public Boolean getTransitionValid() {
        return transitionValid.getValue();
    }

    public void setTransitionValid(boolean val) {
        transitionValid.setValue(val);
    }

    public Boolean isValid() {
        return transitionValid.getValue();
    }

    public void setTaken() {
        wasVisited.setTrue();
    }

    public Boolean wasTaken() {
        return wasVisited.getValue();
    }

    public Boolean isTriggeredBy(Character trigger) {
        return transitionTrigger.equals(trigger);
    }

    @Override
    public String toString() {
        return "Transition{" +
                sourceState.stateName() + " -> " + destinationState.stateName() +
                ", trigger: " + transitionTrigger + ", output: " + transitionOutput +
                '}';
    }
}
