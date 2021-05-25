package dyachenko.androidbeginnercalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static dyachenko.androidbeginnercalculator.Operation.CLEAR;
import static dyachenko.androidbeginnercalculator.Operation.CLEAR_ALL;
import static dyachenko.androidbeginnercalculator.Operation.DELETE;
import static dyachenko.androidbeginnercalculator.Operation.DIVIDE;
import static dyachenko.androidbeginnercalculator.Operation.EQUAL;
import static dyachenko.androidbeginnercalculator.Operation.MINUS;
import static dyachenko.androidbeginnercalculator.Operation.MULTIPLY;
import static dyachenko.androidbeginnercalculator.Operation.PLUS;
import static dyachenko.androidbeginnercalculator.Operation.REST;
import static dyachenko.androidbeginnercalculator.Operation.XOR;

public class Calculator {
    private final static int MAX_OPERAND_LENGTH = 15;
    private final StringBuilder leftOperand = new StringBuilder();
    private final StringBuilder rightOperand = new StringBuilder();
    private final StringBuilder error = new StringBuilder();
    private Operation operation = null;
    private final Map<Integer, String> digitButtons = new HashMap<>();
    private final Map<Integer, Operation> operationButtons = new HashMap<>();

    public Calculator() {
        fillDigits();
        fillOperations();
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

    public ArrayList<Integer> getAllButtonIds() {
        ArrayList<Integer> ids = new ArrayList<>();
        ids.addAll(digitButtons.keySet());
        ids.addAll(operationButtons.keySet());
        return ids;
    }

    public String getRightOperand() {
        return rightOperand.toString();
    }

    public String getLeftOperand() {
        return leftOperand.toString() + getOperationString();
    }

    public String getError() {
        return error.toString();
    }

    public void handleButton(int id) {
        clearBuilder(error);

        if (digitButtons.containsKey(id)) {
            handleDigit(digitButtons.get(id));
        } else if (operationButtons.containsKey(id)) {
            handleOther(operationButtons.get(id));
        }
    }

    public void handleDigit(String digit) {
        if (rightOperand.length() == MAX_OPERAND_LENGTH) {
            return;
        }
        rightOperand.append(digit);
    }

    public void handleOther(Operation operation) {
        if (operation == null) {
            return;
        }
        if (operation.isArithmetic()) {
            calc();
            if (operation != EQUAL) {
                if (!rightIsEmpty()) {
                    rightToLeft();
                    clearRight();
                }
                if (!leftIsEmpty()) {
                    this.operation = operation;
                }
            }
        } else {
            switch (operation) {
                case DELETE:
                    delete();
                    break;
                case CLEAR:
                    clear();
                    break;
                case CLEAR_ALL:
                    clearAll();
                    break;
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
                try {
                    return a / b;
                } catch (ArithmeticException exception) {
                    error.append("Division by zero!");
                    return 0;
                }
            case REST:
                return a % b;
            case XOR:
                return a ^ b;
        }
        error.append("Wrong operation!");
        return 0;
    }

    private void delete() {
        int length = rightOperand.length();
        if (length > 0) {
            rightOperand.deleteCharAt(length - 1);
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
        return operation == null ? "" : " " + operation.getValue();
    }

    private void fillOperations() {
        operationButtons.put(R.id.button_c, CLEAR_ALL);
        operationButtons.put(R.id.button_ce, CLEAR);
        operationButtons.put(R.id.button_delete, DELETE);
        operationButtons.put(R.id.button_divide, DIVIDE);
        operationButtons.put(R.id.button_equal, EQUAL);
        operationButtons.put(R.id.button_minus, MINUS);
        operationButtons.put(R.id.button_multiply, MULTIPLY);
        operationButtons.put(R.id.button_plus, PLUS);
        operationButtons.put(R.id.button_rest, REST);
        operationButtons.put(R.id.button_xor, XOR);
    }

    private void fillDigits() {
        digitButtons.put(R.id.button_0, "0");
        digitButtons.put(R.id.button_1, "1");
        digitButtons.put(R.id.button_2, "2");
        digitButtons.put(R.id.button_3, "3");
        digitButtons.put(R.id.button_4, "4");
        digitButtons.put(R.id.button_5, "5");
        digitButtons.put(R.id.button_6, "6");
        digitButtons.put(R.id.button_7, "7");
        digitButtons.put(R.id.button_8, "8");
        digitButtons.put(R.id.button_9, "9");
    }

}
