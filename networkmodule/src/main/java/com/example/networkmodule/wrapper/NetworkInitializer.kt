package com.example.networkmodule.wrapper

import android.content.Context
import com.example.coremodule.manager.TokenManager

class NetworkInitializer {

    private lateinit var baseUrl:String
    private lateinit var context: Context

    /*
    Initialization of retrofit object with application context and
    base url of restful apis
    * */
    fun initialize(context: Context, baseUrl: String, tokenManager: TokenManager){
        this.baseUrl = baseUrl
        this.context = context
        NetworkController.init(baseUrl, tokenManager, context)
    }

    fun context(): Context {
        return context
    }

}