package com.example.plantshandbook.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.plantshandbook.entities.ImageItem
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Query("SELECT * FROM image_list")
    fun getAllImage(): Flow<List<ImageItem>>

    @Query("DELETE FROM image_list WHERE id IS :id")
    suspend fun deleteImage(id: Int)

    @Insert
    suspend fun insertImage(note: ImageItem)

    @Update
    suspend fun updateNote(note: ImageItem)
}