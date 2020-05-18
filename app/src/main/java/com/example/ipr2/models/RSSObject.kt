package com.example.ipr2.models

data class RSSObject(
    val status: String,
    val feed: Feed,
    val items: List<Item>
)
