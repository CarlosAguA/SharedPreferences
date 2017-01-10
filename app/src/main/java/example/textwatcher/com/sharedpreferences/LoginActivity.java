package example.textwatcher.com.sharedpreferences;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginActivity extends AppCompatActivity {

    /*A*/
    SharedPreferences Savesettings ;

    EditText user, password , etDis;
    Button loginButton;
    ImageView imageButtonSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Part A1. Understanding sharedPreferences
        etDis = (EditText)findViewById(R.id.etDis);
        Savesettings = getSharedPreferences("settingFile", MODE_PRIVATE);

        /* B1. Casting of the widgets*/
        user = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.button_login);

        /*C1. Casting of the ImageView*/
        imageButtonSettings = (ImageView)findViewById(R.id.button_settings);
     /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
     /*++++++++++++++++++++++    END CASTING  ++++++++++++++++++++++++++++++++++++++++++++++++*/

        /* A2.  If value for key not exist then return second param value -
            = if (a || b ) are false         ("isTv2Disabled",false) - in this case false */
        if (Savesettings.contains("isTv2Disabled") || Savesettings.getBoolean("isTv2Disabled",false)) {
            etDis.setEnabled( false );
            etDis.setVisibility(View.GONE);
        }
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

        imageButtonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //Research pending when using a fragment format Layoutinflater inflater...... should be casted
     /*public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);*/
                LayoutInflater inflater = LayoutInflater.from(LoginActivity.this);
                View dialogView = inflater.inflate(R.layout.view_settings, null);

                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("Android Logo")
                        .setView(dialogView)
                        .setPositiveButton("Accept",null) //Replace null for newDialogIterface.onClickListener....
                        .setNegativeButton("Cancel", null)
                        .show();
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
