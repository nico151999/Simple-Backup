package de.nilix.simplebackup;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
        int intPosition = extras.getInt(Tab1Fragment.EXTRA_APP_ITEM2) + 1;

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        TextView appTitle = (TextView) findViewById(R.id.package_name);
        TextView appNumber = (TextView) findViewById(R.id.detailsAppNumber);

        appTitle.setText(applicationInfo.loadLabel(pm).toString());
        imageView.setImageDrawable(applicationInfo.loadIcon(pm));
        appNumber.setText(Integer.toString(intPosition));
        supportStartPostponedEnterTransition();

        getWindow().getSharedElementEnterTransition().setDuration(750);
        getWindow().getSharedElementReturnTransition().setDuration(750);

        getWindow().setAllowEnterTransitionOverlap(false);
        getWindow().setAllowReturnTransitionOverlap(false);

    }
}
