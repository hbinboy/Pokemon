package com.hb.pokemon.search

import android.app.Application
import com.apollographql.apollo3.api.Optional
import com.hb.base.PokemonSpeciesQuery
import com.hb.pokemon.app.MyApplication
import com.hb.pokemon.utils.ColorsUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository class responsible for fetching search data.
 * @property application The Application instance.
 *
 * @author Robin
 * @version 1.0
 * @since 2023-10-14
 */
class SearchRepository(val application: Application) {

    /**
     * Fetches search data based on the given parameters.
     * @param name The name to search for.
     * @param offset The offset for pagination.
     * @param count The number of items to fetch.
     * @return ArrayList of SearchItem containing search results.
     */
    suspend fun getData(name: String, offset: Int, count: Int) : ArrayList<SearchItem> {

        val deferredResult: ArrayList<SearchItem> =
            withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                search(name, offset, count)
            }
        return deferredResult
    }

    /**
     * Performs a search based on the given parameters.
     * @param name The name to search for.
     * @param offset The offset for pagination.
     * @param count The number of items to fetch.
     * @return ArrayList of SearchItem containing search results.
     */
    private suspend fun search(name: String, offset: Int, count: Int): ArrayList<SearchItem> {
        var result = ArrayList<SearchItem>()
            var response = MyApplication.app.apolloClient.query(
                PokemonSpeciesQuery(
                    name = Optional.present(name),
                    offset = Optional.present(offset), count = Optional.present(count)
                )
            ).execute()
            response.data?.pokemon_v2_pokemonspecies?.forEach { specy ->
                val pokemons = StringBuilder()
                specy.pokemon_v2_pokemons?.forEach { pokemon ->
                    pokemons.append(pokemon.name).append("\n")
                }
                val searchItem =
                    specy.capture_rate?.let {
                        specy.pokemon_color_id?.let { it1 -> ColorsUtils.switchColor(it1) }
                            ?.let { it2 ->
                                SearchItem(
                                    specy.name, it, pokemons.toString(),
                                    it2
                                )
                            }
                    }
                if (searchItem != null) {
                    result.add(searchItem)
                }
            }
        return result
    }
}