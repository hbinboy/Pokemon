package com.hb.pokemon.search

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.hb.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel class for managing search functionality.
 * @property application The application context.
 *
 * @author Robin
 * @version 1.0
 * @since 2023-10-14
 */
class SearchViewModel(application: Application) : BaseViewModel(application) {
    var offset: Int = 0
    var count: Int = 10

    private var data: MutableLiveData<ArrayList<SearchItem>>? = null

    private var repository = SearchRepository(application)

    /**
     * Gets the LiveData object for search results.
     * @return MutableLiveData<ArrayList<SearchItem>> The LiveData object containing search results.
     */
    fun getData(): MutableLiveData<ArrayList<SearchItem>> {
        if (data == null) {
            data = MutableLiveData<ArrayList<SearchItem>>()
        }
        return data as MutableLiveData<ArrayList<SearchItem>>
    }

    /**
     * Fetches the next page of search results.
     * @param name The name to search for.
     */
    fun nextPage(name: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                offset += count
                var result = repository.getData(name, offset, count)
                data?.apply {
                    value?.addAll(result)
                    postValue(value)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                errorFlag.postValue(true)
            }
        }
    }

    /**
     * Performs a new search operation.
     * @param name The name to search for.
     */
    fun newSearch(name: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                offset = 0
                var result = repository.getData(name, offset, count)
                data?.apply {
                    value?.clear()
                    postValue(result)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                errorFlag.postValue(true)
            }
        }
    }
}