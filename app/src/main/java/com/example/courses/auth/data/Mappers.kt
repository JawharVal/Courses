package com.example.courses.data

import com.example.courses.data.remote.CourseDto
import com.example.courses.domain.model.Course

fun CourseDto.toDomain(overrideLike: Boolean? = null) = Course(
    id,
    title,
    text,
    price,
    rate,
    startDate,
    publishDate,
    hasLike = overrideLike ?: hasLike,
    imageUrl = imageUrl ?: ""
)
