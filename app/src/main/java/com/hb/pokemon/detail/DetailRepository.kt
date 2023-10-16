package com.hb.pokemon.detail

import android.app.Application
import com.apollographql.apollo3.api.Optional
import com.hb.base.PokemonabilitiesQuery
import com.hb.pokemon.app.MyApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository class responsible for fetching detailed data about a Pokemon's abilities.
 *
 * @param application The application context.
 *
 * @author Robin
 * @version 1.0
 * @since 2023-10-15
 */
class DetailRepository(private val application: Application) {

    /**
     * Coroutine function to fetch detailed data about a Pokemon's abilities.
     *
     * @param name The name of the Pokemon to fetch abilities for.
     * @return Detailed data about the Pokemon's abilities.
     */
    suspend fun getData(name: String): DetailData {
        // Fetch detailed data about the Pokemon's abilities using GraphQL query.
        val deferredResult: DetailData = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            getDetail(name)
        }
        return deferredResult
    }

    /**
     * Coroutine function to execute GraphQL query and extract detailed abilities data.
     *
     * @param name The name of the Pokemon to fetch abilities for.
     * @return Detailed data about the Pokemon's abilities.
     */
    private suspend fun getDetail(name: String): DetailData {
        var result = DetailData(name, ArrayList())
        var response = MyApplication.app.apolloClient.query(
            PokemonabilitiesQuery(Optional.present(name))
        ).execute()
        response.data?.pokemon_v2_pokemonspecies?.forEach { specy ->
            specy.pokemon_v2_pokemons.forEach { abilities ->
                abilities.pokemon_v2_pokemonabilities.forEach { ability ->
                    ability.pokemon_v2_ability?.name?.let { result.abilities.add(it) }
                }
            }
        }
        return result
    }
}