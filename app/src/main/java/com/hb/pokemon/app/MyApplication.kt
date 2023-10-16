package com.hb.pokemon.app

import com.hb.base.BaseApp

/**
 * Custom application class that extends BaseApp.
 * Initializes and holds the application instance.
 *
 * @author Robin
 * @version 1.0
 * @since 2023-10-13
 */
class MyApplication : BaseApp() {

    /**
     * Called when the application is created.
     */
    override fun onCreate() {
        super.onCreate()
        app = this
    }

    /**
     * A companion object to hold a reference to the application instance.
     */
    companion object {
        // Reference to the application instance.
        lateinit var app: MyApplication
    }
}
