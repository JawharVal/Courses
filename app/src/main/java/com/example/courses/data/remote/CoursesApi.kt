package com.example.courses.data.remote

import retrofit2.http.GET

interface CoursesApi {
    @GET("courses.json")
    suspend fun getCourses(): CoursesResponse
}
