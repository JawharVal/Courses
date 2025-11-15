package com.example.courses.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorites")
    fun observeAll(): Flow<List<FavoriteCourseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: FavoriteCourseEntity)

    @Query("DELETE FROM favorites WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT id FROM favorites")
    fun observeIds(): Flow<List<Int>>
}
