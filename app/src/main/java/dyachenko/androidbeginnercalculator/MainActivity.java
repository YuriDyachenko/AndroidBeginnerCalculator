package dyachenko.androidbeginnercalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String KEY_CALCULATOR_DATA = "EXTRA_CALCULATOR_DATA";
    public final static String KEY_THEME = "EXTRA_THEME";
    public final static int APP_DAY_THEME = 0;
    public final static int APP_NIGHT_THEME = 1;
    public final static int DEFAULT_DAY_THEME = 2;
    public final static int DEFAULT_NIGHT_THEME = 3;
    private final static int SETTINGS_REQUEST_CODE = 1;
    private TextView leftOperandTextView;
    private TextView rightOperandTextView;
    private TextView errorTextView;
    private Calculator calculator;
    private int theme = APP_DAY_THEME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            theme = savedInstanceState.getInt(KEY_THEME);
        }
        setAppTheme();

        setContentView(R.layout.activity_main);

        calculator = new Calculator();
        initViews();
        updateViews();
    }

    private void setAppTheme() {
        switch (theme) {
            case APP_NIGHT_THEME:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                setTheme(R.style.Theme_Calculator);
                break;
            case DEFAULT_DAY_THEME:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                setTheme(R.style.Theme_AndroidBeginnerCalculator);
                break;
            case DEFAULT_NIGHT_THEME:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                setTheme(R.style.Theme_AndroidBeginnerCalculator);
                break;
            default:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                setTheme(R.style.Theme_Calculator);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.putExtra(KEY_THEME, theme);
            startActivityForResult(intent, SETTINGS_REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != SETTINGS_REQUEST_CODE) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Bundle extras = data.getExtras();
                theme = extras.getInt(KEY_THEME);
                recreate();
            }
        }
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
        outState.putInt(KEY_THEME, theme);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        calculator.setFromCalculatorData(savedInstanceState.getParcelable(KEY_CALCULATOR_DATA));
        updateViews();
    }
}