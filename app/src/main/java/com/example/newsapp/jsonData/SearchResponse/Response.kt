package com.example.newsapp.jsonData.SearchResponse

data class Response(
    val docs: List<Doc>,
    val facets: Facets,
    val meta: Meta
)