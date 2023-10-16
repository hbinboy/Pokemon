package com.hb.pokemon.detail

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.hb.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * ViewModel class responsible for managing detailed information about a Pokemon.
 *
 * @param application The application context.
 *
 * @author Robin
 * @version 1.0
 * @since 2023-10-15
 */
class DetailViewModel(application: Application) : BaseViewModel(application) {

    // Repository to fetch detailed Pokemon data.
    private val repository = DetailRepository(application)

    // LiveData for holding detailed Pokemon information.
    private var data: MutableLiveData<DetailData>? = null

    /**
     * Get LiveData for detailed Pokemon data.
     *
     * @return LiveData containing detailed Pokemon information.
     */
    fun getData(): MutableLiveData<DetailData> {
        if (data == null) {
            data = MutableLiveData<DetailData>()
        }
        return data as MutableLiveData<DetailData>
    }

    /**
     * Search for detailed information about a Pokemon by its name.
     *
     * @param name The name of the Pokemon to search for.
     */
    fun search(name: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var result = repository.getData(name)
                data?.postValue(result)
            } catch (e: Exception) {
                e.printStackTrace()
                errorFlag.postValue(true)
            }
        }
    }
}