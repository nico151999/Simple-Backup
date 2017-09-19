package de.nilix.simplebackup;

import android.content.pm.ApplicationInfo;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Felix on 16.07.2017.
 */

public interface AppItemClickListener {
    void onAppItemClick(int pos, ApplicationInfo appItem, ImageView sharedImageView, TextView sharedTextView, CardView sharedCardView, ImageView secondSharedImageView, TextView secondSharedTextView, Button sharedButtonView, AppBarLayout sharedAppBarLayout1, AppBarLayout sharedAppBarLayout2, Toolbar sharedToolbar1, Toolbar sharedToolbar2, TabLayout sharedTabLayout);

    void onBackupClick(int pos, ApplicationInfo appItem);
}