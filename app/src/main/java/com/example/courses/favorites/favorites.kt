package com.example.courses.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.courses.data.CoursesRepository
import com.example.courses.domain.model.Course
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class FavoritesViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = CoursesRepository(app)


    val favorites = repo.observeFavorites()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList<Course>())

    // Toggle favorite status for a course
    fun onToggleFavorite(course: Course) {

        viewModelScope.launch {
            repo.toggleFavorite(course)
        }
    }
}
