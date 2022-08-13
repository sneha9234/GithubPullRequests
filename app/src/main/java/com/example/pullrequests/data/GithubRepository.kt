package com.example.pullrequests.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.pullrequests.model.AccessRequest
import com.example.pullrequests.model.AccessTokenResponse
import com.example.networkmodule.core.RemoteSource
import com.example.networkmodule.core.Result
import com.example.pullrequests.network.GithubApiInterface
import javax.inject.Inject

const val PAGE_SIZE = 10
class GithubRepository @Inject constructor(private val apiService: GithubApiInterface) {

    fun pullRequests(owner:String, repo: String) = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            maxSize = 100,
            enablePlaceholders = false,
            initialLoadSize = PAGE_SIZE
        ),
        pagingSourceFactory = { GithubPagingSource(apiService, owner, repo) }
    ).liveData

    suspend fun accessToken(request: AccessRequest): Result<AccessTokenResponse> {
        return RemoteSource.safeApiCall { apiService.getAccessToken(request) }
    }


}