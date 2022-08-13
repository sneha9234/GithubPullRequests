package com.example.networkmodule.wrapper

import android.content.Context
import com.example.coremodule.manager.TokenManager

object NetworkController {

    private lateinit var baseUrl: String
    private lateinit var tokenManager: TokenManager

    fun init(baseUrl: String, tokenManager: TokenManager, context: Context) {
        this.baseUrl = baseUrl
        this.tokenManager = tokenManager

    }

    fun getTokenManager(): TokenManager {
        return tokenManager
    }
}