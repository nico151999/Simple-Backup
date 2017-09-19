package de.nilix.simplebackup;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PackageListAdapter extends RecyclerView.Adapter<PackageListAdapter.ViewHolder> {

    private PackageManager pm;
    private List<ApplicationInfo> dataSource;
    private Context context;
    private final AppItemClickListener appItemClickListener;

    public PackageListAdapter(List<ApplicationInfo> dataArgs, AppItemClickListener appItemClickListener, PackageManager pm, Context context){
        dataSource = dataArgs;
        this.pm = pm;
        this.context = context;
        this.appItemClickListener = appItemClickListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_installed_app, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ApplicationInfo appItem = dataSource.get(position);

        Drawable icon = dataSource.get(position).loadIcon(pm);

        holder.packageName.setText(getAppName(appItem, pm));
        holder.packageIcon.setImageDrawable(icon);

        holder.packageNumber.setText(Integer.toString(position + 1));

        Bitmap iconBitmap = drawableToBitmap(icon);
        BlurHelper blurHelper = new BlurHelper(25f);
        Bitmap blurred = blurHelper.blur(context, iconBitmap);

        holder.packageBackground.setImageBitmap(blurred);

        //ViewCompat.setTransitionName(holder.packageIcon, appItem.name);

        holder.packageIcon.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appItemClickListener.onAppItemClick(holder.getAdapterPosition(), appItem, holder.packageIcon, holder.packageName, holder.cardView, holder.packageIconBackground, holder.packageNumber, holder.buttonBackup, holder.appbarLayout1, holder.appbarLayout2, holder.main_toolbar, holder.details_toolbar, holder.tabs);
            }
        });

        holder.buttonBackup.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appItemClickListener.onBackupClick(holder.getAdapterPosition(), appItem);
            }
        });
    }



    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView packageName;
        protected ImageButton packageIcon;
        protected ImageView packageBackground;
        protected TextView packageNumber;
        protected Button buttonBackup;
        protected CardView cardView;
        protected ImageView packageIconBackground;
        protected AppBarLayout appbarLayout1;
        protected AppBarLayout appbarLayout2;
        protected Toolbar main_toolbar;
        protected Toolbar details_toolbar;
        protected TabLayout tabs;

        public ViewHolder(View itemView) {
            super(itemView);

            // define view elements
            packageName = (TextView) itemView.findViewById(R.id.package_name);
            packageIcon = (ImageButton) itemView.findViewById(R.id.package_icon);
            packageBackground = (ImageView) itemView.findViewById(R.id.package_background);
            packageNumber = (TextView) itemView.findViewById(R.id.app_number);
            cardView = (CardView) itemView.findViewById(R.id.card);
            buttonBackup = (Button) itemView.findViewById(R.id.button);
            packageIconBackground = (ImageView) itemView.findViewById(R.id.icon_background);
            appbarLayout1 = (AppBarLayout) itemView.findViewById(R.id.appbar);
            appbarLayout2 = (AppBarLayout) itemView.findViewById(R.id.details_bar);
            main_toolbar = (Toolbar) itemView.findViewById(R.id.toolbar);
            details_toolbar = (Toolbar) itemView.findViewById(R.id.details_toolbar);
            tabs = (TabLayout) itemView.findViewById(R.id.tabs);
        }

    }


    // Helpers
    private String getAppName(ApplicationInfo info, PackageManager pm) {
        return info.loadLabel(pm).toString();
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
