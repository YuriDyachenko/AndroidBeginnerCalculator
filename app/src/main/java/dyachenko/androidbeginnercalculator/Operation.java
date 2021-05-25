package dyachenko.androidbeginnercalculator;

public enum Operation {
    PLUS("+", true),
    MINUS("-", true),
    MULTIPLY("*", true),
    DIVIDE("/", true),
    REST("%", true),
    XOR("^", true),
    EQUAL("=", true),
    DELETE("←", false),
    CLEAR("CE", false),
    CLEAR_ALL("C", false);

    private final String value;
    private final boolean arithmetic;

    Operation(String value, boolean arithmetic) {
        this.value = value;
        this.arithmetic = arithmetic;
    }

    public String getValue() {
        return value;
    }

    public boolean isArithmetic() {
        return arithmetic;
    }
}
