package de.nilix.simplebackup;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by nico99 on 12.07.17.
 */

public class Settings extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_settings);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.translateout);
    }
}
