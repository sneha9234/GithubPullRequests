package com.example.networkmodule.wrapper

import android.content.Context
import com.example.coremodule.manager.TokenManager
import com.example.networkmodule.retrofit.RetrofitClient

object NetworkController {

    private lateinit var retrofitClient: RetrofitClient
    private lateinit var baseUrl: String
    private lateinit var tokenManager: TokenManager

    fun init(baseUrl: String, tokenManager: TokenManager, context: Context) {
        this.baseUrl = baseUrl
        this.tokenManager = tokenManager
        this.retrofitClient = RetrofitClient(
            NetworkController.baseUrl,
            context
        )
    }

    fun getAPIClient(): RetrofitClient {
        return retrofitClient
    }

    fun getTokenManager(): TokenManager {
        return tokenManager
    }
}