package de.nilix.simplebackup;

import android.content.pm.ApplicationInfo;
import android.widget.ImageView;

/**
 * Created by Felix on 16.07.2017.
 */

public interface AppItemClickListener {
    void onAppItemClick(int pos, ApplicationInfo appItem, ImageView sharedImageView);
}
