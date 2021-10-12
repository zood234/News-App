package com.example.newsapp.models.jsonData.SearchResponse

data class Person(
    val firstname: String,
    val lastname: String,
    val middlename: Any,
    val organization: String,
    val qualifier: Any,
    val rank: Int,
    val role: String,
    val title: Any
)