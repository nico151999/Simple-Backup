package de.nilix.simplebackup;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.LayerDrawable;
import android.media.Image;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class PackageListAdapter extends RecyclerView.Adapter<PackageListAdapter.ViewHolder> {

    private PackageManager pm;
    private List<ApplicationInfo> dataSource;

    public PackageListAdapter(List<ApplicationInfo> dataArgs, PackageManager pm){
        dataSource = dataArgs;
        this.pm = pm;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.app_listview_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.packageName.setText(getAppName(dataSource.get(position), pm));
        holder.packageIcon.setImageDrawable(dataSource.get(position).loadIcon(pm));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener

    {
        protected TextView packageName;
        protected ImageView packageIcon;
        protected Button buttonBackup;
        protected Button buttonInfo;

        public ViewHolder(View itemView) {
            super(itemView);

            // define view elements
            packageName =  (TextView) itemView.findViewById(R.id.package_name);
            packageIcon = (ImageView) itemView.findViewById(R.id.package_icon);

            buttonBackup = (Button) itemView.findViewById(R.id.button);
            buttonInfo = (Button) itemView.findViewById(R.id.button2);

            buttonBackup.setOnClickListener(this);
            buttonInfo.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "position = " + getPosition(), Toast.LENGTH_SHORT).show();
        }

    }

    
    // Helpers
    private String getAppName(ApplicationInfo info, PackageManager pm) {
        return info.loadLabel(pm).toString();
    }
}
