package com.rupashri.jobconnect

import android.app.Application
import com.rupashri.jobconnect.di.AppContainer
import com.rupashri.jobconnect.di.AppDataContainer

// This class will be responsible for creating and holding the AppContainer instance.
class JobConnectApplication : Application() {

    /**
     * AppContainer instance used by the rest of the classes to obtain dependencies.
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        // Initialize the container with the application context.
        container = AppDataContainer(this)
    }
}
