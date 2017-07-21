package de.nilix.simplebackup;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by nico99 on 16.07.17.
 */

public class DetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        supportPostponeEnterTransition();

        PackageManager pm = getPackageManager();

        Bundle extras = getIntent().getExtras();
        ApplicationInfo applicationInfo = extras.getParcelable(Tab1Fragment.EXTRA_APP_ITEM);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        //TextView appLabel = (TextView) findViewById(R.id.appLabel);

        //appLabel.setText(applicationInfo.packageName);
        imageView.setImageDrawable(applicationInfo.loadIcon(pm));
        supportStartPostponedEnterTransition();

    }
}
