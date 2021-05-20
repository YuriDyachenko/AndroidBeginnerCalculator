package dyachenko.androidbeginnercalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings");

        initViews();
    }

    private void initViews() {
        radioGroup = (RadioGroup) findViewById(R.id.themes_group);

        findViewById(R.id.apply).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(MainActivity.KEY_THEME, themeById(radioGroup.getCheckedRadioButtonId()));
            setResult(Activity.RESULT_OK, intent);
            finish();
        });

        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            int theme = extras.getInt(MainActivity.KEY_THEME, MainActivity.APP_DAY_THEME);
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(theme);
            radioButton.setChecked(true);
        }
    }

    private int themeById(int themeId) {
        switch (themeId) {
            case (R.id.app_night_theme):
                return MainActivity.APP_NIGHT_THEME;
            case (R.id.default_day_theme):
                return MainActivity.DEFAULT_DAY_THEME;
            case (R.id.default_night_theme):
                return MainActivity.DEFAULT_NIGHT_THEME;
            default:
                return MainActivity.APP_DAY_THEME;
        }
    }
}