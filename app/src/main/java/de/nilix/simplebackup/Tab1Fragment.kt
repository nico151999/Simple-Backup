package de.nilix.simplebackup

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

import java.util.ArrayList
import java.util.Collections


class Tab1Fragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.tab1_fragment, container, false)

        val pm = activity.packageManager
        val packages = appsWrapper

        val recyclerView = view.findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        //RecyclerView.Adapter adapter = new RecyclerAdapter(packageNamesArray); // Set data source // Old way
        val adapter = PackageListAdapter(packages, pm, context)
        recyclerView.adapter = adapter


        return view
    }

    private val appsWrapper: List<ApplicationInfo>
        get() {
            val pm = activity.packageManager
            val packages = pm.getInstalledApplications(0)
            Collections.sort(packages, ApplicationInfo.DisplayNameComparator(pm))

            return packages

        }

    companion object {
        private val TAG = "Tab1Fragment"
    }
}
