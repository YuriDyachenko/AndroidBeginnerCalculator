package dyachenko.androidbeginnercalculator;

import java.util.HashMap;
import java.util.Map;

import static dyachenko.androidbeginnercalculator.Operation.EQUAL;

public class Calculator {
    public final String TAG_DIGIT_BUTTON;
    public final String TAG_OTHER_BUTTON;
    private final String DIGIT_0;
    private final static int MAX_OPERAND_LENGTH = 15;
    private final StringBuilder leftOperand = new StringBuilder();
    private final StringBuilder rightOperand = new StringBuilder();
    private final Map<String, Operation> mapByStringOperations = new HashMap<>();
    private final Map<Operation, String> mapByOperations = new HashMap<>();
    private Operation operation;

    public Calculator(String tag_digit_button, String tag_other_button, String digit_0) {
        TAG_DIGIT_BUTTON = tag_digit_button;
        TAG_OTHER_BUTTON = tag_other_button;
        DIGIT_0 = digit_0;
        operation = null;
    }

    public CalculatorData createCalculatorData() {
        return new CalculatorData(leftOperand.toString(), rightOperand.toString(), operation);
    }

    public void setFromCalculatorData(CalculatorData data) {
        clearAll();
        leftOperand.append(data.getLeftOperand());
        rightOperand.append(data.getRightOperand());
        operation = data.getOperation();
    }

    public void registerOperation(Operation op, String value) {
        mapByStringOperations.put(value, op);
        mapByOperations.put(op, value);
    }

    public String getRightOperand() {
        return rightOperand.toString();
    }

    public String getLeftOperand() {
        return leftOperand.toString() + getOperationString();
    }

    public void handle(String tag, String text) {
        if (tag.equals(TAG_DIGIT_BUTTON)) {
            handleDigit(text);
        } else {
            handleOther(text);
        }
    }

    public void handleDigit(String digit) {
        if (rightOperand.length() == MAX_OPERAND_LENGTH) {
            return;
        }
        if (digit.equals(DIGIT_0) && rightOperand.length() == 0) {
            return;
        }
        rightOperand.append(digit);
    }

    public void handleOther(String value) {
        Operation op = mapByStringOperations.get(value);
        if (op != null) {
            switch (op) {
                case DELETE:
                    delete();
                    break;
                case CLEAR:
                    clear();
                    break;
                case CLEARALL:
                    clearAll();
                    break;
                default:
                    handleArithmeticOperation(op);
            }
        }
    }

    public void handleArithmeticOperation(Operation op) {
        calc();
        if (op != EQUAL) {
            if (!rightIsEmpty()) {
                rightToLeft();
                clearRight();
            }
            if (!leftIsEmpty()) {
                this.operation = op;
            }
        }
    }

    private void calc() {
        if (leftOperand.length() == 0 || rightOperand.length() == 0 || operation == null) {
            return;
        }
        int res = doOperation(Integer.parseInt(leftOperand.toString()),
                Integer.parseInt(rightOperand.toString()));
        clearAll();
        leftOperand.append(res);
        operation = null;
    }

    private int doOperation(int a, int b) {
        switch (operation) {
            case PLUS:
                return a + b;
            case MINUS:
                return a - b;
            case MULTIPLY:
                return a * b;
            case DIVIDE:
                return a / b;
            case REST:
                return a % b;
            case XOR:
                return a ^ b;
        }
        return 0;
    }

    private void delete() {
        int length = rightOperand.length();
        if (length > 0) {
            rightOperand.deleteCharAt(--length);
        }
    }

    private void clear() {
        clearBuilder(rightOperand);
    }

    private void clearAll() {
        clearBuilder(leftOperand);
        clearBuilder(rightOperand);
        operation = null;
    }

    private void clearBuilder(StringBuilder builder) {
        int length = builder.length();
        if (length > 0) {
            builder.delete(0, length);
        }
    }

    private void clearLeft() {
        clearBuilder(leftOperand);
    }

    private void clearRight() {
        clearBuilder(rightOperand);
    }

    private void rightToLeft() {
        clearLeft();
        leftOperand.append(rightOperand);
    }

    private boolean rightIsEmpty() {
        return rightOperand.length() == 0;
    }

    private boolean leftIsEmpty() {
        return leftOperand.length() == 0;
    }

    private String getOperationString() {
        String op = mapByOperations.get(operation);
        return op == null ? "" : " " + op;
    }
}
