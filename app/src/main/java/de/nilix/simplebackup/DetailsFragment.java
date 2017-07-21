package de.nilix.simplebackup;

import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Felix on 16.07.2017.
 */

public class DetailsFragment extends Fragment {

    private static final String EXTRA_APP_ITEM = "app_item";
    private static final String EXTRA_TRANSITION_NAME = "transition_name";

    public DetailsFragment() {

    }

    public static DetailsFragment newInstance(ApplicationInfo appItem, String transitionName) {
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_APP_ITEM, appItem);
        bundle.putString(EXTRA_TRANSITION_NAME, transitionName);
        detailsFragment.setArguments(bundle);
        return detailsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();

        View view = inflater.inflate(R.layout.fragment_details, container, false);

        return view;
    }
}
