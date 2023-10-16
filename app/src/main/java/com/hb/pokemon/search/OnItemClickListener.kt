package com.hb.pokemon.search

/**
 * Interface definition for a callback to be invoked when an item in the search results is clicked.
 *
 * @author Robin
 * @version 1.0
 * @since 2023-10-14
 */
interface OnItemClickListener {

    /**
     * Called when an item in the search results is clicked.
     * @param item The clicked search item.
     */
    fun onItemClick(item: SearchItem)
}
