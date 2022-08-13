package com.example.pullrequests.basefiles

import com.example.pullrequests.utils.GithubConstants.Companion.BASE_URL

class App : BaseApplication() {
    override fun getBaseURL(): String {
        return BASE_URL
    }

}