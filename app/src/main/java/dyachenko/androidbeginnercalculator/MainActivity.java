package dyachenko.androidbeginnercalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String KEY_CALCULATOR_DATA = "EXTRA_CALCULATOR_DATA";
    private TextView leftOperandTextView;
    private TextView rightOperandTextView;
    private TextView errorTextView;
    private Calculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculator = new Calculator();
        initViews();
        updateViews();
    }

    private void initViews() {
        leftOperandTextView = findViewById(R.id.left_operand);
        rightOperandTextView = findViewById(R.id.right_operand);
        errorTextView = findViewById(R.id.error);

        ArrayList<Integer> buttonIds = calculator.getAllButtonIds();
        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(this);
        }
    }

    private void updateViews() {
        leftOperandTextView.setText(calculator.getLeftOperand());
        rightOperandTextView.setText(calculator.getRightOperand());
        errorTextView.setText(calculator.getError());
    }

    @Override
    public void onClick(View v) {
        calculator.handleButton(v.getId());
        updateViews();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_CALCULATOR_DATA, calculator.createCalculatorData());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        calculator.setFromCalculatorData(savedInstanceState.getParcelable(KEY_CALCULATOR_DATA));
        updateViews();
    }
}