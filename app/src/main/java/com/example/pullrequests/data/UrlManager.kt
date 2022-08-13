package com.example.pullrequests.data

import com.example.pullrequests.utils.GithubConstants

class UrlManager {
    fun getPullRequestsUrl(owner: String, repo: String) = "${GithubConstants.BASE_URL_API}repos/$owner/$repo/pulls"
}