package com.appwrkassignment.base

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import androidx.preference.PreferenceManager

/**
 * BasePrefsManager is an object used to handle shared preferences operations across the application.
 * It provides utility methods to initialize and interact with the shared preferences.
 */
object BasePrefsManager {

    // Declaration of SharedPreferences object to store key-value pairs persistently
    var sharedPreferences: SharedPreferences? = null

    // Declaration of SharedPreferences.Editor object to make changes to the SharedPreferences
    private var editor: SharedPreferences.Editor? = null

    // Key for the shared preferences
    private val PERSISTENCE_PREFS = "PERSISTENCE_PREFS"

    // Shared preferences object for persistent storing the value of shared preferences, until user uninstall the app
    private var sharedPreferencesPersistent: SharedPreferences? = null

    // Editor for persistent shared preferences
    private var editorPersistent: SharedPreferences.Editor? = null

    /**
     * Method to get the value from the shared preferences, based on the shared preference type
     *  @param key - name for the value that we will save
     *  @param value - its a value of any type i.e String, Long, Int, Float and Boolean
     *  @param preferences shared preferences from which value need to be fetched
     */
    private fun <T : Any> getValue(key: String, default: T, preferences: SharedPreferences?): T? {
        val type = default::class.java
        return when (default) {
            is String -> type.cast(preferences?.getString(key, default))
            is Long -> type.cast(preferences?.getLong(key, default))
            is Int -> type.cast(preferences?.getInt(key, default))
            is Float -> type.cast(preferences?.getFloat(key, default))
            is Boolean -> type.cast(preferences?.getBoolean(key, default))
            else -> default
        }
    }

    /**
     * Method to set the value of the shared preferences, based on the editor passed
     * @param key - name for the value that we will save
     * @param value - its a value of any type i.e String, Long, Int, Float and Boolean
     * @param editor editor to which value need to be set
     */
    private fun <T : Any> setValue(
        key: String,
        value: T,
        editor: SharedPreferences.Editor?
    ): Boolean {
        when (value) {
            is String -> editor?.putString(key, value)
            is Long -> editor?.putLong(key, value)
            is Int -> editor?.putInt(key, value)
            is Float -> editor?.putFloat(key, value)
            is Boolean -> editor?.putBoolean(key, value)
            else -> return false
        }
        return editor?.commit()!!
    }

    /**
     * Used to initialize the SharedPreference object by passing an application context
     * @param context - Will pass an application context
     */
    fun with(context: Context): BasePrefsManager {
        sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
        editor = sharedPreferences?.edit()
        sharedPreferencesPersistent =
            context.getSharedPreferences(PERSISTENCE_PREFS, Context.MODE_PRIVATE)
        editorPersistent = sharedPreferencesPersistent?.edit()
        return this
    }

    /**
     * Used to clear the all preferences
     */
    fun clearAll() {
        editor?.apply {
            clear()
            apply()
        }
    }

    /**
     * Used to clear the specific key prefernce
     * @param key - contains the name for the key that we want to clear
     */
    fun clearPref(key: String) {
        if (TextUtils.isEmpty(key))
            return
        editor?.apply {
            remove(key)
            apply()
        }
    }

    /**
     * Used to clear the specific key persistent prefernce
     * @param key - contains the name for the key that we want to clear
     */
    fun clearPersistentPref(key: String) {
        if (TextUtils.isEmpty(key))
            return
        editorPersistent?.apply {
            remove(key)
            apply()
        }
    }

    /**
     *Common method to set the value in shared preference
     *@param key - name for the value that we will save
     *@param value - its a value of any type i.e String, Long, Int, Float and Boolean
     *@param isPersistent whether the value need to be stored permanently or not
     */
    fun <T : Any> set(key: String, value: T, isPersistent: Boolean = false): Boolean {
        if (TextUtils.isEmpty(key)) {
            return false
        }
        return if (isPersistent) {
            setValue(key, value, editorPersistent)
        } else {
            setValue(key, value, editor)
        }
    }

    /**
     *Common method to fetch the value from the shared preference
     *@param key - name for the value that we will get from the prefs
     *@param default - contains the default value for the type of data that we will save
     *@param isPersistent whether the value need to be fetched from persistent preferences or default preferences
     */
    fun <T : Any> get(key: String, default: T, isPersistent: Boolean = false): T? {
        return if (isPersistent) {
            getValue(key, default, sharedPreferencesPersistent)
        } else {
            getValue(key, default, sharedPreferences)
        }
    }

    /**
     * Method to check if shared preference has this key or not
     * @param key key to be checked from shared preferences
     */
    fun hasKey(key: String): Boolean {
        return sharedPreferences?.contains(key) ?: false
    }
}