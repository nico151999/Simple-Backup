package de.nilix.simplebackup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by nico99 on 12.07.17.
 */

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.translateout);
    }
}
