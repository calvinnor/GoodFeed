@file:JvmName("ViewExtensions")

package com.mpaani.goodfeed.core.extension

import android.view.View
import android.widget.ImageView
import com.mpaani.goodfeed.core.util.ImageLoader

/**
 * Show a View.
 */
fun View.setVisible() {
    this.visibility = View.VISIBLE
}

/**
 * Hide a view, while removing it's space.
 */
fun View.setGone() {
    this.visibility = View.GONE
}

/**
 * Hide a view, while holding it's place.
 */
fun View.setInvisible() {
    this.visibility = View.INVISIBLE
}

/**
 * Specific function for loading a user's avatar.
 */
fun ImageView.loadAvatar(userEmail: String) = ImageLoader.loadUserImage(this, userEmail)
