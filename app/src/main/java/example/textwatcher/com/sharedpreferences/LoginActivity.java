package example.textwatcher.com.sharedpreferences;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class LoginActivity extends AppCompatActivity {

    /*A*/
    SharedPreferences Savesettings ;

    EditText user, password , etDis;
    Button loginButton;
    ImageView imageButtonSettings;
    int imageController = 0 ;
    int droid = 0 ;
    ImageView logo ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*Part A1. Understanding sharedPreferences specifying file name . S1. **/
        etDis = (EditText)findViewById(R.id.etDis);
        Savesettings = getSharedPreferences("settingFile", MODE_PRIVATE);

        /* B1. Casting of the widgets*/
        user = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.button_login);

        /*C1. Casting of the Settings ImageView*/
        imageButtonSettings = (ImageView)findViewById(R.id.button_settings);

        /*D1. Casting of the Android ImageView*/
        logo = (ImageView)findViewById(R.id.androidLogo);
     /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
     /*++++++++++++++++++++++    END CASTING  ++++++++++++++++++++++++++++++++++++++++++++++++*/

        /* A2. Using a boolean for showing an editText in loginActivity
          If value for key doesn´t exist, then return second param value -  if (a || b ) are false
          @param Savesettings.getBoolean("isTv2Disabled",false) - in this case false */
        if (Savesettings.contains("isTv2Disabled") || Savesettings.getBoolean("isTv2Disabled",false)) {
            etDis.setEnabled( false );
            etDis.setVisibility(View.GONE);
        }

        /* B3. Set Text Watcher listener for the 2 editTexts*/
        user.addTextChangedListener(userAndPasswordFilledInWatcher);
        password.addTextChangedListener(userAndPasswordFilledInWatcher);

        //(getContext()
        /* D. Using sharedPref within another class for managing the preference
        * @param preferences.getString() */
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

        /*C2. Pop Up an AlertDialog after clicking the settings */
        imageButtonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /*Note 1*/
                LayoutInflater inflater = LayoutInflater.from(LoginActivity.this);
                View dialogView = inflater.inflate(R.layout.view_settings, null);

               final RadioGroup androidLogo = (RadioGroup) dialogView.findViewById(R.id.androidLogo);

               AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
              //RadioButton rb1 = (RadioButton) dialogView.findViewById(R.id.button1);

                builder.setTitle("Android Logo");
                builder.setView(dialogView);
                builder.setPositiveButton("Ok",new DialogInterface.OnClickListener() { //Replace null for newDialogIterface.onClickListener....
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor example2 = Savesettings.edit();
                        // Show Pictures
                        if (imageController == 1) {  example2.putInt("Droid", 1); example2.apply();
                            logo.setImageResource(R.drawable.whitedroid);
                            //logo.setVisibility(View.VISIBLE);
                            }
                        if (imageController == 2) {
                            example2.putInt("Droid", 2);
                            example2.apply();
                            logo.setImageResource(R.drawable.blackdroid);
                            // logo.setVisibility(View.VISIBLE);  }// Storing integer
                        }
                        if (imageController == 3) {
                            example2.putInt("Droid", 3);
                            example2.apply();
                            logo.setImageResource(R.drawable.greendroid);
                            //logo.setVisibility(View.VISIBLE); }
                        }
                            }
                });
                builder.show();
                //addListenerRadioGroup();
                androidLogo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch(checkedId) {
                            case R.id.button1: imageController = 1 ; Log.i("ID", "Boton1"); break;
                            case R.id.button2: imageController = 2 ; Log.i("ID", "Boton2"); break;
                            case R.id.button3: imageController = 3 ; Log.i("ID", "Boton3"); break;
                        }
                    }
                });
            }
        });

       // showAndroid();
    }

    /*private void showAndroid() {

       droid = Savesettings.getInt("whitedroid",0);
        if (droid == 0) {
            logo.setVisibility(View.GONE);
        } else {
            if (droid == 1) {
                logo.setImageResource(R.drawable.whitedroid);
                logo.setVisibility(View.VISIBLE);
            }
            if (droid == 2) {
                logo.setImageResource(R.drawable.blackdroid);
                logo.setVisibility(View.VISIBLE);
            }
            if (droid == 3) {
                logo.setImageResource(R.drawable.greendroid);
                logo.setVisibility(View.VISIBLE);
            }
        }
    }*/

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


/* Note 1 : //Research pending when using a fragment format Layoutinflater inflater...... should be casted
     /*public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);*/

/*S1 - http://stackoverflow.com/questions/2614719/how-do-i-get-the-sharedpreferences-from-a-preferenceactivity-in-android*/