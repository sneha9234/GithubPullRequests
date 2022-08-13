package com.example.pullrequests.di

import android.app.Application
import com.example.pullrequests.network.GithubApiInterface
import com.example.pullrequests.network.RetrofitBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(application: Application): GithubApiInterface  =
        RetrofitBuilder.getRetrofit(application)

}
