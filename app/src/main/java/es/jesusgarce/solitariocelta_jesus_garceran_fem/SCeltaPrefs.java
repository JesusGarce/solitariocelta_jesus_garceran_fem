package es.jesusgarce.solitariocelta_jesus_garceran_fem;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SCeltaPrefs extends PreferenceActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
