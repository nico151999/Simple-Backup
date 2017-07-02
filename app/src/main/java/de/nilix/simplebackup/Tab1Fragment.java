package de.nilix.simplebackup;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

        PackageManager pm = getActivity().getPackageManager();
        List<ApplicationInfo> packages = getAppsWrapper();
        //List<ApplicationInfo> packageNames =  new ArrayList<~>();

        // Populate packageName list
        /*
        PackageManager pm = getActivity().getPackageManager();
        for (PackageInfo info : packages) {
            packageNames.add(info.applicationInfo.loadLabel(pm).toString());
        }
        // Populate array
        String[] packageNamesArray = packageNames.toArray(new String[packageNames.size()]);
        */

        // Sample data
        // String[] dataArray = new String[]{"Test1","Test2","Test3","Test4","Test5","Test6","Test7","Test8","Test9","Test10","Test11"};

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        //RecyclerView.Adapter adapter = new RecyclerAdapter(packageNamesArray); // Set data source
        PackageListAdapter adapter = new PackageListAdapter(packages, pm);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private List<ApplicationInfo> getAppsWrapper() {
        PackageManager pm = getActivity().getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        return packages;

        /*for (int i=0; i<packages.size(); i++)
        {

            appList.add((String)pm.getApplicationLabel(packages.get(i)));
        }*/

        //ListView appListView = (ListView) view.findViewById(R.id.appListView);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, appList);

        //appListView.setAdapter(adapter);

    }
}
