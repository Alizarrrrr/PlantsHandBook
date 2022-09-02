package com.example.plantshandbook.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.plantshandbook.entities.FirebaseListItem
import com.example.plantshandbook.entities.ImageItem
import com.example.plantshandbook.entities.StatItem

@Dao
interface Dao {
    @Query("SELECT * FROM image_list")
    fun getAllImage(): LiveData<List<ImageItem>>

    @Query("SELECT * FROM image_list")
    suspend fun getAllImageList(): List<ImageItem>

    @Query("SELECT * FROM stat_list")
    fun getAllStat(): LiveData<List<StatItem>>

    @Query("SELECT * FROM stat_list")
    suspend fun getAllStatList(): List<StatItem>

    @Query("SELECT * FROM firebase_list")
    fun getAllFb(): LiveData<List<FirebaseListItem>>


    @Query("SELECT * FROM firebase_list")
    suspend fun getAllFbList(): List<FirebaseListItem>

    @Query("DELETE FROM firebase_list WHERE id IS :id")
    suspend fun deleteFb(id: Int)

    @Query("DELETE FROM image_list WHERE id IS :id")
    suspend fun deleteImage(id: Int)


    //@Query("SELECT 'id' FROM image_list")
    //suspend fun idRead(): Flow<List<ImageItem>>

    @Insert
    suspend fun insertImage(note: ImageItem)

    @Insert
    suspend fun insertStat(note: StatItem)

    @Insert
    suspend fun insertFb(note: FirebaseListItem)

    @Update
    suspend fun updateImage(note: ImageItem)

    @Update
    suspend fun updateStat(note: StatItem)

    @Update
    suspend fun updateFb(note: FirebaseListItem)



}