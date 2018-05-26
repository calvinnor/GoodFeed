package com.mpaani.goodfeed.core.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.mpaani.goodfeed.R
import com.mpaani.goodfeed.core.data.getAvatarUrl

/**
 * Wrapper class for Image Loading.
 *
 * Internally uses Glide, but can be hot-swapped for anything else.
 */
object ImageLoader {

    private val userImageRequestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_account)

    /**
     * Load a user's display image based on his Email.
     */
    fun loadUserImage(imageView: ImageView, userEmail: String) {
        Glide.with(imageView)
                .applyDefaultRequestOptions(userImageRequestOptions)
                .load(getAvatarUrl(userEmail))
                .into(imageView)
    }
}
