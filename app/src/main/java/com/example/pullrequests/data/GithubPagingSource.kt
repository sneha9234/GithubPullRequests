package com.example.pullrequests.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pullrequests.model.GithubReposResponse
import com.example.pullrequests.utils.GithubConstants.Companion.CLOSED
import com.example.networkmodule.core.DEFAULT_ERROR_MESSAGE
import com.example.networkmodule.core.RemoteSource
import com.example.networkmodule.core.Result

private const val STARTING_PAGE_INDEX = 1

class GithubPagingSource(private val service: GithubApiInterface, private val owner: String, private val repo: String) : PagingSource<Int, GithubReposResponse>() {

    private val urlManager by lazy { UrlManager() }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubReposResponse> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        return try {

            val response = RemoteSource.safeApiCall { service.getPullRequests(
                urlManager.getPullRequestsUrl(owner, repo),
                state = CLOSED/*,
                per_page = params.loadSize,
                page = pageIndex*/
            ) }


            when(response){
                is Result.Error -> {
                    throw Exception(response.error.message)
                }
                is Result.Success -> {

                    val joined = mutableListOf<GithubReposResponse>()
                  //  joined.addAll(response.data ?: emptyList())


                    val nextKey = if (joined.size < PAGE_SIZE) null else pageIndex + 1

                    LoadResult.Page(
                        data = joined,
                        prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex - 1,
                        nextKey = nextKey
                    )
                }
                else -> {
                    throw Exception(DEFAULT_ERROR_MESSAGE)
                }
            }

        }catch (exception: java.lang.Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GithubReposResponse>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
