//TODO: Remove deprecated adapter

package de.nilix.simplebackup

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class RecyclerAdapter(private val dataSource: Array<String>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.app_listview_item, parent, false)

        val viewHolder = ViewHolder(view)
        return viewHolder


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = dataSource[position]
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView

        init {
            textView = itemView.findViewById(R.id.package_name) as TextView

        }


    }
}
