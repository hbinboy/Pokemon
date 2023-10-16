package com.hb.pokemon.search

/**
 * Data class representing a search item.
 * @property name The name of the search item.
 * @property rate The rate of the search item.
 * @property species The species of the search item.
 * @property color The background color associated with the search item.
 *
 * @author Robin
 * @version 1.0
 * @since 2023-10-14
 */
data class SearchItem(val name: String, val rate: Int, val species: String, val color: Int)
