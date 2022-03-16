package com.example.plantshandbook.entities
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity (tableName = "image_list")
data class ImageItem(
    @PrimaryKey (autoGenerate = true)
    val id: Int?,
    @ColumnInfo (name = "title")
    val  title: String,
    @ColumnInfo (name = "path")
    val  path: String,
   // @ColumnInfo (name = "imgId")
  //  val  imgId: Int,
): Serializable