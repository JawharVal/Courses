package com.example.courses.domain.model

data class Course(
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: Double,
    val startDate: String,
    val publishDate: String,
    val hasLike: Boolean,
    val imageUrl: String
)
