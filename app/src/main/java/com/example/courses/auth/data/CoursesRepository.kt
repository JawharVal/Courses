package com.example.courses.data

import android.content.Context
import com.example.courses.data.local.AppDatabase
import com.example.courses.data.local.FavoriteCourseEntity
import com.example.courses.data.remote.RetrofitModule
import com.example.courses.domain.model.Course
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

class CoursesRepository(ctx: Context) {
    private val api = RetrofitModule.api
    private val dao = AppDatabase.get(ctx).favoritesDao()


    fun observeCourses(): Flow<List<Course>> = flow {
        val remote = api.getCourses().courses.map { it.toDomain() }
        emit(remote)
    }.combine(dao.observeIds()) { remote, favIds ->
        remote.map { course ->

            course.copy(hasLike = favIds.contains(course.id))
        }
    }


    fun observeFavorites(): Flow<List<Course>> =
        dao.observeAll().combine(observeCourses()) { favEntities, remoteCourses ->
            val remoteMap = remoteCourses.associateBy { it.id }
            favEntities.map { entity ->
                remoteMap[entity.id] ?: Course(
                    id = entity.id,
                    title = entity.title,
                    text = entity.text,
                    price = entity.price,
                    rate = entity.rate,
                    startDate = entity.startDate,
                    publishDate = entity.publishDate,
                    hasLike = true,
                    imageUrl = entity.imageUrl ?: ""
                )
            }
        }


    suspend fun toggleFavorite(course: Course) {
        if (course.hasLike) {

            dao.deleteById(course.id)
        } else {

            dao.upsert(
                FavoriteCourseEntity(
                    id = course.id,
                    title = course.title,
                    text = course.text,
                    price = course.price,
                    rate = course.rate,
                    startDate = course.startDate,
                    publishDate = course.publishDate,
                    imageUrl = course.imageUrl
                )
            )
        }
    }
}
