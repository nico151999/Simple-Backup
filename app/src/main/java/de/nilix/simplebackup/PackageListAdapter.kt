package de.nilix.simplebackup

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.media.Image
import android.support.v7.widget.ButtonBarLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class PackageListAdapter(private val dataSource: List<ApplicationInfo>, private val pm: PackageManager, private val context: Context) : RecyclerView.Adapter<PackageListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.app_listview_item, parent, false)

        val viewHolder = ViewHolder(view)
        return viewHolder


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.packageName.text = getAppName(dataSource[position], pm)
        holder.packageIcon.setImageDrawable(dataSource[position].loadIcon(pm))

        val icon = dataSource[position].loadIcon(pm)
        val bitmap = drawableToBitmap(icon)

        val blurHelper = BlurHelper(20f)
        val blurred = blurHelper.blur(context, bitmap)

        holder.packageBackground.setImageBitmap(blurred)
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var packageName: TextView
        var packageIcon: ImageView
        var packageBackground: ImageView
        var buttonBackup: Button

        init {
            // define view elements
            packageName = itemView.findViewById(R.id.package_name) as TextView
            packageIcon = itemView.findViewById(R.id.package_icon) as ImageView
            packageBackground = itemView.findViewById(R.id.package_background) as ImageView

            buttonBackup = itemView.findViewById(R.id.button) as Button

            buttonBackup.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            Toast.makeText(view.context, "position = " + position, Toast.LENGTH_SHORT).show()
        }

    }

    // Helpers
    private fun getAppName(info: ApplicationInfo, pm: PackageManager): String {
        return info.loadLabel(pm).toString()
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        var bitmap: Bitmap

        if (drawable is BitmapDrawable) {
            val bitmapDrawable = drawable as BitmapDrawable
            if (bitmapDrawable.bitmap != null) {
                return bitmapDrawable.bitmap
            }
        }

        if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        } else {
            bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888);
        }

        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}
