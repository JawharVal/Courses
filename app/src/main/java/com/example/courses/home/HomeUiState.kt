package com.example.courses.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.courses.data.CoursesRepository
import com.example.courses.domain.model.Course
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

data class HomeUiState(
    val courses: List<Course> = emptyList(),
    val loading: Boolean = true,
    val error: String? = null
)

class HomeViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = CoursesRepository(app)

    private val _sortNewest = MutableStateFlow(true)
    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()


    private val dateFormats = listOf(
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()),
        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()),
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    )

    init {
        viewModelScope.launch {
            combine(repo.observeCourses(), _sortNewest) { items, newest ->
                val sorted = items.sortedByDescending { parseDate(it.publishDate) ?: Date(Long.MIN_VALUE) }
                if (newest) sorted else sorted.reversed()
            }.collect { list ->
                _state.value = HomeUiState(courses = list, loading = false)
            }
        }
    }

    private fun parseDate(s: String): Date? {
        for (format in dateFormats) {
            try {
                return format.parse(s)
            } catch (e: Exception) {

            }
        }
        return null
    }

    fun onSortClick() { _sortNewest.value = true }
    fun onToggleFavorite(c: Course) = viewModelScope.launch { repo.toggleFavorite(c) }
}
