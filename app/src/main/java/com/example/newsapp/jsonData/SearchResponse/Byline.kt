package com.example.newsapp.jsonData.SearchResponse

data class Byline(
    val organization: Any,
    val original: String,
    val person: List<Person>
)