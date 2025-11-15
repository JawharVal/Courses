package com.example.courses.data.remote

data class CoursesResponse(val courses: List<CourseDto>)

data class CourseDto(
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: Double,
    val startDate: String,
    val publishDate: String,
    val hasLike: Boolean,
    val imageUrl: String?
)
