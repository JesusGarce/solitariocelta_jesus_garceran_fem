package es.jesusgarce.solitariocelta_jesus_garceran_fem;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

public class SCeltaPrefs extends PreferenceActivity implements OnSharedPreferenceChangeListener {
    public final String LOG_KEY = "JGS";

    public void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean darkMode = sharedPref.getBoolean(getString(R.string.prefKeyModoOscuro), false);

        if (darkMode)
            setTheme(R.style.AppThemeDark);

        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.i(LOG_KEY, "Preferencia " + key + " ha cambiado");
    }

}
