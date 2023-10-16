package com.hb.pokemon.search
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hb.pokemon.R

/**
 * Adapter for the search results RecyclerView.
 * @param itemList List of search items to display.
 * @param onItemClickListener Listener for item click events.
 *
 * @author Robin
 * @version 1.0
 * @since 2023-10-14
 */
class SearchAdapter(var itemList: List<SearchItem>, private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    /**
     * ViewHolder for the search item view.
     * @param view The view for a single search item.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTip: TextView = view.findViewById(R.id.nameTip)
        val name: TextView = view.findViewById(R.id.name)
        val rateTip: TextView = view.findViewById(R.id.rateTip)
        val rate: TextView = view.findViewById(R.id.captureRate)
        val speciesTip: TextView = view.findViewById(R.id.speciesTip)
        val species: TextView = view.findViewById(R.id.species)
        val bg: View = view.findViewById(R.id.bg)

    }

    /**
     * Creates a ViewHolder by inflating the item layout.
     * @param parent The parent view group.
     * @param viewType The type of the view to be created.
     * @return The created ViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_list_item_layout, parent,false)

        return ViewHolder(view)
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items.
     */
    override fun getItemCount(): Int {
        return itemList.size
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.apply {
            nameTip.setTextColor(getContrastColor(item.color))
            name.text = item.name
            name.setTextColor(getContrastColor(item.color))
            rateTip.setTextColor(getContrastColor(item.color))
            rate.text = item.rate.toString()
            rate.setTextColor(getContrastColor(item.color))
            speciesTip.setTextColor(getContrastColor(item.color))
            species.text = item.species
            species.setTextColor(getContrastColor(item.color))
            bg.setBackgroundColor(item.color)
            holder.itemView.setOnClickListener { onItemClickListener.onItemClick(item) }
        }
    }

    /**
     * Get the contrasting text color based on the background color.
     * @param backgroundColor The background color against which the text will be displayed.
     * @return The text color that contrasts well with the given background color.
     */
    fun getContrastColor(backgroundColor: Int): Int {
        val darkness = 1 - (0.299 * Color.red(backgroundColor) + 0.587 * Color.green(backgroundColor) + 0.114 * Color.blue(backgroundColor)) / 255
        return if (darkness < 0.5) Color.BLACK else Color.WHITE
    }
}