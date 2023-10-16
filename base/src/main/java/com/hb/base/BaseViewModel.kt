package com.hb.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

/**
 * Base ViewModel class providing common functionality for view models in the application.
 *
 * @property application The application context.
 *
 * @author Robin
 * @version 1.0
 * @since 2023-10-14
 */
open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * LiveData object representing the error state.
     */
    protected var errorFlag = MutableLiveData<Boolean>()

    /**
     * Returns the LiveData object representing the error state.
     *
     * @return LiveData<Boolean> object indicating if an error occurred.
     */
    fun getError(): MutableLiveData<Boolean> {
        return errorFlag
    }

}