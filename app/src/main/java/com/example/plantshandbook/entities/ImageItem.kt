package com.example.plantshandbook.entities
import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity (tableName = "image_list")
data class ImageItem(
    @PrimaryKey (autoGenerate = true)
    val id: Int?,
    @ColumnInfo (name = "title")
    val title: String,
    @ColumnInfo (name = "img")
    val img: String,
    @ColumnInfo (name = "all_count_free")
    val all_count_free: Int,
    @ColumnInfo (name = "true_count_free")
    val true_count_free: Int,
    @ColumnInfo (name = "all_count_10g")
    val all_count_10g: Int,
    @ColumnInfo (name = "true_count_10g")
    val true_count_10g: Int

): Serializable