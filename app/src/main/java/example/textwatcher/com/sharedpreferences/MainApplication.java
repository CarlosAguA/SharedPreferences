package example.textwatcher.com.sharedpreferences;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Paviliondm4 on 1/9/2017.
 */


public class MainApplication  extends Application {

    private SharedPreferences preferences;
    public static final String PREFERENCE_USER = "user";

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

    }
}
