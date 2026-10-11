package com.example.cnc

data class Topic(val title: String, val description: String)

data class LearnModule(
    val id: String,
    val emoji: String,
    val title: String,
    val subtitle: String,
    val topics: List<Topic> = emptyList(),
    // Used by modules with language tabs (DSA): language name -> topics
    val tracks: Map<String, List<Topic>> = emptyMap()
)
