package com.example.pullrequests.network

import android.app.Application
import com.example.networkmodule.interceptors.AuthenticationInterceptor
import com.example.networkmodule.interceptors.UrlValidationInterceptor
import com.example.networkmodule.wrapper.NetworkController
import com.example.pullrequests.utils.GithubConstants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    fun getRetrofit(application: Application): GithubApiInterface {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client =
            OkHttpClient.Builder()
                .addInterceptor(UrlValidationInterceptor())
                .addInterceptor(getAuthenticationInterceptor())
                .addInterceptor(logging)
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
        val apiService: GithubApiInterface = retrofit.create(GithubApiInterface::class.java)
        return apiService
    }

    private fun getAuthenticationInterceptor(): AuthenticationInterceptor {
        return AuthenticationInterceptor(NetworkController.getTokenManager())
    }
}


