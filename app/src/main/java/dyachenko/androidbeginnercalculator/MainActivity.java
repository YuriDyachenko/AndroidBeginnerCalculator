package dyachenko.androidbeginnercalculator;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static dyachenko.androidbeginnercalculator.Operation.CLEAR;
import static dyachenko.androidbeginnercalculator.Operation.CLEARALL;
import static dyachenko.androidbeginnercalculator.Operation.DELETE;
import static dyachenko.androidbeginnercalculator.Operation.DIVIDE;
import static dyachenko.androidbeginnercalculator.Operation.EQUAL;
import static dyachenko.androidbeginnercalculator.Operation.MINUS;
import static dyachenko.androidbeginnercalculator.Operation.MULTIPLY;
import static dyachenko.androidbeginnercalculator.Operation.PLUS;
import static dyachenko.androidbeginnercalculator.Operation.REST;
import static dyachenko.androidbeginnercalculator.Operation.XOR;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvLeftOperand;
    private TextView tvRightOperand;
    private Calculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        createCalculator();
        setOnClickListenerForAllButtons();

        updateViews();
    }

    private void updateViews() {
        tvLeftOperand.setText(calculator.getLeftOperand());
        tvRightOperand.setText(calculator.getRightOperand());
    }

    private void findViews() {
        tvLeftOperand = findViewById(R.id.left_operand);
        tvRightOperand = findViewById(R.id.right_operand);
    }

    private void createCalculator() {
        Resources r = getResources();
        calculator = new Calculator(r.getString(R.string.tag_digit_button),
                r.getString(R.string.tag_other_button),
                r.getString(R.string.button_digit_0));
        calculator.registerOperation(PLUS, r.getString(R.string.button_plus));
        calculator.registerOperation(MINUS, r.getString(R.string.button_minus));
        calculator.registerOperation(MULTIPLY, r.getString(R.string.button_multiply));
        calculator.registerOperation(DIVIDE, r.getString(R.string.button_divide));
        calculator.registerOperation(REST, r.getString(R.string.button_rest));
        calculator.registerOperation(DELETE, r.getString(R.string.button_delete));
        calculator.registerOperation(EQUAL, r.getString(R.string.button_equal));
        calculator.registerOperation(CLEAR, r.getString(R.string.button_clear));
        calculator.registerOperation(CLEARALL, r.getString(R.string.button_clear_all));
        calculator.registerOperation(XOR, r.getString(R.string.button_xor));
    }

    @Override
    public void onClick(View v) {
        calculator.handle(String.valueOf(v.getTag()), String.valueOf(((Button) v).getText()));
        updateViews();
    }

    private void setOnClickListenerForAllButtons() {
        ArrayList<View> touchables = findViewById(R.id.buttons_container).getTouchables();
        for (View v : touchables) {
            Object tag = v.getTag();
            if (tag.equals(calculator.TAG_DIGIT_BUTTON) || tag.equals(calculator.TAG_OTHER_BUTTON)) {
                v.setOnClickListener(this);
            }
        }
    }
}