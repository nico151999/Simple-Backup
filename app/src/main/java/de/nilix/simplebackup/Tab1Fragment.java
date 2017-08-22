package de.nilix.simplebackup;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;


public class Tab1Fragment extends Fragment implements AppItemClickListener {

    public static final String EXTRA_APP_ITEM = "packageIcon";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);



        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        PackageManager pm = getActivity().getPackageManager();
        List<ApplicationInfo> packages = getAppsWrapper();

        TextView appCounter = (TextView)view.findViewById(R.id.app_counter);
        appCounter.setText(String.format(getResources().getString(R.string.total_installed_apps), Integer.toString(packages.size())));

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        PackageListAdapter adapter = new PackageListAdapter(packages, this, pm, getContext());
        recyclerView.setAdapter(adapter);

        recyclerView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        recyclerView.getViewTreeObserver().removeOnPreDrawListener(this);

                        for (int i = 0; i < recyclerView.getChildCount(); i++) {
                            View v = recyclerView.getChildAt(i);
                            v.setTranslationY(1500);
                            v.animate().translationY(0)
                                    .setDuration(350)
                                    .setStartDelay(i * 25)
                                    .start();
                        }

                        return true;
                    }
                });
    }



    private List<ApplicationInfo> getAppsWrapper() {
        PackageManager pm = getActivity().getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(0);
        Collections.sort(packages, new ApplicationInfo.DisplayNameComparator(pm));

        return packages;

    }

    @Override
    public void onAppItemClick(int pos, ApplicationInfo appItem, ImageView sharedImageView, TextView sharedTextView) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(EXTRA_APP_ITEM, appItem);

        Pair<View, String> p1 = Pair.create((View)sharedImageView, "appIconTransition");
        Pair<View, String> p2 = Pair.create((View)sharedTextView, "appNameTransition");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), p1, p2);

        startActivity(intent, options.toBundle());
    }

    @Override
    public void onBackupClick(int pos, ApplicationInfo appItem) {
        Toast toast = Toast.makeText(getActivity(), appItem.packageName, Toast.LENGTH_SHORT);
        toast.show();
    }


}
