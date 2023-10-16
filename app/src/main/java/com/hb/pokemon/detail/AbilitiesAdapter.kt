package com.hb.pokemon.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hb.pokemon.R

/**
 * ViewModel class responsible for managing detailed information about a Pokemon.
 *
 * @param application The application context.
 *
 * @author Robin
 * @version 1.0
 * @since 2023-10-15
 */
class AbilitiesAdapter(var abilities: List<String>) : RecyclerView.Adapter<AbilitiesAdapter.ViewHolder>() {

    /**
     * ViewHolder class to hold the views for each item in the RecyclerView.
     *
     * @param view The view representing an item in the RecyclerView.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
    }

    /**
     * Creates and returns a new ViewHolder for the RecyclerView items.
     *
     * @param parent The parent ViewGroup into which the new view will be added.
     * @param viewType The type of the new view.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ability_list_item_layout, parent,false)
        return ViewHolder(view)
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in the adapter.
     */
    override fun getItemCount(): Int {
        return abilities.size
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = abilities[position]
        holder.name.text = item
    }
}