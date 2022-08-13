package com.example.pullrequests.basefiles

import com.example.pullrequests.utils.GithubConstants.Companion.BASE_URL
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : BaseApplication() {
    override fun getBaseURL(): String {
        return BASE_URL
    }

}