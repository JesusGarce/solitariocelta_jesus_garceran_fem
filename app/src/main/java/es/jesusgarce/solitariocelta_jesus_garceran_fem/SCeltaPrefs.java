package es.jesusgarce.solitariocelta_jesus_garceran_fem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class SCeltaPrefs extends PreferenceActivity {

    public void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean darkMode = sharedPref.getBoolean(getString(R.string.prefKeyModoOscuro), false);

        sharedPref.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);

        if (darkMode)
            setTheme(R.style.AppThemeDark);

        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);
    }

    SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    if (key.equals(getString(R.string.prefKeyModoOscuro)))
                        restartApp();
                }
            };

    private void restartApp(){
        this.recreate();
    }
}
