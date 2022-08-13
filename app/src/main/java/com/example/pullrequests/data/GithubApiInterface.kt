package com.example.pullrequests.data

import com.example.pullrequests.model.AccessRequest
import com.example.pullrequests.model.AccessTokenResponse
import com.example.pullrequests.model.GithubReposResponse
import com.example.pullrequests.utils.GithubConstants.Companion.ACCESS_TOKEN_URL
import retrofit2.Response
import retrofit2.http.*

interface GithubApiInterface {

    @GET
    suspend fun getPullRequests(
        @Url url: String,
        @Query("state") state: String/*,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int*/
    ): Response<List<GithubReposResponse>>

    @POST(ACCESS_TOKEN_URL)
    suspend fun getAccessToken(@Body accessRequest: AccessRequest): Response<AccessTokenResponse>
}