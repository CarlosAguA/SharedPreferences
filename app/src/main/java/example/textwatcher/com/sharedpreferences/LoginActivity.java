package example.textwatcher.com.sharedpreferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    /*A*/
    SharedPreferences Savesettings ;

    EditText user, password , etDis;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //Part A1. Understanding sharedPreferences
        etDis = (EditText)findViewById(R.id.etDis);
        Savesettings = getSharedPreferences("settingFile", MODE_PRIVATE);
        // If value for key not exist then return second param value -
        // ("isTv2Disabled",false) - in this case false
        if (Savesettings.contains("isTv2Disabled") || Savesettings.getBoolean("isTv2Disabled",false)) {
            etDis.setEnabled( false );
            etDis.setVisibility(View.GONE);
        }

        /* B1. Casting of the widgets*/
        user = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.button_login);

        /* B3. Set Text Watcher listener for the 2 editTexts*/
        user.addTextChangedListener(userAndPasswordFilledInWatcher);
        password.addTextChangedListener(userAndPasswordFilledInWatcher);

        //(getContext()
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        user.setText(preferences.getString(MainApplication.PREFERENCE_USER, null));

        /*B4. Implements the listener for the button*/
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                preferences
                        .edit().putString(MainApplication.PREFERENCE_USER,user.getText().toString())
                        .apply();

                login();


            }
        });
    }

    private void login() {
        /* A - B */
        etDis.setEnabled(false);
        SharedPreferences.Editor example = Savesettings.edit();

        // Save the edit text state to the shared preferences retrieved above
        example.putBoolean("isTv2Disabled", true);
        example.apply();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    /* B2. This constructor will allow the tracking when the userField & passwordField aren´t
    empty and then enabling the login button */
    private final TextWatcher userAndPasswordFilledInWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Log.d("TAG","1.Before");

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d("TAG","2.OnText");
            //textView.setVisibility(View.VISIBLE);

        }

        public void afterTextChanged(Editable s) {
           /* If user field and password field are not equal to zero, set button as Enabled */
            loginButton.setEnabled(
                    user.getText().length() > 0 && password.getText().length() > 0);

        }
    };
}
