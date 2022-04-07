package com.example.plantshandbook.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.plantshandbook.entities.ImageItem
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Query("SELECT * FROM image_list")
    fun getAllImage(): LiveData<List<ImageItem>>

    @Query("SELECT * FROM image_list")
    suspend fun getAllImageList(): List<ImageItem>

    @Query("DELETE FROM image_list WHERE id IS :id")
    suspend fun deleteImage(id: Int)

    //@Query("SELECT 'id' FROM image_list")
    //suspend fun idRead(): Flow<List<ImageItem>>

    @Insert
    suspend fun insertImage(note: ImageItem)

    @Update
    suspend fun updateImage(note: ImageItem)




}