package com.example.tareatresapis.data.remote.dto

data class SqliteQueryRequest(
    val sql: String
)

data class SqliteCloudResponse(
    val status: String?,
    val data: List<Map<String, Any?>>?,
    val error: String?
)
