package com.appwrkassignment.base

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.appwrkassignment.R
import com.appwrkassignment.managers.UserProfilePrefs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * BaseActivity serves as a common base class for all activities in the application.
 * It provides common functionality and utilities that can be shared across multiple activities.
 */
@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    //variable to hold localization json as hashmap
    private var errorCodesMap: HashMap<String, String>? = null

    private var alertDialog: AlertDialog? = null

    @Inject
    lateinit var userProfilePrefsBase: UserProfilePrefs


    /**
     * This method is used to get root view of each activity so that if we need root layout of currently
     * opened activity in base to show alert or snack bar, it can be used to get view.
     */
    protected abstract fun rootLayout(): View?

    /**
     * This method is used to get the view model instance of each view model that is being used in activity to
     * provide a set of common functionalities that can be used across different activities and view models
     */
    protected abstract fun baseViewModel(): BaseViewModel?

    /**
     * This method is used to get api error message and pass into the currently opened fragments.
     */
    protected abstract fun <T> onAPIErrorMessage(model: T?)

    //variable to hold Snackbar instance
    private var snackBar: Snackbar? = null


    /**
     * This is the method where we initialize the activity. Most importantly,
     * here will usually call setContentView(int) with a layout resource defining the UI
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the orientation to portrait
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        setContentView(rootLayout())
    }


    /**
     * This method is used to handle initialisation of object, listeners in the activities. It will
     * always be called when initComponents in the view will be called. for ex. it can be used to clear
     * memory, to save data in shared preference etc.
     */
    @CallSuper
    protected open fun initComponents() {
    }

    /**
     * This method is used to handle error and loading state directly from base view model.
     */
    @CallSuper
    protected open fun observeViewModel() {

    }

    /**
     * Displays a toast message with the provided message.
     * @param msg The message to be displayed in the toast.
     */
    open fun showToastMsg(msg: String?) {
        // Show a toast message with the provided message
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


    /**
     * Replaces the fragment in the specified container with the given fragment.
     * @param containerId The ID of the container view in which to replace the fragment.
     * @param fragment The fragment to replace the current fragment.
     * @param addToStack Whether to add the transaction to the back stack.
     * @param bundle Optional bundle to pass arguments to the fragment.
     */
    open fun changeFragment(
        containerId: Int, fragment: Fragment, addToStack: Boolean, bundle: Bundle? = null
    ) {
        if (bundle != null) fragment.arguments = bundle
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(containerId, fragment, fragment.tag)
        if (addToStack) {
            transaction.addToBackStack(fragment.tag)
        }
        transaction.commit()
    }

    /**
     * Launches an activity without adding it to the back stack.
     * @param clazz The class of the activity to be launched
     * @param clearStack Determines whether to clear the activity stack before launching
     */
    protected open fun launchActivityWithoutHistory(
        clazz: Class<*>?, clearStack: Boolean
    ) {
        val i = Intent(this, clazz)
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        if (clearStack) {
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(i)
    }

    /**
     * Displays a custom confirmation dialog.
     * @param dialogMsg dialogMsg to show on dialog
     * @param onConfirm callback for click on confirm button
     */
    fun showConfirmationDialog(dialogMsg: String, onConfirm: () -> Unit) {
        if (alertDialog != null) return
        alertDialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.def_alert))
            .setMessage(dialogMsg)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.def_confirm)) { dialog, _ ->
                onConfirm()
                dialog.dismiss()
                alertDialog = null
            }
            .setNegativeButton(getString(R.string.def_cancel)) { dialog, _ ->
                dialog.dismiss()
                alertDialog = null
            }
            .create()

        // Show the dialog
        alertDialog?.show()
    }
}