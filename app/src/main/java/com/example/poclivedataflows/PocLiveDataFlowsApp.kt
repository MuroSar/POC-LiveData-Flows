package com.example.poclivedataflows

import android.app.Application
import com.example.poclivedataflows.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class PocLiveDataFlowsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PocLiveDataFlowsApp)
            androidLogger(Level.NONE)
            modules(listOf(viewModelModule))
        }
    }
}
