package com.example.newsapp.models.jsonData.SearchResponse

data class Byline(
    val organization: Any,
    val original: String,
    val person: List<Person>
)