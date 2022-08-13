package com.example.pullrequests.model

data class GithubReposResponse(
    val id: Int? = null,
    var title: String? = null,
    var created_at: String? = null,
    var closed_at: String? = null,
    var user: User ? = null
)

data class User(
    val login: String? = null,
    val avatar_url: String? = null
)