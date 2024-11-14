package com.example.dymagram.di

import android.content.Context
import com.example.dymagram.BuildConfig
import com.example.dymagram.di.modules.coreModule
import com.example.dymagram.di.modules.remoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.error.KoinApplicationAlreadyStartedException
import org.koin.dsl.module

fun injectModuleDependencies(context: Context) {
    try {
        startKoin {
            // Reference Android context
            androidContext(context)
            // Load modules
            modules(appModules)
        }
    } catch (alreadyStart: KoinApplicationAlreadyStartedException) {
        loadKoinModules(appModules)
    }

}

fun injectAppConfiguration() {
    val appConfiguration = ApplicationConfiguration(BuildConfig.FAKE_SERVER_BASE_URL)

    appModules.add(module {
        single { appConfiguration }
    })
}


var appModules = mutableListOf(coreModule, remoteModule)

data class ApplicationConfiguration(val baseUrl: String)