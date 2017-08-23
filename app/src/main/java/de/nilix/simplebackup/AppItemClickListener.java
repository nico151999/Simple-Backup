package de.nilix.simplebackup;

import android.content.pm.ApplicationInfo;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Felix on 16.07.2017.
 */

public interface AppItemClickListener {
    void onAppItemClick(int pos, ApplicationInfo appItem, ImageView sharedImageView, TextView sharedTextView, CardView sharedCardView, ImageView secondSharedImageView, TextView secondSharedTextView);

    void onBackupClick(int pos, ApplicationInfo appItem);
}