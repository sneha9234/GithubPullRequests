package com.example.networkmodule.retrofit

import android.content.Context
import com.example.networkmodule.interceptors.UrlValidationInterceptor
import com.example.networkmodule.wrapper.NetworkController
import com.google.gson.GsonBuilder
import com.example.networkmodule.interceptors.AuthenticationInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient(baseUrl: String, context: Context) {

    private val retrofit by lazy {
        createRetrofitInstance(baseUrl)
    }

    private val apiMap: MutableMap<String, Any> = mutableMapOf()

    private fun createRetrofitInstance(baseUrl: String): Retrofit {
        val httpClientBuilder = getOkHttpClientBuilder()

        val retroBuilder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().serializeNulls().create()
                )
            )
            .client(httpClientBuilder.build())

        return retroBuilder.build()
    }

    private fun getOkHttpClientBuilder(): OkHttpClient.Builder {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(UrlValidationInterceptor())
            .addInterceptor(getAuthenticationInterceptor())
            .addInterceptor(logging)
    }

    private fun getAuthenticationInterceptor(): AuthenticationInterceptor {
        return AuthenticationInterceptor(NetworkController.getTokenManager())
    }

    /**
     * Get the API Service instance for given class
     *
     * @param apiClass API interface class
     * @return api object reference to call Retrofit mode APIs
     */
    fun <T> getApiService(apiClass: Class<T>): Any? {
        var api: Any? = apiMap[apiClass.name]
        if (api == null) {
            api = retrofit.create(apiClass)
            apiMap[apiClass.name] = api!!
        }
        return api
    }

}