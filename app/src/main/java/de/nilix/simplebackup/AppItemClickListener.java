package de.nilix.simplebackup;

import android.content.pm.ApplicationInfo;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Felix on 16.07.2017.
 */

public interface AppItemClickListener {
    void onAppItemClick(int pos, ApplicationInfo appItem, ImageView sharedImageView);

    void onAppItemClick(int pos, ApplicationInfo appItem, TextView sharedTextView);
}
