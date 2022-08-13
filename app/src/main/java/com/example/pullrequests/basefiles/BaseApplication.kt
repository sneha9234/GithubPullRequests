package com.example.pullrequests.basefiles

import android.app.Application
import com.example.coremodule.manager.TokenManager
import com.example.networkmodule.wrapper.NetworkInitializer

abstract class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initDependencies()
    }
    open fun initDependencies() {
        tokenManager = TokenManager(this)
        NetworkInitializer().initialize(this, getBaseURL(), tokenManager)
    }

    abstract fun getBaseURL(): String

    companion object {
        lateinit var tokenManager: TokenManager
    }
}