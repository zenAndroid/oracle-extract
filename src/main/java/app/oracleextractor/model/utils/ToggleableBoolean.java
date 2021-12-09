package app.oracleextractor.model.utils;

public class ToggleableBoolean {
    Boolean value;

    public ToggleableBoolean(Boolean value) {
        this.value = value;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(boolean val) {
        value = val;
    }

    public void toggleValue() {
        value = !value;
    }

    public void setTrue() {
        value = true;
    }

    public void setFalse() {
        value = false;
    }

}
