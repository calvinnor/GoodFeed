package com.mpaani.goodfeed.core.app

import android.app.Application
import android.content.Context
import com.mpaani.goodfeed.core.injection.initialise

/**
 * Application class.
 *
 * Initialise all required 3rd party libraries and utility classes here.
 */
class GoodFeedApp : Application() {

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        initDagger()
    }

    private fun initDagger() {
        initialise()
    }
}
