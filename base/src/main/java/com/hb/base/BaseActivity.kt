package com.hb.base

import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.AndroidViewModel

/**
 * Abstract base class for activities with data binding and view model support.
 *
 * This class provides common functionality for setting up data binding, initializing view models,
 * and managing loading indicators.
 *
 * @param T The type of data binding class associated with the activity.
 * @param VM The type of view model associated with the activity.
 *
 * @author Robin
 * @version 1.0
 * @since 2023-10-14
 */
abstract class BaseActivity<T : ViewDataBinding, VM : AndroidViewModel?> : AppCompatActivity() {

    /**
     * The data binding instance associated with the layout of the activity.
     */
    lateinit var binding: T
        private set

    /**
     * The view model instance associated with the activity.
     */
    var viewModel: VM? = null

    /**
     * The loading layout container for displaying loading indicators.
     */
    private var loadingLayout: LinearLayout? = null

    /**
     * The loading indicator TextView displayed within the loading layout.
     */
    private var loading: TextView? = null

    /**
     * Called when the activity is first created. Responsible for initializing data binding,
     * view model, action bar, and other common setup tasks.
     *
     * @param savedInstanceState If non-null, this activity is being re-constructed from a
     * previous saved state as given here.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        viewModel = initViewModel()
        setupActionBar()
        init()
    }

    /**
     * Called when a menu item is selected. Handles the home button click event to finish the activity.
     *
     * @param item The selected menu item.
     * @return true if the event was handled, false otherwise.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            else -> {

            }
        }
        return true
    }

    /**
     * Shows a centered loading indicator with the default loading text.
     */
    open fun showCenterLoading() {
        showCenterLoading(getString(R.string.loading_tip))
    }

    /**
     * Shows a centered loading indicator with custom loading text.
     *
     * @param text The loading text to be displayed.
     */
    open fun showCenterLoading(text: String?) {
        addLoadingView()
        if (loadingLayout != null && loading != null) {
            loading!!.text = text
            loadingLayout!!.visibility = View.VISIBLE
        }
    }

    /**
     * Hides the centered loading indicator.
     */
    open fun hideCenterLoading() {
        if (loadingLayout != null) {
            loadingLayout!!.visibility = View.GONE
        }
    }

    /**
     * Adds the loading layout to the activity's view hierarchy.
     */
    private fun addLoadingView() {
        if (loadingLayout == null || loading == null) {
            val loadingView = View.inflate(this, R.layout.layout_global_center_loading, null)
            val params = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            params.gravity = Gravity.CENTER
            addContentView(loadingView, params)
            loadingLayout = findViewById<View>(R.id.ll_global_center_loading) as LinearLayout?
            loading = findViewById<View>(R.id.global_center_loading) as TextView?
        }
    }

    /**
     * Abstract method to provide the layout resource ID for the activity.
     *
     * @return The layout resource ID.
     */
    abstract fun getLayoutId(): Int

    /**
     * Abstract method to setup the action bar of the activity.
     */
    abstract fun setupActionBar()

    /**
     * Abstract method to perform initialization tasks specific to the activity.
     */
    abstract fun init()

    /**
     * Abstract method to initialize and provide the view model for the activity.
     *
     * @return The initialized view model instance or null if not applicable.
     */
    abstract fun initViewModel(): VM?
}