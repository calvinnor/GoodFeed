package com.mpaani.goodfeed.core.ui

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.annotation.MenuRes
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import kotlinx.android.synthetic.main.activity_toolbar.*

/**
 * Base Activity to inherit from.
 * All common code and abstraction layer goes in here.
 */
abstract class BaseActivity : AppCompatActivity() {

    companion object {
        private const val NO_LAYOUT = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupLayout()
        setupToolbar()

        // Setup the root fragment only on first launch
        if (savedInstanceState != null) return
        setupFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (menuLayout == NO_LAYOUT) return false
        menuInflater.inflate(menuLayout, menu)
        return true
    }

    /**
     * Replace the fragment present in provided container.
     *
     * @param containerId The container to replace.
     * @param fragment    The fragment to place.
     */
    protected fun replaceFragment(@IdRes containerId: Int, fragment: BaseFragment, addToBackStack: Boolean) {
        supportFragmentManager.beginTransaction().apply {
            replace(containerId, fragment, fragment.fragmentTag)
            if (addToBackStack) addToBackStack(null)
        }.commit()
    }

    /**
     * Override this value to provide an activity layout.
     *
     * @return The layout resource ID.
     */
    @LayoutRes
    protected open val contentLayout = NO_LAYOUT

    /**
     * Override this value to provide a root fragment.
     *
     * @return The BaseFragment instance to inflate.
     */
    protected open val fragment: BaseFragment? = null

    /**
     * Override this value to provide the fragment container ID.
     *
     * @return An IdRes representing the container to place the root fragment.
     */
    @IdRes
    protected open val fragmentContainer = NO_LAYOUT

    /**
     * Override this value to provide a Menu layout.
     *
     * @return The Menu resource ID.
     */
    @MenuRes
    protected open val menuLayout = NO_LAYOUT

    private fun setupLayout() {
        if (contentLayout == NO_LAYOUT) return
        setContentView(contentLayout)
    }

    private fun setupToolbar() {
        setSupportActionBar(activity_toolbar)
    }

    private fun setupFragment() {
        if (contentLayout == NO_LAYOUT) return
        val fragment = fragment ?: return

        replaceFragment(fragmentContainer, fragment, false)
    }
}
