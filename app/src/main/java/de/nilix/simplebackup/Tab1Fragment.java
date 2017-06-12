package de.nilix.simplebackup;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class Tab1Fragment extends Fragment {
    private static final String TAG = "Tab1Fragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment, container, false);

        loadApps(view);

        return view;
    }

    private void loadApps(View view) {
        List appList = new ArrayList<String>();

        PackageManager pm = getActivity().getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        /*for (int i=0; i<packages.size(); i++)
        {

            appList.add((String)pm.getApplicationLabel(packages.get(i)));
        }*/

        for (ApplicationInfo info : packages) {
            appList.add(info.packageName);
        }

        ListView appListView = (ListView) view.findViewById(R.id.appListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, appList);

        appListView.setAdapter(adapter);

    }
}
