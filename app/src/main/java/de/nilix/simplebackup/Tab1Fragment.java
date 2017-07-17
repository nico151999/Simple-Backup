package de.nilix.simplebackup;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.Collections;
import java.util.List;


public class Tab1Fragment extends Fragment {
    private static final String TAG = "Tab1Fragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);

        PackageManager pm = getActivity().getPackageManager();
        List<ApplicationInfo> packages = getAppsWrapper();

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        //RecyclerView.Adapter adapter = new RecyclerAdapter(packageNamesArray); // Set data source // Old way
        PackageListAdapter adapter = new PackageListAdapter(packages, pm, getContext());
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

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ApplicationInfo item) {
                testIntent();
            }
        });

        return view;
    }

    private List<ApplicationInfo> getAppsWrapper() {
        PackageManager pm = getActivity().getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(0);
        Collections.sort(packages, new ApplicationInfo.DisplayNameComparator(pm));

        return packages;

    }


    public void testIntent() {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);

        startActivity(intent);
    }
}
