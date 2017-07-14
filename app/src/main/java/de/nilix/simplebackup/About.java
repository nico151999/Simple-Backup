package de.nilix.simplebackup;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by nico99 on 12.07.17.
 */

public class About extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_about);
    }
}
