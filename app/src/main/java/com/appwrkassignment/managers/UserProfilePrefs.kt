package com.appwrkassignment.managers

import android.content.Context
import com.appwrkassignment.base.BasePrefsManager
import com.appwrkassignment.data.DataItemModel
import com.appwrkassignment.utilities.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

/**
 * UserProfilePrefs class extending NetworkSessionManager to manage user profile preferences.
 * This class initializes the base preference manager with the provided context.
 */
class UserProfilePrefs @Inject constructor(val context: Context?) {
    // Instance of BasePrefsManager to handle preference operations
    private val preferenceManager = BasePrefsManager

    // Initialization block to set up the preference manager with the given context
    init {
        // Check if the context is not null
        if (context != null) {
            // Initialize the preference manager with the context
            preferenceManager.with(context)
        }
    }

    /**
     * Retrieves the shared preference manager instance
     * @return The instance of [BasePrefsManager] used for managing user preferences.
     */
    fun getPreferenceManager(): BasePrefsManager {
        return preferenceManager
    }


    fun saveItemData(vitalList: List<DataItemModel>) {
        val data = Gson().toJson(vitalList)
        preferenceManager.set(Constants.ITEM_DATA, data, true)
    }


    fun getItemDate(): MutableList<DataItemModel>? {
        val data = preferenceManager.get(Constants.ITEM_DATA, "", true)
        return if (data != null) {
            val type = object : TypeToken<MutableList<DataItemModel>>() {}.type
            Gson().fromJson(data, type)
        } else {
            null
        }
    }
}