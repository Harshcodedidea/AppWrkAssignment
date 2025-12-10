package com.appwrkassignment.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * The BaseViewModel class is used as a parent class for view models that are used in fragments and activities.
 * The purpose of this class is to provide a set of common methods and functionality that can be used
 * across different fragments/activities and view models.
 * @param baseActivity The BaseActivity instance associated with this ViewModel.
 */
open class BaseViewModel : ViewModel() {
    // Used to handle the loader state that is shown before api and hidden after response
    private val loading = MutableLiveData<Boolean>()


    /**
     * Method used to get loading state and is being handled in the base activity/fragment
     */
    open fun getLoading(): LiveData<Boolean?> {
        return loading
    }

    /**
     * Sets the loading LiveData with the provided boolean value.
     * @param value Boolean value indicating whether loading is in progress or not.
     */
    open fun setLoading(value: Boolean) {
        loading.postValue(value)
    }
}