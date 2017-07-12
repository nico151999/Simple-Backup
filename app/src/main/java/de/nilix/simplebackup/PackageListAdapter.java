package de.nilix.simplebackup;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
    private Context context;

    public PackageListAdapter(List<ApplicationInfo> dataArgs, PackageManager pm, Context context){
        dataSource = dataArgs;
        this.pm = pm;
        this.context = context;
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
        Drawable icon = dataSource.get(position).loadIcon(pm);

        holder.packageName.setText(getAppName(dataSource.get(position), pm));
        holder.packageIcon.setImageDrawable(icon);

        Bitmap iconBitmap = drawableToBitmap(icon);
        BlurHelper blurHelper = new BlurHelper(25f);
        Bitmap blurred = blurHelper.blur(context, iconBitmap);

        holder.packageBackground.setImageBitmap(blurred);
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener

    {
        protected TextView packageName;
        protected ImageView packageIcon;
        protected ImageView packageBackground;
        protected Button buttonBackup;

        public ViewHolder(View itemView) {
            super(itemView);

            // define view elements
            packageName =  (TextView) itemView.findViewById(R.id.package_name);
            packageIcon = (ImageView) itemView.findViewById(R.id.package_icon);
            packageBackground = (ImageView) itemView.findViewById(R.id.package_background);

            buttonBackup = (Button) itemView.findViewById(R.id.button);
            buttonBackup.setOnClickListener(this);
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
