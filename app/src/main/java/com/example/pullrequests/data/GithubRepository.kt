package com.example.pullrequests.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.pullrequests.model.AccessRequest
import com.example.pullrequests.model.AccessTokenResponse
import com.example.pullrequests.model.GithubReposResponse
import com.example.networkmodule.core.RemoteSource
import com.example.networkmodule.core.Result
import com.example.networkmodule.wrapper.NetworkController

const val PAGE_SIZE = 10
class GithubRepository {
    private val githubApiInterface: GithubApiInterface by lazy {
        NetworkController.getAPIClient().getApiService(
            GithubApiInterface::class.java
        ) as GithubApiInterface
    }


    fun pullRequests(owner:String, repo: String) = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            maxSize = 100,
            enablePlaceholders = false,
            initialLoadSize = PAGE_SIZE
        ),
        pagingSourceFactory = { GithubPagingSource(githubApiInterface, owner, repo) }
    ).liveData

    suspend fun accessToken(request: AccessRequest): Result<AccessTokenResponse> {
        return RemoteSource.safeApiCall { githubApiInterface.getAccessToken(request) }
    }


}