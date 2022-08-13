package com.example.networkmodule.interceptors

import com.example.coremodule.manager.TokenManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

open class AuthenticationInterceptor(
    private val tokenManager: TokenManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        // Build new request
        val builder = request.newBuilder()
        var retry = false
        builder.header("Accept", "application/vnd.github+json") // if necessary, say to consume JSON

        val token = getAccessToken() // save token of this request for future
        setAuthHeader(builder, token) // write current token to request
        request = builder.build() // overwrite old request
        val response = chain.proceed(request) // perform request, here original request will be executed

        if (retry) {
            response.close()
            var tempRequest = chain.request()
            val tAccessToken = getAccessToken()
            setAuthHeader(builder, tAccessToken)
            tempRequest = builder.build()
            try {
                return chain.proceed(tempRequest)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
        return response
    }

    private fun getAccessToken(): String? {
        return tokenManager.token
    }


    open fun setAuthHeader(builder: Request.Builder, token: String?) {
        if (!token.isNullOrEmpty())
            builder.header("Authorization",token)
    }
}