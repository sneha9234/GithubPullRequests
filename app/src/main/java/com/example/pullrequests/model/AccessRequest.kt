package com.example.pullrequests.model

data class AccessRequest(
    val client_id: String? = null,
    val client_secret: String? = null,
    val code: String? = null
)